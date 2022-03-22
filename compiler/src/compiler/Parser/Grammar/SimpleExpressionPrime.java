package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class SimpleExpressionPrime {

    private AdditiveExpressionPrime aep;
    private Token r;
    private AdditiveExpression ae;

    public SimpleExpressionPrime(AdditiveExpressionPrime aep, Token r, AdditiveExpression ae) {
        this.aep = aep;
        this.r = r;
        this.ae = ae;
    }
    
}
