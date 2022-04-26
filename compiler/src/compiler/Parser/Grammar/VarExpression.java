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
            setRegNum((Integer)(CMinusCompiler.globalHash.get(varName)));
            
            // Create a load oper
            Operation loadOper = new Operation(OperationType.LOAD_I, function.getCurrBlock());
            Operand destOperand = new Operand(OperandType.REGISTER, getRegNum());
            loadOper.setDestOperand(0, destOperand);
        }
    }
}
