package compiler.Parser.Grammar;

import java.util.Map;

import compiler.Scanner.Token;

public class AdditiveExpressionPrime {

    private Map<Expression, Token> terms;

    public AdditiveExpressionPrime(Map<Expression, Token> terms) {
        this.terms = terms;
    }
    
}
