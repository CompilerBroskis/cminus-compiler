package compiler.Parser.Grammar;

public class IterationStatement {

    private Expression e;
    private Statement s;

    public IterationStatement(Expression e, Statement s) 
    {
        this.e = e;
        this.s = s;
    }

    public void print(String indent) 
    {
        System.out.println(indent + "while {");
        
        e.print(indent + " ");
        s.print(indent + " ");
        
        System.out.println(indent + "}");
    }
    
}
