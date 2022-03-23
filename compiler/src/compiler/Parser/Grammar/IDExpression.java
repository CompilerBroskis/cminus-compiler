package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class IDExpression extends Expression
{
    private Token id;
    private Expression index;

    public IDExpression(Token id, Expression index)
    {
        this.id = id;
        this.index = index;
    }
}
