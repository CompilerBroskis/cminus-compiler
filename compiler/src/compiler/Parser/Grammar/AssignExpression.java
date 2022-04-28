package compiler.Parser.Grammar;

import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

public class AssignExpression extends Expression
{
    private Expression lhs;
    private Expression rhs;
    
    public AssignExpression(Expression lhs, Expression rhs)
    {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void print(String indent) 
    {
        System.out.println(indent + "=");
        lhs.print(indent + " ");
        rhs.print(indent + " ");
    }

    @Override
    public void genLLCode(Function function)
    {
        lhs.genLLCode(function);
        rhs.genLLCode(function);
        
        Operation operation = new Operation(OperationType.ASSIGN, function.getCurrBlock());
        Operand lhsOperand = new Operand(OperandType.REGISTER, lhs.getRegNum());
        Operand rhsOperand = new Operand(OperandType.REGISTER, rhs.getRegNum());
        operation.setSrcOperand(0, rhsOperand);
        operation.setDestOperand(0, lhsOperand);

        function.getCurrBlock().appendOper(operation);
    }
}
