package compiler.Parser.Grammar;

import java.util.function.Function;

import compiler.Scanner.Token;

public class Declaration
{
    private Token ID;
    private DeclarationPrime dp;
    private FunctionDeclarationPrime fdp;

    public Declaration()
    {
        
    }

    public Declaration(Token id, DeclarationPrime DP)
    {
        ID = id;
        dp = DP;
    }
    
    public Declaration(Token id, FunctionDeclarationPrime FDP)
    {
        ID = id;
        fdp = FDP;
    }

    public boolean isInt()
    {
        return (dp != null);
    }

    public Declaration parseDeclaration()
    {

        return null;
    }
}
