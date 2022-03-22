package compiler.Parser.Grammar;

import java.util.Map;

import compiler.Scanner.Token;

public class AdditiveExpression {

    private Map<Expression, Token> terms;

    public AdditiveExpression(Map<Expression, Token> terms) {
        this.terms = terms;
    }
    
}
