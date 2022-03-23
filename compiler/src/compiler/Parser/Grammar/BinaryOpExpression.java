package compiler.Parser.Grammar;

import compiler.Scanner.Token;

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
}
