package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class NumExpression extends Expression
{
    private Token num; 
    
    public NumExpression(Token num)
    {
        this.num = num;
    }

    @Override
    public void print(String indent) 
    {
        System.out.println(indent + num.tokenData());
    }
}
