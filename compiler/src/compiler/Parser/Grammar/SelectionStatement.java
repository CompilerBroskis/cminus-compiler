package compiler.Parser.Grammar;

public class SelectionStatement {

    private Expression e;
    private Statement s1; 
    private Statement s2;

    public SelectionStatement(Expression e, Statement s1, Statement s2)
    {
        this.e = e;
        this.s1 = s1;
        this.s2 = s2;
    }

    public void print(String indent) 
    {
        System.out.println(indent + "if {");
        
        e.print(indent + " ");
        s1.print(indent + " ");

        if(s2 != null)
        {
            System.out.println(indent + "else: ");
            s2.print(indent + " ");
        }
        
        System.out.println(indent + "}");
    }
    
}
