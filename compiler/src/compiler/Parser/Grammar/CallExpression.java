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
}
