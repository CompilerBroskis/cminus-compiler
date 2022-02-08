package compiler.Scanner;

import java.io.BufferedReader;
import java.io.IOException;

import javax.lang.model.util.ElementScanner6;

import compiler.Scanner.Token.TokenType;

public class CMinusScanner implements Scanner
{
    private BufferedReader inFile;
    private Token nextToken;

    public enum StateType
    {
        START,
        IN_ASSIGN,
        IN_COMMENT,
        IN_COMMENT_START,
        IN_NUM,
        IN_ID,
        IN_LESS_THAN,
        IN_GREATER_THAN,
        IN_NOT_EQUAL,
        DONE
    }

    public Token getNextToken()
    {
        Token returnToken = nextToken;
        if(nextToken.getTokenType() != TokenType.EOF_TOKEN)
        {
            nextToken = scanToken();
        }
        return returnToken;
    }

    public Token viewNextToken()
    {
        return nextToken;
    }

    public Token scanToken()
    {
        // stores the token to be returned
        Token currentToken = null;

        // state always begins at start
        StateType state = StateType.START;

        while(state != StateType.DONE)
        {
            try 
            {
                // grab one char from the file
                char c = (char)inFile.read();

                switch(state)
                {
                    case START:
                        if(Character.isDigit(c))
                            state = StateType.IN_NUM;
                        else if(Character.isLetter(c))
                            state = StateType.IN_ID;
                        else if(c == '=')
                            state = StateType.IN_ASSIGN;
                        else if(c == '<')
                            state = StateType.IN_LESS_THAN;
                        else if(c == '>')
                            state = StateType.IN_GREATER_THAN;
                        else if(c == '!')
                            state = StateType.IN_NOT_EQUAL;
                        else if ((c == ' ') || (c == '\t') || (c == '\n'))
                            // skip these characters
                            continue;
                        else if(c == '/')
                            state = StateType.IN_COMMENT_START;
                        else
                        {
                            state = StateType.DONE;
                            switch(c)
                            {
                                case 'z': // EOF
                                    break;
                                case '<':
                                    currentToken = new Token(TokenType.LT_TOKEN);
                                    break;
                                case '>':
                                    currentToken = new Token(TokenType.GT_TOKEN);
                                    break;
                                case '+':
                                    currentToken = new Token(TokenType.PLUS_TOKEN);
                                    break;
                                case '-':
                                    currentToken = new Token(TokenType.MINUS_TOKEN);
                                    break;
                                case '*':
                                    currentToken = new Token(TokenType.MUL_TOKEN);
                                    break;
                                case ';':
                                    currentToken = new Token(TokenType.SC_TOKEN);
                                    break;
                                case ',':
                                    currentToken = new Token(TokenType.COMMA_TOKEN);
                                    break;
                                case '(':
                                    currentToken = new Token(TokenType.LP_TOKEN);
                                    break;
                                case ')':
                                    currentToken = new Token(TokenType.RP_TOKEN);
                                    break;
                                case '[':
                                    currentToken = new Token(TokenType.LB_TOKEN);
                                    break;
                                case ']':
                                    currentToken = new Token(TokenType.RB_TOKEN);
                                    break;
                                case '{':
                                    currentToken = new Token(TokenType.LCB_TOKEN);
                                    break;
                                case '}':
                                    currentToken = new Token(TokenType.RCB_TOKEN);
                                    break;
                                default:
                                    currentToken = new Token(TokenType.ERR_TOKEN);
                                    break;
                            }
                        }
                        break;
                    case IN_COMMENT_START: // determine if division or start of comment
                        if(c == '*')
                            state = StateType.IN_COMMENT;
                        else
                            currentToken = new Token(TokenType.DIV_TOKEN);
                        break;
                    case IN_COMMENT:
                        char nextC = (char)inFile.read();
                        if(nextC != '*')
                            currentToken = new Token(TokenType.DIV_TOKEN);
                }
            } 
            
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }
}
