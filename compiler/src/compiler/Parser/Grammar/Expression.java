package compiler.Parser.Grammar;

import lowlevel.Function;

public abstract class Expression {

    private int regNum = -2;

    public void setRegNum(int num)
    {
        this.regNum = num;
    }

    public int getRegNum()
    {
        return regNum;
    }

    public abstract void print(String indent);

    public abstract void genLLCode(Function function);
}
