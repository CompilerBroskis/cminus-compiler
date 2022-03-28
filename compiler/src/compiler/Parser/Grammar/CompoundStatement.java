package compiler.Parser.Grammar;

import java.util.Map;

import compiler.Scanner.Token;

public class CompoundStatement {

    private VarExpression[] localDecls;
    private Statement[] statements;

    public CompoundStatement(VarExpression[] localDecls, Statement[] statements) {
        this.statements = statements;
        this.localDecls = localDecls;
    }
    
}
