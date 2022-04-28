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
            //Check if it is a vardecl or INT fundecl
            if(decl.isInt())
            {
                //If this is a fundecl type INT
                if(decl.getDeclarationPrime().getFunctionDeclarationPrime() !=null)
                {
                    //Not sure if DATA is the correct class to determine the enum of VOID or INT???
                    Function function = new Function(Data.TYPE_INT, decl.getToken().tokenData().toString());
                    FunctionDeclarationPrime fdp = decl.getDeclarationPrime().getFunctionDeclarationPrime();

                    Token[] params = fdp.getParams();

                    FuncParam param = null;
                    for(Token p : params)
                    {
                        if(param == null)
                        {
                            param = new FuncParam(Data.TYPE_INT, p.tokenData().toString());
                            function.setFirstParam(param);
                        }
                        else{
                            FuncParam newParam = new FuncParam(Data.TYPE_INT, p.tokenData().toString());
                            param.setNextParam(newParam);
                            param = newParam;
                        }
                    }


                    //Does this need to be run every time???
                    function.createBlock0();
                    BasicBlock block = function.getFirstBlock();
                    function.setCurrBlock(block);

                    fdp.getCompoundStatement().genLLCode();

                    // append the main chain then append the unconnected chain
                    function.appendBlock(function.getReturnBlock());
                    function.appendBlock(function.getFirstUnconnectedBlock());
                    
                    if(item == null){
                        item = function;
                    }
                    else{
                        item.setNextItem(function);
                        item = function;
                    }

                }
                else
                {
                    //Check if declprime has array
                    //vardecl
                    Data data = new Data(Data.TYPE_INT, decl.getToken().tokenData().toString());
                    //public Data(int type, String newName, boolean array, int size) for if num !=null
                    
                    if(item == null){
                        item = data;
                    }
                    else{
                        item.setNextItem(data);
                        item = data;
                    }
                }
            }
            else 
            {
                //fundecl type void

                FunctionDeclarationPrime fdp = decl.getFunDeclarationPrime();
                Function function = new Function(Data.TYPE_VOID, decl.getToken().tokenData().toString());
                Token[] params = fdp.getParams();

                FuncParam param = null;
                for(Token p : params)
                {
                    if(param == null)
                    {
                        param = new FuncParam(Data.TYPE_INT, p.tokenData().toString());
                        function.setFirstParam(param);
                    }
                    else{
                        FuncParam newParam = new FuncParam(Data.TYPE_INT, p.tokenData().toString());
                        param.setNextParam(newParam);
                        param = newParam;
                        // table.add var name and function.getnextregnum
                    }
                }

                function.createBlock0();
                BasicBlock block = new BasicBlock(function);
                function.appendBlock(block);
                function.setCurrBlock(block);
                fdp.getCompoundStatement().genLLCode(function);
                
                if(item == null){
                    item = function;
                }
                else{
                    item.setNextItem(function);
                    item = function;
                }
                // append 
            }
        }


        return item;
    }
}
