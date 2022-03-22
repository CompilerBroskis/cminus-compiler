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
}
