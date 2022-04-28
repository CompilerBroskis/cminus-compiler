package compiler.Parser.Grammar;

import lowlevel.CodeItem;
import lowlevel.Function;

public class CompoundStatement {

    private String[] localDecls;
    private Statement[] statements;

    public CompoundStatement(String[] localDecls, Statement[] statements) {
        this.statements = statements;
        this.localDecls = localDecls;
    }

    public void print(String indent) {
        System.out.println(indent + "CompoundStatement {");
        
        if(localDecls !=null && localDecls.length > 0){
            for(String e : localDecls)
            {
                e.print(indent + " ");
            }
        }

        if(statements !=null && statements.length > 0){
            for(Statement s : statements)
            {
                s.print(indent + " ");
            }
        }

        System.out.println(indent + "}");
    }

    public void genLLCode(Function function) // return type may not be void
    {
        for(String v : localDecls)
        {
            function.getTable().put(v, function.getNewRegNum());
        }
        for(Statement s : statements)
        {
            s.genLLCode(function);
        }
        
    }
    
}
