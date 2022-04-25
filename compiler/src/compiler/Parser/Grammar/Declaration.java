package compiler.Parser.Grammar;

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

    public Token getToken()
    {
        return ID;
    }

    public DeclarationPrime getDeclarationPrime()
    {
        return dp;
    }

    public FunctionDeclarationPrime getFunDeclarationPrime()
    {
        return fdp;
    }

    public Declaration parseDeclaration()
    {

        return null;
    }

    public void print(String indent)
    {
        System.out.println(indent + "Declaration {");
        if(isInt())
        {
            System.out.println(indent + " int "+ ID.tokenData());
            dp.print(indent + " ");
        }
        else
        {
            System.out.println(indent + " void " + ID.tokenData());
            fdp.print(indent + " ");
        }
        System.out.println(indent + "}");
    }
}
