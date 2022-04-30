package compiler.Parser.Grammar;

import compiler.Scanner.Token;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

public class CallExpression extends Expression
{
    private Token id;
    private Expression[] args;

    public CallExpression(Token id, Expression[] args)
    {
        this.id = id;
        this.args = args;
    }

    @Override
    public void print(String indent) 
    {
        System.out.println(indent + "CallExpression {");
        System.out.println(indent + " function: " + id.tokenData());

        System.out.println(indent + " (");
        if(args != null && args.length > 0)
        {
            for(int i = 0; i < args.length; i++){
                args[i].print(indent + "  ");
            }
        }
        System.out.println(indent + " )");

        System.out.println(indent + "}");
    }

    @Override
    public void genLLCode(Function function)
    {
        for(Expression e : args)
        {
            // Call genCode on params to generate code for them
            e.genLLCode(function);

            // Add operation to move each param to register or memory
            Operation operation = new Operation(OperationType.PASS, function.getCurrBlock());
            Operand operand = new Operand(OperandType.REGISTER, e.getRegNum());
            operation.setSrcOperand(0, operand);
            operation.setDestOperand(0, new Operand(OperandType.REGISTER, function.getNewRegNum()));
            function.getCurrBlock().appendOper(operation);
        }
        
        // Add call operation
        Operation callOperation = new Operation(OperationType.CALL, function.getCurrBlock());
        Operand operand = new Operand(OperandType.STRING, id.tokenData().toString());
        callOperation.setSrcOperand(0, operand);
        function.getCurrBlock().appendOper(callOperation);

        // May want to add a Macro Operation for PostCall

        // TODO: Annotate Call with param size (?????)

        // Need to move return register into regular register
        // WHAT is a regular register?

        
    }
}
