package compiler.Parser.Grammar;

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
}
