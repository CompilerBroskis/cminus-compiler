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
}
