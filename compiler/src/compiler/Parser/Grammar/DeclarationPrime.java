package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class DeclarationPrime 
{
    private FunctionDeclarationPrime fdp;
    private Token lb;
    private Token rb;
    private Token num;
    private Token sc;
    
    public DeclarationPrime(Token SC)
    {
        sc = SC;
    }

    public DeclarationPrime(FunctionDeclarationPrime FDP)
    {
        fdp = FDP;
    }

    public DeclarationPrime(Token LB, Token NUM, Token RB, Token SC)
    {
        lb = LB;
        num = NUM;
        rb = RB;
        sc = SC;
    }
}
