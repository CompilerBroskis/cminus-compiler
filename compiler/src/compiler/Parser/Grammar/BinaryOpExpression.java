package compiler.Parser.Grammar;

import compiler.Scanner.Token;
import compiler.Scanner.Token.TokenType;

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
}
