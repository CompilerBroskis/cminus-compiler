package compiler.Parser.Grammar;

import lowlevel.Attribute;
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
        
        if(e !=null)
        {
            e.print(indent + " ");
        }
        
        System.out.println(indent + "}");
    }

    public void genLLCode(Function function)
    {
        if(e != null)
        {
            e.genLLCode(function);
            Operation moveToRetReg = new Operation(OperationType.PASS, function.getCurrBlock());
            moveToRetReg.addAttribute(new Attribute("PARAM_NUM", Integer.toString(0)));
            Operand src = new Operand(OperandType.REGISTER, e.getRegNum());
            Operand dest = new Operand(OperandType.MACRO, "RetReg");
            moveToRetReg.setSrcOperand(0, src);
            moveToRetReg.setDestOperand(0, dest);
            function.getCurrBlock().appendOper(moveToRetReg);

            // add jump-to-return-block instruction
            Operation jump = new Operation(OperationType.JMP, function.getCurrBlock());
            Operand jumpDest = new Operand(OperandType.BLOCK, function.getReturnBlock().getBlockNum());
            Operand jumpSrc = new Operand(OperandType.BLOCK, function.getReturnBlock().getBlockNum());
            jump.setDestOperand(0, jumpDest);
            jump.setSrcOperand(0, jumpSrc);

            function.getCurrBlock().appendOper(jump);
        }
    }
    


}
