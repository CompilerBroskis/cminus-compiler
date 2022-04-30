package compiler.Parser.Grammar;

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

        // Generate branch condition
        Operation compare = new Operation(OperationType.PASS, function.getCurrBlock());
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
        beq.setSrcOperand(0, beqSrc1);
        beq.setDestOperand(0, beqDest);
        function.getCurrBlock().appendOper(beq);

        // Append the then block
        function.appendBlock(whileBlock); // NOTE: not yet appending the post block to the then block because the then block may have its own ifs and elses
        function.setCurrBlock(whileBlock);
        
        // Call codeGen on the then block
        s.genLLCode(function);

        // Re-do check to create while loop
        // If this doesn't work, regenerate the branch condition
        Operation bne = new Operation(OperationType.BNE, function.getCurrBlock());
        Operand bneDest = new Operand(OperandType.BLOCK, whileBlock.getBlockNum());
        bne.setSrcOperand(0, beqSrc0);
        bne.setSrcOperand(0, beqSrc1);
        bne.setDestOperand(0, bneDest);
        function.getCurrBlock().appendOper(bne);


        // Append the post block
        function.appendBlock(postBlock);
        function.setCurrBlock(postBlock);
    }
    
}
