package compiler.Parser.Grammar;

import compiler.CMinusCompiler;
import compiler.Scanner.Token;
import lowlevel.CodeItem;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

public class VarExpression extends Expression
{
    private Token id;
    private Expression index;

    public VarExpression(Token id, Expression index)
    {
        this.id = id;
        this.index = index;
    }

    public Token getID()
    {
        return id;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Var: " + id.tokenData());
        if(index !=null)
        {
            System.out.println(indent + "[");
            index.print(indent + " ");
            System.out.println(indent + "]");
        }
    }

    @Override
    public void genLLCode(Function function) {
        String varName = id.tokenData().toString();

        if(function.getTable().containsKey(varName)){
            setRegNum((Integer)(function.getTable().get(varName)));
        }
        else if(CMinusCompiler.globalHash.containsKey(varName)){
            // Create a load oper
            Operation loadOper = new Operation(OperationType.LOAD_I, function.getCurrBlock());
            int newRegNum = function.getNewRegNum();
            Operand srcOperand = new Operand(OperandType.STRING, varName);
            Operand destOperand = new Operand(OperandType.REGISTER, newRegNum);
            loadOper.setDestOperand(0, destOperand);
            loadOper.setSrcOperand(0, srcOperand);

            //Add to block
            function.getCurrBlock().appendOper(loadOper);

            setRegNum(newRegNum);
        }
    }
}
