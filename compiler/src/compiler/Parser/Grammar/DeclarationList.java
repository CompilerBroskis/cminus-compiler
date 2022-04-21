package compiler.Parser.Grammar;

import lowlevel.CodeItem;

public class DeclarationList
{
    private Declaration[] declarations;

    public DeclarationList(Declaration[] d)
    {
        declarations = d;
    }

    public void print(String indent)
    {
        for(Declaration d : declarations)
        {
            d.print(indent);
        }
    }

    public CodeItem genLLCode()
    {
        return null;
    }
}
