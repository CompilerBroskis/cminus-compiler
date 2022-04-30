package compiler.Parser.Grammar;

import compiler.CMinusCompiler;
import compiler.Scanner.Token;
import compiler.Scanner.Token.TokenType;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;

public class Declaration
{
    private Token ID;
    private DeclarationPrime dp;
    private FunctionDeclarationPrime fdp;

    public Declaration()
    {
        
    }

    public Declaration(Token id, DeclarationPrime DP)
    {
        ID = id;
        dp = DP;
    }
    
    public Declaration(Token id, FunctionDeclarationPrime FDP)
    {
        ID = id;
        fdp = FDP;
    }

    public boolean isInt()
    {
        return (dp != null);
    }

    public Token getToken()
    {
        return ID;
    }

    public DeclarationPrime getDeclarationPrime()
    {
        return dp;
    }

    public FunctionDeclarationPrime getFunDeclarationPrime()
    {
        return fdp;
    }

    public Declaration parseDeclaration()
    {

        return null;
    }

    public void print(String indent)
    {
        System.out.println(indent + "Declaration {");
        if(isInt())
        {
            System.out.println(indent + " int "+ ID.tokenData());
            dp.print(indent + " ");
        }
        else
        {
            System.out.println(indent + " void " + ID.tokenData());
            fdp.print(indent + " ");
        }
        System.out.println(indent + "}");
    }

    public CodeItem genLLCode() 
    {
        if(isInt() && getDeclarationPrime().getFunctionDeclarationPrime() == null)
        {
            //decl'
            //Check if declprime has array
            Data data = new Data(Data.TYPE_INT, getToken().tokenData().toString());
            //public Data(int type, String newName, boolean array, int size) for if num !=null
            CMinusCompiler.globalHash.put(getToken().tokenData().toString(), getToken().tokenData().toString());

            
            return data;
        }
        else 
        {
            //fundecl
            FunctionDeclarationPrime fdp = null;
            int type = -1;
            if(getDeclarationPrime() !=null) {
                fdp = getDeclarationPrime().getFunctionDeclarationPrime();
                type = Data.TYPE_INT;
            }
            else {
                fdp = getFunDeclarationPrime();
                type = Data.TYPE_VOID;
            }
            Function function = new Function(type, getToken().tokenData().toString());
            Token[] params = fdp.getParams();

            if(params !=null){
                FuncParam headParam = null;
                FuncParam currentParam = null;
                for(Token p : params)
                {
                    FuncParam newParam = null;
                    if(p.getTokenType() == TokenType.VOID_TOKEN){
                        continue;
                    }
                    else 
                    {
                        newParam = new FuncParam(Data.TYPE_INT, p.tokenData().toString());
                    }

                    if(headParam == null)
                    {
                        headParam = newParam;
                        function.setFirstParam(headParam);
                    }
                    else{                        
                        currentParam.setNextParam(newParam);
                        function.getTable().put(p.tokenData().toString(), function.getNewRegNum());
                    }
                    currentParam = newParam;
                }
            }

            function.createBlock0();
            BasicBlock block = new BasicBlock(function);
            function.appendBlock(block);
            function.setCurrBlock(block);
            fdp.getCompoundStatement().genLLCode(function);

            // append the main chain then append the unconnected chain
            function.appendBlock(function.getReturnBlock());
            if(function.getFirstUnconnectedBlock() !=null)
            {
                function.appendBlock(function.getFirstUnconnectedBlock());
            }
            
            return function;
        }
    }
}
