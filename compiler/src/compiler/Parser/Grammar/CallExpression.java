package compiler.Parser.Grammar;

import compiler.Scanner.Token;
import lowlevel.Attribute;
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
        int index = 0;
        for(Expression e : args)
        {
            // Call genCode on params to generate code for them
            e.genLLCode(function);

            // Add operation to move each param to register or memory
            Operation operation = new Operation(OperationType.PASS, function.getCurrBlock());
            operation.addAttribute(new Attribute("PARAM_NUM", Integer.toString(index)));

            Operand operand = new Operand(OperandType.REGISTER, e.getRegNum());
            operation.setSrcOperand(0, operand);
            Operand dest = new Operand(OperandType.REGISTER, function.getNewRegNum());
            operation.setDestOperand(0, dest);
            function.getCurrBlock().appendOper(operation);
            index++;
        }
        
        // Add call operation

        Operation callOperation = new Operation(OperationType.CALL, function.getCurrBlock());
        Operand operand = new Operand(OperandType.STRING, id.tokenData().toString());
        callOperation.setSrcOperand(0, operand);
        callOperation.addAttribute(new Attribute("numParams", "" + args.length));

        function.getCurrBlock().appendOper(callOperation);

        // May want to add a Macro Operation for PostCall
        //Add operation like varexpression
        //get new register and move it to retreg
        int newReg = function.getNewRegNum();
        setRegNum(newReg);
        Operation store = new Operation(OperationType.STORE_I, function.getCurrBlock());
        Operand src = new Operand(OperandType.REGISTER, newReg);
        Operand dest = new Operand(OperandType.MACRO, "RetReg");
        store.setSrcOperand(0, src);
        store.setDestOperand(0, dest);
        function.getCurrBlock().appendOper(store);
    }
}