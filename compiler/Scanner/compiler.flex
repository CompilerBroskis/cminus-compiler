/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2018  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/* Java 1.2 language lexer specification */

/* Use together with unicode.flex for Unicode preprocesssing */
/* and java12.cup for a Java 1.2 parser                      */

/* Note that this lexer specification is not tuned for speed.
   It is in fact quite slow on integer and floating point literals, 
   because the input is read twice and the methods used to parse
   the numbers are not very fast. 
   For a production quality application (e.g. a Java compiler) 
   this could be optimized */
package compiler.Scanner;

import compiler.Scanner.Scanner;
import compiler.Scanner.Token;
import compiler.Scanner.Token.TokenType;
import java.io.IOException;

%%

%public
%class JFlexScanner
%implements Scanner
%function next_token
%type Token
//implements sym

%unicode

%line
%column

%cup
%cupdebug

%{
  private Token nextToken;

  public Token viewNextToken()
  {
      return nextToken;
  }

  public Token getNextToken(){
    Token returnToken = nextToken;
    try { 
      this.nextToken = next_token();
    } catch (IOException e) {
      throw new RuntimeException("Unable to fetch next token from stream", e); 
    } 
    return returnToken;
  }

  private Token token(TokenType type) {
    return new Token(type);
  }

  private Token token(TokenType type, Object value) {
    return new Token(type, value);
  }
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"

/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*
    
/* floating point literals */        
DoubleLiteral = ({FLit1})

FLit1    = [0-9]+ \. [0-9]* 

%%

<YYINITIAL> {

  /* keywords */
  "double"                       { return token(TokenType.DOUBLE_TOKEN); }
  "else"                         { return token(TokenType.ELSE_TOKEN); }
  "int"                          { return token(TokenType.INT_TOKEN); }
  "if"                           { return token(TokenType.IF_TOKEN); }
  "return"                       { return token(TokenType.RETURN_TOKEN); }
  "void"                         { return token(TokenType.VOID_TOKEN); }
  "while"                        { return token(TokenType.WHILE_TOKEN); }
  
  /* separators */
  "("                            { return token(TokenType.LP_TOKEN); }
  ")"                            { return token(TokenType.RP_TOKEN); }
  "{"                            { return token(TokenType.LCB_TOKEN); }
  "}"                            { return token(TokenType.RCB_TOKEN); }
  "["                            { return token(TokenType.LB_TOKEN); }
  "]"                            { return token(TokenType.RB_TOKEN); }
  ";"                            { return token(TokenType.SC_TOKEN); }
  ","                            { return token(TokenType.COMMA_TOKEN); }
  
  /* operators */
  "="                            { return token(TokenType.ASSIGN_TOKEN); }
  ">"                            { return token(TokenType.GT_TOKEN); }
  "<"                            { return token(TokenType.LT_TOKEN); }
  "=="                           { return token(TokenType.EQEQ_TOKEN); }
  "<="                           { return token(TokenType.LTEQ_TOKEN); }
  ">="                           { return token(TokenType.GTEQ_TOKEN); }
  "!="                           { return token(TokenType.NOTEQ_TOKEN); }
  "+"                            { return token(TokenType.PLUS_TOKEN); }
  "-"                            { return token(TokenType.MINUS_TOKEN); }
  "*"                            { return token(TokenType.MUL_TOKEN); }
  "/"                            { return token(TokenType.DIV_TOKEN); }
  
  /* numeric literals */

  {DecIntegerLiteral}            { return token(TokenType.INT_TOKEN, Integer.valueOf(yytext())); }
  
  {DoubleLiteral}                { return token(TokenType.DOUBLE_TOKEN, new Double(yytext())); }
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return token(TokenType.ID_TOKEN, yytext()); }  
}

/* error fallback */
[^]                              { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return token(TokenType.EOF_TOKEN); }