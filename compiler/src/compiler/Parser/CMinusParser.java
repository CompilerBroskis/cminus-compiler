package compiler.Parser;

import java.io.FileNotFoundException;

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
		Program program = parseProgram();
        program.print();

		return program;
	}

	public void matchToken(TokenType type)
	{
		Token t = scan.getNextToken();
		if(t.getTokenType() != type)
		{
			throw new CMinusException("Invalid Token: " + t.getTokenType());
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
		Token ID = scan.getNextToken();

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
	public String[] parseLocalDeclarations()
	{
		List<String> decls = new ArrayList<String>();
		while(scan.viewNextToken().getTokenType() == TokenType.INT_TOKEN)
		{
			matchToken(TokenType.INT_TOKEN);
			Token id = scan.getNextToken();
			Integer num = null;
			if(scan.viewNextToken().getTokenType() == TokenType.LB_TOKEN)
			{
				matchToken(TokenType.LB_TOKEN);
				num = (Integer)scan.getNextToken().tokenData(); // NUM_TOKEN
				matchToken(TokenType.RB_TOKEN);
			}
			matchToken(TokenType.SC_TOKEN);
			decls.add(id.tokenData().toString());
		}
		return decls.toArray(new String[0]);
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
		throw new CMinusException("Invalid Statement");
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
			Token id = scan.getNextToken();
			VarExpression vare = new VarExpression(id, null);
			return parseExpressionPrime(vare);
		}
		// expression -> NUM simple-expression'
		else if(scan.viewNextToken().getTokenType() == TokenType.NUM_TOKEN)
		{
			Token num = scan.getNextToken();
			NumExpression nume = new NumExpression(num);
			return parseSimpleExpressionPrime(nume);
		}
		// expression -> ( expression ) simple-expression'
		else if (scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN)
		{
			matchToken(TokenType.LP_TOKEN);
			Expression insideParentheses = parseExpression();
			matchToken(TokenType.RP_TOKEN);
			Expression seprime = parseSimpleExpressionPrime(insideParentheses);
			return seprime;
		}
		throw new CMinusException("Invalid Expression");
	}

	//expression' -> = expression | [ expression ] expression'' | ( args ) | simple-expression' 
	public Expression parseExpressionPrime(VarExpression ide)
	{
		// expression' -> = expression
		if(scan.viewNextToken().getTokenType() == TokenType.ASSIGN_TOKEN)
		{
			matchToken(TokenType.ASSIGN_TOKEN);
			return new AssignExpression(ide, parseExpression());
		}
		// expression' -> ( args )
		else if(scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN)
		{
			matchToken(TokenType.LP_TOKEN);
			Expression[] args = parseArgs();
			matchToken(TokenType.RP_TOKEN);
			return new CallExpression(ide.getID(), args);
		}
		// expression' -> [ expression ] expression''
		else if(scan.viewNextToken().getTokenType() == TokenType.LB_TOKEN)
		{
			matchToken(TokenType.LB_TOKEN);
			Expression insideBrackets = parseExpression();
			matchToken(TokenType.RB_TOKEN);
			VarExpression vare = new VarExpression(ide.getID(), insideBrackets);
			return parseExpressionDoublePrime(vare);
		}
		// expression' -> simple-expression'
		else
		{
			return parseSimpleExpressionPrime(ide);
		}
	}

	//expression'' -> = expression | simple-expression'
	public Expression parseExpressionDoublePrime(Expression lhs)
	{
		// expression'' -> = expression
		if(scan.viewNextToken().getTokenType() == TokenType.ASSIGN_TOKEN)
		{
			matchToken(TokenType.ASSIGN_TOKEN);
			return new AssignExpression(lhs, parseExpression());
		}
		// expression'' -> simple-expression'
		else
		{
			return parseSimpleExpressionPrime(lhs);
		}
	}

	// simple-expression' -> additive-expression' [ relop additive-expression ]
	public Expression parseSimpleExpressionPrime(Expression e)
	{

		Expression aep = parseAdditiveExpressionPrime(e);
		Token r = null;
		Expression ae = null;

		if(scan.viewNextToken().getTokenType() == TokenType.LTEQ_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.GTEQ_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.GT_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.LT_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.EQEQ_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.NOTEQ_TOKEN)
		{
			r = scan.getNextToken();
			ae = parseAdditiveExpression();
			return new BinaryOpExpression(aep, ae, r);
		}

		return aep;
	}

	// additive-expression -> term { addop term }
	public Expression parseAdditiveExpression()
	{
		Expression term = parseTerm();
		while(scan.viewNextToken().getTokenType() == TokenType.PLUS_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.MINUS_TOKEN)
		{
			Token op = scan.getNextToken();
			Expression newterm = new BinaryOpExpression(term, parseTerm(), op);
			term = newterm;
		}

		return term;
	}

	// additive-expression' -> term' { addop term }
	public Expression parseAdditiveExpressionPrime(Expression lhs)
	{
		Expression termPrime = parseTermPrime(lhs);
		while(scan.viewNextToken().getTokenType() == TokenType.PLUS_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.MINUS_TOKEN)
		{
			Token op = scan.getNextToken();
			Expression newterm = new BinaryOpExpression(termPrime, parseTerm(), op);
			termPrime = newterm;
		}

		return termPrime;
	}

	// term -> factor { mulop factor }
	public Expression parseTerm()
	{
		Expression factor = parseFactor();
		while(scan.viewNextToken().getTokenType() == TokenType.MUL_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.DIV_TOKEN)
		{
			Token op = scan.getNextToken();
			Expression newfactor = new BinaryOpExpression(factor, parseFactor(), op);
			factor = newfactor;
		}
		return factor;
	}

	public Expression parseTermPrime(Expression lhs)
	{
		Expression factor = lhs; // this passed down from expression -> NUM simple-expression' to handle a single num
		while(scan.viewNextToken().getTokenType() == TokenType.MUL_TOKEN
		|| scan.viewNextToken().getTokenType() == TokenType.DIV_TOKEN)
		{
			Token op = scan.getNextToken();
			Expression newfactor = new BinaryOpExpression(factor, parseFactor(), op);
			factor = newfactor;
		}
		return factor;
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
			return parseVarCall(id);
		}
		// factor -> NUM
		else if(scan.viewNextToken().getTokenType() == TokenType.NUM_TOKEN)
		{
			Token num = scan.getNextToken();
			return new NumExpression(num);
		}

		throw new CMinusException("Invalid Factor");
	}

	// varcall -> ( args ) | [ '[' expression ']' ]
	public Expression parseVarCall(Token id)
	{
		// varcall -> ( args )
		if(scan.viewNextToken().getTokenType() == TokenType.LP_TOKEN)
		{
			matchToken(TokenType.LP_TOKEN);
			Expression[] args = parseArgs();
			matchToken(TokenType.RP_TOKEN);
			return new CallExpression(id, args);
		}
		// varcall -> [expression]
		else if(scan.viewNextToken().getTokenType() == TokenType.LB_TOKEN)
		{
			matchToken(TokenType.LB_TOKEN);
			Expression e = parseExpression();
			matchToken(TokenType.RB_TOKEN);
			return new VarExpression(id, e);
		}
		// varcall -> epsilon
		return new VarExpression(id, null);
	}

	// args -> [ arg-list ]
	public Expression[] parseArgs()
	{
		if(scan.viewNextToken().getTokenType() == TokenType.RP_TOKEN)
		{
			return null;
		}
		return parseArgList();
	}

	public Expression[] parseArgList()
	{
		List<Expression> args = new ArrayList<Expression>();
		Expression e = parseExpression();
		if(e != null)
		{
			args.add(e);
		}
		while(scan.viewNextToken().getTokenType() == TokenType.COMMA_TOKEN)
		{
			matchToken(TokenType.COMMA_TOKEN);
			Expression e2 = parseExpression();
			if(e2 != null)
			{
				args.add(e2);
			}
			else
			{
				throw new CMinusException("Invalid Args");
			}
		}
		Expression[] arr = args.toArray(new Expression[0]);
		return arr;
	}
}