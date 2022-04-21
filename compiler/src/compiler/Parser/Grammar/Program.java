package compiler.Parser.Grammar;

import lowlevel.CodeItem;

public class Program
{
    private DeclarationList declList;
    
    // CONSTRUCTORS
    public Program()
    {
        
    }

    public Program(DeclarationList d)
    {
        declList = d;
    }

    // GETTERS
    public DeclarationList getDeclarationList()
    {
        return declList;
    }

    public void print() 
    {
        String indent = "   ";
        System.out.println("Program {");
        declList.print(indent);
        System.out.println("}");
    }

    public CodeItem genLLCode()
    {
        return declList.genLLCode();
    }
}
