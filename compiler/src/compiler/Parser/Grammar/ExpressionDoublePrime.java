package compiler.Parser.Grammar;

public class ExpressionDoublePrime {
    
    private Expression e;
    private SimpleExpressionPrime sep;

    public ExpressionDoublePrime(Expression e) {
        this.e = e;
    }

    public ExpressionDoublePrime(SimpleExpressionPrime sep) {
        this.sep = sep;
    }

}
