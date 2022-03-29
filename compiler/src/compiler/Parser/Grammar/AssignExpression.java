package compiler.Parser.Grammar;

public class AssignExpression extends Expression
{
    private Expression lhs;
    private Expression rhs;
    
    public AssignExpression(Expression lhs, Expression rhs)
    {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void print(String indent) 
    {
        System.out.println(indent + "=");
        lhs.print(indent + " ");
        rhs.print(indent + " ");
    }
}
