package compiler.Parser.Grammar;

import javax.lang.model.util.ElementScanner14;

import compiler.CMinusCompiler;
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
        // lhs.genLLCode(function);
        rhs.genLLCode(function);
        //if local var is true then move rhs into lhs
        VarExpression lhsVar = (VarExpression)lhs;
        String varName = lhsVar.getID().tokenData().toString();
        if(function.getTable().containsKey(varName)){
            int lhsRegNum = function.getTable().get(varName);
            setRegNum(lhsRegNum);
            Operation operation = new Operation(OperationType.ASSIGN, function.getCurrBlock());
            Operand lhsOperand = new Operand(OperandType.REGISTER, lhsRegNum);
            Operand rhsOperand = new Operand(OperandType.REGISTER, rhs.getRegNum());
            operation.setSrcOperand(0, rhsOperand);
            operation.setDestOperand(0, lhsOperand);

            function.getCurrBlock().appendOper(operation);
        }
        else if(CMinusCompiler.globalHash.containsKey(varName))
        {
            //if global do a store
            setRegNum(rhs.getRegNum());
            Operation storeOper = new Operation(OperationType.STORE_I, function.getCurrBlock());
            Operand srcOperand = new Operand(OperandType.REGISTER, rhs.getRegNum());
            Operand destOperand = new Operand(OperandType.STRING, varName);
            storeOper.setSrcOperand(0, srcOperand);
            storeOper.setSrcOperand(1, destOperand);

            function.getCurrBlock().appendOper(storeOper);
        }
        else
        {
            setRegNum(-4);
        }
    }
}
