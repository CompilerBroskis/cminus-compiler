package compiler.Parser.Grammar;

public class ExpressionPrime {

    private Expression e;
    private ExpressionDoublePrime edp;
    private Expression[] args;
    private SimpleExpressionPrime sep;

    public ExpressionPrime(Expression e) {
        this.e = e;
    }

    public ExpressionPrime(Expression e, ExpressionDoublePrime edp) {
        this.e = e;
        this.edp = edp;
    }

    public ExpressionPrime(Expression[] args) {
        this.args = args;
    }

    public ExpressionPrime(SimpleExpressionPrime sep) {
        this.sep = sep;
    }
    
}
