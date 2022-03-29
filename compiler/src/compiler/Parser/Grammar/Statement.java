package compiler.Parser.Grammar;

public class Statement {
    private ExpressionStatement es;
    private SelectionStatement ss;
    private IterationStatement is;
    private ReturnStatement rs;
    private CompoundStatement cs;
    
    public Statement(ExpressionStatement s) 
    {
        es = s;
    }

    public Statement(SelectionStatement s) 
    {
        ss = s;
    }

    public Statement(IterationStatement s) 
    {
        is = s;
    }

    public Statement(ReturnStatement s) 
    {
        rs = s;
    }

    public Statement(CompoundStatement s) 
    {
        cs = s;
    }

    public void print(String indent) {
        if(es !=null)
        {
            es.print(indent + " ");
        }       
        else if(ss !=null)
        {
            ss.print(indent + " ");
        }  
        else if(is !=null)
        {
            is.print(indent + " ");
        } 
        else if(rs !=null)
        {
            rs.print(indent + " ");
        } 
        else if(cs !=null)
        {
            cs.print(indent + " ");
        } 
    }
}
