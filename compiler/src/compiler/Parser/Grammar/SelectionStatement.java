package compiler.Parser.Grammar;

import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

public class SelectionStatement {

    private Expression e;
    private Statement s1; 
    private Statement s2;

    public SelectionStatement(Expression e, Statement s1, Statement s2)
    {
        this.e = e;
        this.s1 = s1;
        this.s2 = s2;
    }

    public void print(String indent) 
    {
        System.out.println(indent + "if {");
        
        e.print(indent + " ");
        s1.print(indent + " ");

        if(s2 != null)
        {
            System.out.println(indent + "else: ");
            s2.print(indent + " ");
        }
        
        System.out.println(indent + "}");
    }

    public void genLLCode(Function function)
    {
        // if statement, no else
        if(s2 == null)
        {
            // Make Blocks
            BasicBlock thenBlock = new BasicBlock(function);
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
            beq.setSrcOperand(1, beqSrc1);
            beq.setSrcOperand(2, beqDest);
            function.getCurrBlock().appendOper(beq);

            // Append the then block
            function.appendBlock(thenBlock); // NOTE: not yet appending the post block to the then block because the then block may have its own ifs and elses
            function.setCurrBlock(thenBlock);
            
            // Call codeGen on the then block
            s1.genLLCode(function);

            // Append the post block
            function.appendBlock(postBlock);
            function.setCurrBlock(postBlock);
        }
        else
        // if statement WITH else
        {
            // Make Blocks
            BasicBlock thenBlock = new BasicBlock(function); // not sure if this goes here yet
            BasicBlock elseBlock = new BasicBlock(function);
            BasicBlock postBlock = new BasicBlock(function);

            // Generate the branch condition
            Operation compare = new Operation(OperationType.PASS, function.getCurrBlock());
            Operand src = new Operand(OperandType.REGISTER, e.getRegNum());
            int destRegNum = function.getNewRegNum();
            Operand dest = new Operand(OperandType.REGISTER, destRegNum);
            compare.setSrcOperand(0, src);
            compare.setDestOperand(0, dest);
            function.getCurrBlock().appendOper(compare);

            // generate the branch itself
            Operation beq = new Operation(OperationType.BEQ, function.getCurrBlock());
            Operand beqSrc0 = new Operand(OperandType.REGISTER, destRegNum);
            Operand beqSrc1 = new Operand(OperandType.INTEGER, 0);
            Operand beqDest = new Operand(OperandType.BLOCK, elseBlock.getBlockNum());
            beq.setSrcOperand(0, beqSrc0);
            beq.setSrcOperand(1, beqSrc1);
            beq.setSrcOperand(2, beqDest);
            function.getCurrBlock().appendOper(beq);

            // Append the then block
            function.appendBlock(thenBlock); // NOTE: not yet appending the post block to the then block because the then block may have its own ifs and elses
            function.setCurrBlock(thenBlock);

            // Call codeGen on the then block
            s1.genLLCode(function);

            // Append the post block
            function.appendBlock(postBlock);
            function.setCurrBlock(postBlock);
            
            // set current block to the else block
            function.setCurrBlock(elseBlock);

            // call codeGen on the else block
            s2.genLLCode(function);

            // Append the jump-to-post operation to the else block
            Operation jump = new Operation(OperationType.JMP, function.getCurrBlock());
            Operand jumpDest = new Operand(OperandType.BLOCK, postBlock.getBlockNum());
            Operand jumpSrc = new Operand(OperandType.BLOCK, postBlock.getBlockNum());
            jump.setDestOperand(0, jumpDest);
            jump.setSrcOperand(0, jumpSrc);
            function.getCurrBlock().appendOper(jump);

            // Append the else block to the unconnected chain
            function.appendUnconnectedBlock(elseBlock);

            // Set current block to post
            function.setCurrBlock(postBlock);
        }
    }
}
