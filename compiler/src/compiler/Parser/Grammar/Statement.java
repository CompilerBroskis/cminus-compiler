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
}
