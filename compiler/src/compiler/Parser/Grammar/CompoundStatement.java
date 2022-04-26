package compiler.Parser.Grammar;

import lowlevel.CodeItem;
import lowlevel.Function;

public class CompoundStatement {

    private VarExpression[] localDecls;
    private Statement[] statements;

    public CompoundStatement(VarExpression[] localDecls, Statement[] statements) {
        this.statements = statements;
        this.localDecls = localDecls;
    }

    public void print(String indent) {
        System.out.println(indent + "CompoundStatement {");
        
        if(localDecls !=null && localDecls.length > 0){
            for(VarExpression e : localDecls)
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
        for(VarExpression v : localDecls)
        {
            v.genLLCode(function);
        }
        for(Statement s : statements)
        {
            Operation operation = s.genLLCode(function);
        }
    }
    
}
