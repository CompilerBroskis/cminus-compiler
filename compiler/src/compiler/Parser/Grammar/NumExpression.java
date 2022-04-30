package compiler.Parser.Grammar;

import compiler.Scanner.Token;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

public class NumExpression extends Expression
{
    private Token num; 
    
    public NumExpression(Token num)
    {
        this.num = num;
    }

    @Override
    public void print(String indent) 
    {
        System.out.println(indent + num.tokenData());
    }

    @Override
    public void genLLCode(Function function)
    {
        // Assign yourself a register
        int regNum = function.getNewRegNum();
        setRegNum(regNum);

        Operation operation = new Operation(OperationType.PASS, function.getCurrBlock()); // should operation type be pass, load or store?
        Operand src = new Operand(OperandType.INTEGER, num.tokenData());
        Operand dest = new Operand(OperandType.REGISTER, regNum);
        operation.setSrcOperand(0, src);
        operation.setDestOperand(0, dest);
        function.getCurrBlock().appendOper(operation);
    }
}
