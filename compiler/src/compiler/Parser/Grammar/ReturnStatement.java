package compiler.Parser.Grammar;

public class ReturnStatement
{

    private Expression e;

    public ReturnStatement(Expression e) {
        this.e = e;
    }

    public void print(String indent) 
    {
        System.out.println(indent + "return {");
        
        e.print(indent + " ");
        
        System.out.println(indent + "}");
    }
    
}
