package compiler.Parser.Grammar;

import lowlevel.Attribute;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

public class IterationStatement {

    private Expression e;
    private Statement s;

    public IterationStatement(Expression e, Statement s) 
    {
        this.e = e;
        this.s = s;
    }

    public void print(String indent) 
    {
        System.out.println(indent + "while {");
        
        e.print(indent + " ");
        s.print(indent + " ");
        
        System.out.println(indent + "}");
    }

    public void genLLCode(Function function)
    {
        // Make Blocks
        BasicBlock whileBlock = new BasicBlock(function);
        BasicBlock postBlock = new BasicBlock(function);

        e.genLLCode(function);

        // Generate branch condition
        Operation compare = new Operation(OperationType.PASS, function.getCurrBlock());
        compare.addAttribute(new Attribute("PARAM_NUM", Integer.toString(0)));
        Operand src = new Operand(OperandType.REGISTER, e.getRegNum());
        int destRegNum = function.getNewRegNum();
        Operand dest = new Operand(OperandType.REGISTER, destRegNum);
        compare.setSrcOperand(0, src);
        compare.setDestOperand(0, dest);
        function.getCurrBlock().appendOper(compare);

        // Generate the branch itself
        Operation beq = new Operation(OperationType.BEQ, function.getCurrBlock());
        Operand beqSrc0 = new Operand(OperandType.REGISTER, destRegNum);
        Operand beqSrc1 = new Operand(OperandType.INTEGER, 0);
        Operand beqDest = new Operand(OperandType.BLOCK, postBlock.getBlockNum());
        beq.setSrcOperand(0, beqSrc0);
        beq.setSrcOperand(1, beqSrc1);
        beq.setSrcOperand(2, beqDest);
        function.getCurrBlock().appendOper(beq);

        // Append the then block
        function.appendBlock(whileBlock); // NOTE: not yet appending the post block to the then block because the then block may have its own ifs and elses
        function.setCurrBlock(whileBlock);
        
        // Call codeGen on the then block
        s.genLLCode(function);

        
        // Generate branch condition
        Operation compare2 = new Operation(OperationType.PASS, function.getCurrBlock());
        compare2.addAttribute(new Attribute("PARAM_NUM", Integer.toString(0)));
        Operand src2 = new Operand(OperandType.REGISTER, e.getRegNum());
        int destRegNum2 = function.getNewRegNum();
        Operand dest2 = new Operand(OperandType.REGISTER, destRegNum2);
        compare2.setSrcOperand(0, src2);
        compare2.setDestOperand(0, dest2);
        function.getCurrBlock().appendOper(compare2);
        
        // Re-do check to create while loop
        
        Operation bne = new Operation(OperationType.BNE, function.getCurrBlock());
        Operand bneDest = new Operand(OperandType.BLOCK, whileBlock.getBlockNum());
        Operand bneSrc0 = new Operand(OperandType.REGISTER, destRegNum2);
        Operand bneSrc1 = new Operand(OperandType.INTEGER, 0);
        bne.setSrcOperand(0, bneSrc0);
        bne.setSrcOperand(1, bneSrc1);
        bne.setSrcOperand(2, bneDest);
        function.getCurrBlock().appendOper(bne);


        // Append the post block
        function.appendBlock(postBlock);
        function.setCurrBlock(postBlock);
    }
    
}
