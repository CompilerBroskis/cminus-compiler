package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class AssignExpression extends Expression
{
    private Token lhs;
    private Expression rhs;
    
    public AssignExpression(Token lhs, Expression rhs)
    {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
