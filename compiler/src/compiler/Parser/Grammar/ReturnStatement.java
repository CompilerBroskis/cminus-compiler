package compiler.Parser.Grammar;

import lowlevel.CodeItem;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

public class ReturnStatement
{

    private Expression e;

    public ReturnStatement(Expression e) {
        this.e = e;
    }

    public void print(String indent) 
    {
        System.out.println(indent + "return {");
        
        e.print(indent + " ");
        
        System.out.println(indent + "}");
    }

    public CodeItem genLLCode(Function function)
    {
        if(e != null)
        {
            CodeItem expressionResult = e.genLLCode(function);
            Operation moveToRetReg = new Operation(OperationType.PASS, function.getCurrBlock());
            Operand src = new Operand(OperandType.REGISTER, e.getRegNum());
            Operand dest = new Operand(OperandType.REGISTER, "RetReg");
            moveToRetReg.setDestOperand(0, dest);
            moveToRetReg.setSrcOperand(0, src);
            function.getCurrBlock().appendOper(moveToRetReg);
            // TODO: add jump operation to exit block
        }
    }
    


}
