package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class DeclarationPrime 
{
    private FunctionDeclarationPrime fdp;
    private Token num;
    
    public DeclarationPrime()
    {
    }

    public DeclarationPrime(FunctionDeclarationPrime FDP)
    {
        fdp = FDP;
    }

    public DeclarationPrime(Token NUM)
    {
        num = NUM;
    }

    public FunctionDeclarationPrime getFunctionDeclarationPrime()
    {
        return fdp;
    }

    public void print(String indent)
    {
        if(fdp != null || num != null)
        {
            System.out.println(indent + "Declaration Prime {");
            if(fdp != null)
            {
                // fun-declaration'
                fdp.print(indent + " ");
            }
            else if(num != null)
            {
                // [ NUM ]
                System.out.println(indent + " [" + num.tokenData() + "]");
            }
            System.out.println(indent + "}");
        }
    }
}
