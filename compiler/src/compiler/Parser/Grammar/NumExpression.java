package compiler.Parser.Grammar;

import compiler.Scanner.Token;
import lowlevel.Function;

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

    @Override
    public void genLLCode(Function function)
    {
        // Assign yourself a register
        setRegNum(function.getNewRegNum()); // right???

        // How does the register know the value of the num expression?
    }
}
