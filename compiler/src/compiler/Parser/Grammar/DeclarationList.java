package compiler.Parser.Grammar;

import compiler.Scanner.Token;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;

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
        CodeItem item = null;
        for(Declaration decl : declarations)
        {
            CodeItem declItem = decl.genLLCode();
            if(item == null){
                item = declItem;
            }
            else{
                item.setNextItem(declItem);
                item = declItem;
            }
        }


        return item;
    }
}
