package compiler.Parser.Grammar;

import compiler.Scanner.Token;
import compiler.Scanner.Token.TokenType;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

public class BinaryOpExpression extends Expression
{
    private Expression lhs;
    private Expression rhs;
    private Token op;
    
    public BinaryOpExpression(Expression lhs, Expression rhs, Token op) 
    {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public void print(String indent) 
    {
        System.out.println(indent + "BinaryOpExpression {");
        
        TokenType opType = op.getTokenType();
        if(opType == TokenType.LT_TOKEN)
        {
            System.out.println(indent + " <");
        }
        else if(opType == TokenType.GT_TOKEN)
        {
            System.out.println(indent + " >");
        }
        else if(opType == TokenType.PLUS_TOKEN)
        {
            System.out.println(indent + " +");
        }
        else if(opType == TokenType.MINUS_TOKEN)
        {
            System.out.println(indent + " -");
        }
        else if(opType == TokenType.MUL_TOKEN)
        {
            System.out.println(indent + " *");
        }
        else if(opType == TokenType.DIV_TOKEN)
        {
            System.out.println(indent + " /");
        }
        else if(opType == TokenType.NOTEQ_TOKEN)
        {
            System.out.println(indent + " !=");
        }
        else if(opType == TokenType.LTEQ_TOKEN)
        {
            System.out.println(indent + " <=");
        }
        else if(opType == TokenType.GTEQ_TOKEN)
        {
            System.out.println(indent + " >=");
        }
        else if(opType == TokenType.EQEQ_TOKEN)
        {
            System.out.println(indent + " ==");
        }
        lhs.print(indent + "  ");
        rhs.print(indent + "  ");

        System.out.println(indent + "}");
    }

    public OperationType getOperationType()
    {
        TokenType opType = op.getTokenType();
        if(opType == TokenType.LT_TOKEN)
        {
            return OperationType.LT;
        }
        else if(opType == TokenType.GT_TOKEN)
        {
            return OperationType.GT;
        }
        else if(opType == TokenType.PLUS_TOKEN)
        {
            return OperationType.ADD_I;
        }
        else if(opType == TokenType.MINUS_TOKEN)
        {
            return OperationType.SUB_I;
        }
        else if(opType == TokenType.MUL_TOKEN)
        {
            return OperationType.MUL_I;
        }
        else if(opType == TokenType.DIV_TOKEN)
        {
            return OperationType.DIV_I;
        }
        else if(opType == TokenType.NOTEQ_TOKEN)
        {
            return OperationType.NOT_EQUAL;
        }
        else if(opType == TokenType.LTEQ_TOKEN)
        {
            return OperationType.LTE;
        }
        else if(opType == TokenType.GTEQ_TOKEN)
        {
            return OperationType.GTE;
        }
        else if(opType == TokenType.EQEQ_TOKEN)
        {
            return OperationType.EQUAL;
        }
        return null;
    }

    @Override
    public void genLLCode(Function function)
    {
        // Call genCode on left and right child
        lhs.genLLCode(function);
        rhs.genLLCode(function);

        // Get location of where children stored their results
        Operation operation = new Operation(getOperationType(), function.getCurrBlock());
        Operand lhsOperand = new Operand(OperandType.REGISTER, lhs.getRegNum());
        Operand rhsOperand = new Operand(OperandType.REGISTER, rhs.getRegNum());
        operation.setSrcOperand(0, lhsOperand);
        operation.setSrcOperand(1, rhsOperand);

        int newRegNum = function.getNewRegNum();
        // Choose a location for your result
        setRegNum(newRegNum);
        Operand destOperand = new Operand(OperandType.REGISTER, newRegNum);
        operation.setDestOperand(0, destOperand);

        // Add operation to do to your function
        function.getCurrBlock().appendOper(operation);
    }
}
