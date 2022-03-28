package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class AssignExpression extends Expression
{
    private Expression lhs;
    private Expression rhs;
    
    public AssignExpression(Expression lhs, Expression rhs)
    {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
