package compiler.Parser;

import java.io.FileNotFoundException;

import compiler.Parser.Grammar.DeclarationList;
import compiler.Parser.Grammar.*;
import compiler.Scanner.*;
import compiler.Scanner.Token.TokenType;

import java.util.ArrayList;
import java.util.List;


public class CMinusParser implements Parser
{
    private Scanner scan;

	public CMinusParser(String file) throws FileNotFoundException
	{
		scan = new CMinusScanner(file);
	}

	public Program parse()
	{
		// parsing code here
		Program program = new Program();
		program.parseProgram();
        return null;
	}

	public Program parseProgram()
	{
		return new Program(parseDeclarationList());
	}

	// declaration-list -> declaration { declaration }
	public DeclarationList parseDeclarationList()
	{
		List<Declaration> decls = new ArrayList<Declaration>();
		
		// always need at least 1 declaration
		decls.add(parseDeclaration());

		// loop until you are done finding declarations
		// int and void are the first set of declaration
		while(scan.viewNextToken().getTokenType() == TokenType.INT_TOKEN || scan.viewNextToken().getTokenType() == TokenType.VOID_TOKEN)
		{
			decls.add(parseDeclaration());
		}

		// convert to DeclarationList
		Declaration[] arr = new Declaration[decls.size()];
		decls.toArray(arr);
		DeclarationList dl = new DeclarationList(arr);

		return dl;
	}

	// declaration -> int ID declaration' | void ID fun-declaration'
	public Declaration parseDeclaration()
	{
		boolean isInt = scan.getNextToken().getTokenType() == TokenType.INT_TOKEN; // grabs the int or void
		Token ID = scan.getNextToken(); // TODO: make sure to get data correctly

		// determine which production choice we're making
		DeclarationPrime dp;
		FunctionDeclarationPrime fdp;
		if(isInt)
		{
			dp = parseDeclarationPrime();
			return new Declaration(ID, dp);
		}
		else
		{
			fdp = parseFunctionDeclarationPrime();
			return new Declaration(ID, fdp);
		}
	}

	// declaration' -> fun-declaration' | [ '[' NUM ']' ]
	public DeclarationPrime parseDeclarationPrime()
	{
		// determine production choice
		// declaration' -> fun-declaration'
		if(scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN)
		{
			FunctionDeclarationPrime fdp = parseFunctionDeclarationPrime();
			return new DeclarationPrime(fdp);
		}
		// declaration' -> [NUM];
		else if (scan.viewNextToken().getTokenType() == TokenType.LB_TOKEN)
		{
			return new DeclarationPrime(scan.getNextToken(), scan.getNextToken(), scan.getNextToken(), scan.getNextToken());
		}
		// declaration' -> ;
		else
		{
			return new DeclarationPrime(scan.getNextToken());
		}
	}

	public FunctionDeclarationPrime parseFunctionDeclarationPrime()
	{
		return null;
	}

	public String[] parseParams()
	{
		return null;
	}

	public String[] parseParamList()
	{
		return null;
	}

	public String parseParam()
	{
		return null;
	}

	public CompoundStatement parseCompoundStatement()
	{
		return null;
	}

	public String parseLocalDeclaration()
	{
		return null;
	}

	public Statement[] parseStatementList()
	{
		return null;
	}

	public Statement parseStatement()
	{
		return null;
	}

	public ExpressionStatement parseExpressionStatement()
	{
		return null;
	}

	public SelectionStatement parseSelectionStatement()
	{
		return null;
	}

	public IterationStatement parseIterationStatement()
	{
		return null;
	}

	public ReturnStatement parseReturnStatement()
	{
		return null;
	}

	public Expression parseExpression()
	{
		return null;
	}

	public ExpressionPrime parseExpressionPrime()
	{
		return null;
	}

	public ExpressionDoublePrime parseExpressionDoublePrime()
	{
		return null;
	}

	public SimpleExpressionPrime parseSimpleExpressionPrime()
	{
		return null;
	}

	public String parseRelop()
	{
		return null;
	}

	public AdditiveExpression parseAdditiveExpression()
	{
		return null;
	}

	public AdditiveExpressionPrime parseAdditiveExpressionPrime()
	{
		return null;
	}

	public String parseAddop()
	{
		return null;
	}

	public Expression parseTerm()
	{
		return null;
	}

	public Expression parseTermPrime()
	{
		return null;
	}

	public Expression parseFactor()
	{
		return null;
	}

	public String parseMulop()
	{
		return null;
	}

	public String parseVarCall()
	{
		return null;
	}

	public Expression[] parseArgs()
	{
		return null;
	}

	public Expression[] parseArgList()
	{
		return null;
	}
}