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
        IN_COMMENT,
        IN_COMMENT_START,
        IN_NUM,
        IN_DOUBLE,
        IN_ID,
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

        boolean skippedChar = false;
        char skippedCharValue;
        

        while(state != StateType.DONE)
        {
            try 
            {
                // grab one char from the file
                char c;
                if(skippedChar){
                    c = skippedCharValue;
                    skippedChar = false;
                } else {
                    c = (char)inFile.read();
                }

                switch(state)
                {
                    case START:
                        if(Character.isDigit(c))
                            state = StateType.IN_NUM;
                        else if(Character.isLetter(c))
                            state = StateType.IN_ID;
                        else if(c == '=')
                        {
                            char nextC = (char) inFile.read();
                            if(nextC == '='){
                                currentToken = new Token(TokenType.EQEQ_TOKEN);
                            }
                            else {
                                 currentToken = new Token(TokenType.ASSIGN_TOKEN);
                                 //Mark skip and pass the value for the next loop
                                 skippedChar = true;
                                 skippedCharValue = nextC;
                            }
                            state = StateType.DONE;
                        }
                        else if(c == '<') 
                        {
                            char nextC = (char) inFile.read();
                            if(nextC == '='){
                                currentToken = new Token(TokenType.LTEQ_TOKEN);
                            }
                            else {
                                currentToken = new Token(TokenType.LT_TOKEN);
                                //Mark skip and pass the value for the next loop
                                skippedChar = true;
                                skippedCharValue = nextC;
                           }
                           state = StateType.DONE;
                        }
                        else if(c == '>') 
                        {
                            char nextC = (char) inFile.read();
                            if(nextC == '='){
                                currentToken = new Token(TokenType.GTEQ_TOKEN);
                            }
                            else {
                                currentToken = new Token(TokenType.GT_TOKEN);
                                //Mark skip and pass the value for the next loop
                                skippedChar = true;
                                skippedCharValue = nextC;
                           }
                           state = StateType.DONE;
                        }
                        else if(c == '!')
                        {
                            char nextC = (char) inFile.read();
                            if(nextC == '='){
                                currentToken = new Token(TokenType.NOTEQ_TOKEN);
                            }
                            else {
                                currentToken = new Token(TokenType.ERROR_TOKEN);
                                //Mark skip and pass the value for the next loop
                                skippedChar = true;
                                skippedCharValue = nextC;
                           }
                           state = StateType.DONE;
                        }
                        else if ((c == ' ') || (c == '\t') || (c == '\n'))
                            // skip these characters
                            continue;
                        else if(c == '/')
                        {
                            char nextC = (char) inFile.read();
                            if(nextC != '*'){
                                currentToken = new Token(TokenType.DIV_TOKEN);
                                state = StateType.DONE;

                                //Mark skip and pass the value for the next loop
                                skippedChar = true;
                                skippedCharValue = nextC;
                            } 
                            else {
                                state = StateType.IN_COMMENT;
                            }
                        }
                        else
                        {
                            state = StateType.DONE;
                            switch(c)
                            {
                                case 'z': // EOF
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
                                    currentToken = new Token(TokenType.ERROR_TOKEN);
                                    break;
                            }
                        }
                        break;
                    case IN_COMMENT:
                        // Looking for the end of a comment
                        if(c == '*'){
                            char nextC = (char)inFile.read();
                        
                            if(nextC == '/'){
                                state = StateType.START; //Out of comment
                            }                            
                        }
                        break;
                    case IN_NUM:
                        if(c == '.'){
                            state = StateType.IN_DOUBLE;
                        }
                        else if(!Character.isDigit(c)){
                            currentToken = new Token(TokenType.INT_TOKEN); //TODO Give the data of the integer
                            state = StateType.DONE;

                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        break;
                    case IN_DOUBLE:
                        if(!Character.isDigit(c)){
                            currentToken = new Token(TokenType.DOUBLE_TOKEN); //TODO Give the data of the integer
                            state = StateType.DONE;

                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        break;
                    case IN_ID:
                        if(!Character.isLetter(c)){
                            currentToken = new Token(TokenType.ID_TOKEN); //TODO Give the data of the integer
                            state = StateType.DONE;

                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        break;
                    case DONE:
                    default: 
                        System.err.println("Scanner Bug: state=" + state);
                        state = StateType.DONE;
                        currentToken = new Token(TokenType.ERROR_TOKEN);
                        break;
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
