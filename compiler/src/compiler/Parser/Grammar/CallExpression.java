package compiler.Parser.Grammar;

import compiler.Scanner.Token;

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
    public String print() {
        // TODO Auto-generated method stub
        return null;
    }
}
