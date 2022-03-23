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
		// statement -> expression-stmt
		if(scan.viewNextToken().getTokenType() == TokenType.SC_TOKEN 
		|| scan.viewNextToken().getTokenType() == TokenType.ID_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.NUM_TOKEN
		)
		{
			return new Statement(parseExpressionStatement());
		}
		// statement -> compound-stmt
		else if (scan.viewNextToken().getTokenType() == TokenType.LCB_TOKEN)
		{
			return new Statement(parseCompoundStatement());
		}
		// statement -> selection-stmt
		else if (scan.viewNextToken().getTokenType() == TokenType.IF_TOKEN)
		{
			return new Statement(parseSelectionStatement());
		}
		// statement -> iteration-stmt
		else if (scan.viewNextToken().getTokenType() == TokenType.WHILE_TOKEN)
		{
			return new Statement(parseIterationStatement());
		}
		// statement -> return-stmt
		else if (scan.viewNextToken().getTokenType() == TokenType.RETURN_TOKEN)
		{
			return new Statement(parseReturnStatement());
		}
		throw new RuntimeException("Invalid Statement");
	}

	// expression-stmt -> [ expression ] ;
	public ExpressionStatement parseExpressionStatement()
	{
		// expression-stmt -> ;
		if(scan.viewNextToken().getTokenType() == TokenType.SC_TOKEN)
		{
			matchToken(TokenType.SC_TOKEN);
			return new ExpressionStatement();
		}
		// expression-stmt -> expression ;
		else
		{
			Expression e = parseExpression();
			matchToken(TokenType.SC_TOKEN);
			return new ExpressionStatement(e);
		}
	}

	// selection-stmt -> if ( expression ) statement [ else statement ]
	public SelectionStatement parseSelectionStatement()
	{
		matchToken(TokenType.IF_TOKEN);
		matchToken(TokenType.LP_TOKEN);
		Expression e = parseExpression();
		matchToken(TokenType.RP_TOKEN);
		Statement s1 = parseStatement();
		Statement s2 = null;
		if(scan.viewNextToken().getTokenType() == TokenType.ELSE_TOKEN)
		{
			matchToken(TokenType.ELSE_TOKEN);
			s2 = parseStatement();
		}

		return new SelectionStatement(e, s1, s2);
	}

	// iteration-stmt -> while ( expression ) statement
	public IterationStatement parseIterationStatement()
	{
		matchToken(TokenType.WHILE_TOKEN);
		matchToken(TokenType.LP_TOKEN);
		Expression e = parseExpression();
		matchToken(TokenType.RP_TOKEN);
		Statement s = parseStatement();
		return new IterationStatement(e, s);
	}

	// return-stmt -> return [ expression ] ;
	public ReturnStatement parseReturnStatement()
	{
		matchToken(TokenType.RETURN_TOKEN);
		
		if(scan.viewNextToken().getTokenType() == TokenType.SC_TOKEN)
		{
			matchToken(TokenType.SC_TOKEN);
			return new ReturnStatement(null);
		}
		
		Expression e = parseExpression();
		matchToken(TokenType.SC_TOKEN);
		return new ReturnStatement(e);
	}

	// expression -> ID expression' | NUM simple-expression' | ( expression ) simple-expression'
	public Expression parseExpression()
	{
		// expression -> ID expression'
		if(scan.viewNextToken().getTokenType() == TokenType.ID_TOKEN)
		{
			// we grab the ID from this production choice in parseExpressionPrime()
			return parseExpressionPrime();
		}
		// expression -> NUM simple-expression'
		else if(scan.viewNextToken().getTokenType() == TokenType.NUM_TOKEN)
		{
			// we grab the NUM from this production choice in parseSimpleExpressionPrime
			return parseSimpleExpressionPrime();
		}

		// expression -> ID expression'
		// if(scan.viewNextToken().getTokenType() == TokenType.ID_TOKEN)
		// {
		// 	Token id = scan.getNextToken();
		// 	ExpressionPrime ep = parseExpressionPrime();
		// 	return new Expression(id, ep);
		// }
		// // expression -> NUM simple-expression'
		// else if(scan.viewNextToken().getTokenType() == TokenType.NUM_TOKEN)
		// {
		// 	Token num = scan.getNextToken();
		// 	SimpleExpressionPrime sep = parseSimpleExpressionPrime();
		// 	return new Expression(num, sep);
		// }
		// // expression -> ( expression ) simple-expression'
		// else if(scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN)
		// {
		// 	matchToken(TokenType.LP_TOKEN);
		// 	Expression e = parseExpression();
		// 	matchToken(TokenType.RP_TOKEN);
		// 	SimpleExpressionPrime sep = parseSimpleExpressionPrime();
		// 	return new Expression(e, sep);
		// }
		
		// throw new RuntimeException("Invalid Expression");
	}

	//expression' -> = expression | [ expression ] expression'' | ( args ) | simple-expression' 
	public Expression parseExpressionPrime()
	{
		Token id = scan.getNextToken();

		// expression' -> = expression
		if(scan.viewNextToken().getTokenType() == TokenType.ASSIGN_TOKEN)
		{
			matchToken(TokenType.ASSIGN_TOKEN);
			Expression e = parseExpression();
			return new ExpressionPrime(e);
		}
		// expression' -> [ expression ] expression''
		else if(scan.viewNextToken().getTokenType() == TokenType.LB_TOKEN)
		{
			matchToken(TokenType.LB_TOKEN);
			Expression e = parseExpression();
			matchToken(TokenType.RB_TOKEN);
			ExpressionDoublePrime edp = parseExpressionDoublePrime();
			return new ExpressionPrime(e, edp);
		}
		// expression' -> ( args )
		else if(scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN)
		{
			matchToken(TokenType.LP_TOKEN);
			Expression[] args = parseArgs();
			matchToken(TokenType.RP_TOKEN);
			return new ExpressionPrime(args);
		}
		// expression' -> simple-expression' 
		else if(scan.viewNextToken().getTokenType() == TokenType.PLUS_TOKEN 
		|| scan.viewNextToken().getTokenType() == TokenType.MUL_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.GT_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.RP_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.SC_TOKEN)
		{
			return new ExpressionPrime(parseSimpleExpressionPrime());
		}

		throw new RuntimeException("Invalid Expression Prime");
	}

	//expression'' -> = expression | simple-expression'
	public ExpressionDoublePrime parseExpressionDoublePrime()
	{
		// expression'' -> = expression
		if(scan.viewNextToken().getTokenType() == TokenType.ASSIGN_TOKEN)
		{
			matchToken(TokenType.ASSIGN_TOKEN);
			Expression e = parseExpression();
			return new ExpressionDoublePrime(e);
		}
		// expression'' -> simple-expression' 
		else if(scan.viewNextToken().getTokenType() == TokenType.PLUS_TOKEN 
		|| scan.viewNextToken().getTokenType() == TokenType.MUL_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.DIV_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.MINUS_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.GT_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.RP_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.SC_TOKEN)
		{
			return new ExpressionDoublePrime(parseSimpleExpressionPrime());
		}
		
		throw new RuntimeException("Invalid Expression Double Prime");
	}

	// simple-expression' -> additive-expression' [ relop additive-expression ]
	public Expression parseSimpleExpressionPrime()
	{
		Token num = scan.getNextToken(); // NUM from expression -> NUM simple-expression'

		AdditiveExpressionPrime aep = parseAdditiveExpressionPrime();
		Token r = null;
		AdditiveExpression ae = null;

		if(scan.viewNextToken().getTokenType() == TokenType.LTEQ_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.GTEQ_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.GT_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.LT_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.EQEQ_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.NOTEQ_TOKEN)
		{
			r = scan.getNextToken();
			ae = parseAdditiveExpression();
		}

		return new SimpleExpressionPrime(aep, r, ae);
	}

	// additive-expression -> term { addop term }
	public AdditiveExpression parseAdditiveExpression()
	{
		Map<Expression, Token> terms = new TreeMap<Expression, Token>();

		terms.put(parseTerm(), null);

		while(scan.viewNextToken().getTokenType() == TokenType.PLUS_TOKEN || scan.viewNextToken().getTokenType() == TokenType.MINUS_TOKEN)
		{
			Token addop = scan.getNextToken();
			terms.put(parseTerm(), addop);
		}

		return new AdditiveExpression(terms);
	}

	// additive-expression' -> term' { addop term }
	public AdditiveExpressionPrime parseAdditiveExpressionPrime()
	{
		Map<Expression, Token> terms = new TreeMap<Expression, Token>();

		Expression termPrime = parseTermPrime();
		if(termPrime != null)
		{
			terms.put(termPrime, null);
		}

		while(scan.viewNextToken().getTokenType() == TokenType.PLUS_TOKEN || scan.viewNextToken().getTokenType() == TokenType.MINUS_TOKEN)
		{
			Token addop = scan.getNextToken();
			terms.put(parseTerm(), addop);
		}

		return new AdditiveExpressionPrime(terms);
	}

	public Expression parseTerm()
	{
		return null;
	}

	public Expression parseTermPrime()
	{
		return null;
	}

	// factor -> ( expression ) | ID varcall | NUM
	public Expression parseFactor()
	{
		// factor -> ( expression )
		if(scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN)
		{
			matchToken(TokenType.LP_TOKEN);
			Expression e = parseExpression();
			matchToken(TokenType.RP_TOKEN);
			return e;
		}
		// factor -> ID varcall
		else if(scan.viewNextToken().getTokenType() == TokenType.ID_TOKEN)
		{
			Token id = scan.getNextToken();
			String varcall = parseVarCall();
			
			return new Expression(exp, sep)
		}
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