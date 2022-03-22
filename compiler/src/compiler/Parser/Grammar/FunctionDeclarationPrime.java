package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class FunctionDeclarationPrime 
{
    private Token[] params; // not sure what type this needs to be
    private CompoundStatement cs;

    public FunctionDeclarationPrime(Token[] p, CompoundStatement CS)
    {
        params = p;
        cs = CS;
    }
}
