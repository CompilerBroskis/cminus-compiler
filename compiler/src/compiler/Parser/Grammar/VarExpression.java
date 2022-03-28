package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class VarExpression extends Expression
{
    private Token id;
    private Expression index;

    public VarExpression(Token id, Expression index)
    {
        this.id = id;
        this.index = index;
    }

    public Token getID()
    {
        return id;
    }
}
