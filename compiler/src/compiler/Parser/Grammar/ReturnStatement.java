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

    public void genLLCode(Function function)
    {
        if(e != null)
        {
            e.genLLCode(function);
            Operation moveToRetReg = new Operation(OperationType.PASS, function.getCurrBlock());
            Operand src = new Operand(OperandType.REGISTER, e.getRegNum());
            Operand dest = new Operand(OperandType.MACRO, "RetReg");
            moveToRetReg.setDestOperand(0, dest);
            moveToRetReg.setSrcOperand(0, src);
            function.getCurrBlock().appendOper(moveToRetReg);

            // add jump-to-return-block instruction
            Operation jump = new Operation(OperationType.JMP, function.getCurrBlock());
            Operand jumpDest = new Operand(OperandType.BLOCK, function.getReturnBlock());
            jump.setDestOperand(0, jumpDest);
        }
    }
    


}
