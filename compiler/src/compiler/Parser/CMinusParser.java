package compiler.Parser;

import java.io.FileNotFoundException;

import compiler.Parser.Grammar.*;
import compiler.Scanner.*;
import compiler.Scanner.Token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.management.RuntimeErrorException;


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
		parseProgram();
        return null;
	}

	public void matchToken(TokenType type)
	{
		Token t = scan.getNextToken();
		if(t.getTokenType() != type)
		{
			throw new RuntimeException("Invalid Token: " + t.getTokenType());
		}
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
		Declaration[] arr = decls.toArray(new Declaration[0]);
		DeclarationList dl = new DeclarationList(arr);

		return dl;
	}

	// declaration -> int ID declaration' | void ID fun-declaration'
	public Declaration parseDeclaration()
	{
		boolean isInt = scan.getNextToken().getTokenType() == TokenType.INT_TOKEN; // grabs the int or void
		Token ID = scan.getNextToken(); // TODO: make sure to get data correctly

		// determine which production choice we're making
		if(isInt)
		{
			DeclarationPrime dp = parseDeclarationPrime();
			return new Declaration(ID, dp);
		}
		else
		{
			FunctionDeclarationPrime fdp = parseFunctionDeclarationPrime();
			return new Declaration(ID, fdp);
		}
	}

	// declaration' -> fun-declaration' | [ '[' NUM ']' ] ;
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
			matchToken(TokenType.LB_TOKEN);
			DeclarationPrime dp = new DeclarationPrime(scan.getNextToken());
			matchToken(TokenType.RB_TOKEN);
			matchToken(TokenType.SC_TOKEN);
			return dp;
		}
		// declaration' -> ;
		else
		{
			DeclarationPrime dp = new DeclarationPrime();
			matchToken(TokenType.SC_TOKEN);
			return dp;
		}
	}

	// fun-declaration' -> ( [ params ] ) compound-stmt
	public FunctionDeclarationPrime parseFunctionDeclarationPrime()
	{
		matchToken(TokenType.LP_TOKEN);
		Token[] params = null;
		if(scan.viewNextToken().getTokenType() == TokenType.INT_TOKEN)
		{
			params = parseParams();
		}
		matchToken(TokenType.RP_TOKEN);
		CompoundStatement cs = parseCompoundStatement();

		return new FunctionDeclarationPrime(params, cs);
	}

	// params -> param-list | void
	public Token[] parseParams()
	{
		if(scan.viewNextToken().getTokenType() == TokenType.VOID_TOKEN)
		{
			return new Token[]{scan.getNextToken()};
		}
		else
		{
			return parseParamList();
		}
	}

	// param-list -> param {, param }
	public Token[] parseParamList()
	{
		List<Token> IDs = new ArrayList<Token>();

		IDs.add(parseParam());

		while(scan.viewNextToken().getTokenType() == TokenType.COMMA_TOKEN)
		{
			matchToken(TokenType.COMMA_TOKEN);
			IDs.add(parseParam());
		}

		// convert to DeclarationList
		Token[] arr = IDs.toArray(new Token[0]);

		return arr;
	}

	// param -> int ID [ '[' ']' ]
	public Token parseParam()
	{
		matchToken(TokenType.INT_TOKEN);
		Token id = scan.getNextToken();
		//TODO: Look into storing array flag
		if(scan.viewNextToken().getTokenType() == TokenType.LB_TOKEN){
			matchToken(TokenType.LB_TOKEN);
			matchToken(TokenType.RB_TOKEN);
		}
		return id;
	}

	// compund-stmt -> { local-declarations statement-list }
	public CompoundStatement parseCompoundStatement()
	{
		matchToken(TokenType.LCB_TOKEN);
		CompoundStatement cs = new CompoundStatement(parseLocalDeclarations(), parseStatementList());
		matchToken(TokenType.RCB_TOKEN);
		return cs;
	}

	// local-declarations -> { int ID [ '[' NUM ']' ] ; }
	public Map<Token, Token> parseLocalDeclarations()
	{
		Map<Token, Token> map = new TreeMap<Token, Token>();
		while(scan.viewNextToken().getTokenType() == TokenType.INT_TOKEN)
		{
			matchToken(TokenType.INT_TOKEN);
			Token id = scan.getNextToken();
			Token num = null;
			if(scan.viewNextToken().getTokenType() == TokenType.LB_TOKEN)
			{
				matchToken(TokenType.LB_TOKEN);
				num = scan.getNextToken(); //NUM_TOKEN
				matchToken(TokenType.RB_TOKEN);
			}
			matchToken(TokenType.SC_TOKEN);
			map.put(id, num);
		}
		return map;
	}

	// statement-list -> { statement }
	public Statement[] parseStatementList()
	{
		List<Statement> list = new ArrayList<Statement>();
		while(scan.viewNextToken().getTokenType() == TokenType.IF_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.WHILE_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.RETURN_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.ID_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.SC_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.NUM_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.LCB_TOKEN
		)
		{
			list.add(parseStatement());
		}
		
		Statement[] arr = list.toArray(new Statement[0]);
		return arr;
	}

	// statement -> expression-stmt | compound-stmt | selection-stmt | iteration-stmt | return-stmt
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