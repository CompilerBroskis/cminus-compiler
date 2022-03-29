package compiler.Parser.Grammar;

import compiler.Scanner.Token;

public class FunctionDeclarationPrime 
{
    private Token[] params; // not sure what type this needs to be
    private CompoundStatement cs;

    public FunctionDeclarationPrime(Token[] p, CompoundStatement CS)
    {
        params = p;
        cs = CS;
    }

    public void print(String indent) {
        System.out.println(indent + "FunDeclPrime {");
        
        System.out.print(indent + " (");
        if(params != null && params.length > 0)
        {
            
            for(int i = 0; i < params.length; i++){
                if(i > 0)
                {
                    System.out.print(", ");
                }
                System.out.print(params[i].tokenData());
            }
        }
        System.out.print(")\n");
        
        cs.print(indent + " ");
        System.out.println(indent + "}");
    }
}
