package compiler.Parser.Grammar;

import lowlevel.Function;

public class ExpressionStatement {
    private Expression e;

    public ExpressionStatement()
    {

    }

    public ExpressionStatement(Expression exp)
    {
        e = exp;
    }

    public void print(String indent) 
    {
        if(e !=null)
        {
            e.print(indent + " ");
        }
    }

    public void genLLCode(Function function)
    {
        if(e != null)
        {
            e.genLLCode(function);
        }
    }
}
