package compiler.Scanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import compiler.Scanner.Token.TokenType;

public class CMinusScanner implements Scanner
{
    private BufferedReader inFile;
    private Token nextToken;
    private boolean skippedChar = false;
    private char skippedCharValue = '?';

    public enum StateType
    {
        START,
        IN_ASSIGN,
        IN_LT,
        IN_GT,
        IN_NOT,
        IN_COMMENT,
        IN_COMMENT_START,
        IN_COMMENT_END,
        IN_NUM,
        IN_DOUBLE,
        IN_ID,
        DONE
    }

    public CMinusScanner(String filePath) throws FileNotFoundException 
    {
        this.inFile = new BufferedReader(new FileReader(filePath));
        this.nextToken = scanToken(); // Fill nextToken with the first token
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

        String dataString = "";

        while(state != StateType.DONE)
        {
            try 
            {
                // grab one char from the file
                char c;
                if(skippedChar)
                {
                    c = skippedCharValue;
                    skippedChar = false;
                } 
                else 
                {
                    int charValue = inFile.read();

                    if(charValue == -1) //EOF Value
                    {
                        currentToken = new Token(TokenType.EOF_TOKEN);
                        state = StateType.DONE;
                        continue;
                    }

                    c = (char) charValue;
                }

                switch(state)
                {
                    case START:
                        if(Character.isDigit(c))
                        {
                            state = StateType.IN_NUM;
                            dataString += c;
                        }
                        else if(Character.isLetter(c))
                        {
                            state = StateType.IN_ID;
                            dataString += c;
                        }
                        else if(c == '=')
                        {
                            state = StateType.IN_ASSIGN;
                        }
                        else if(c == '<') 
                        {
                            state = StateType.IN_LT;
                        }
                        else if(c == '>') 
                        {
                           state = StateType.IN_GT;
                        }
                        else if(c == '!')
                        {
                           state = StateType.IN_NOT;
                        }
                        else if ((c == ' ') || (c == '\t') || (c == '\n') || (c == '\r'))
                            // skip these characters
                            continue;
                        else if(c == '/')
                        {
                            state = StateType.IN_COMMENT_START;
                        }
                        else
                        {
                            state = StateType.DONE;
                            switch(c)
                            {
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
                    case IN_ASSIGN:
                        if(c == '=')
                            currentToken = new Token(TokenType.EQEQ_TOKEN);
                        else
                        {
                            //Mark skip and pass the value for the next loop
                            currentToken = new Token(TokenType.ASSIGN_TOKEN);
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        state = StateType.DONE;
                        break;
                    case IN_LT:
                        if(c == '=')
                        {
                            currentToken = new Token(TokenType.LTEQ_TOKEN);
                        }
                        else 
                        {
                            currentToken = new Token(TokenType.LT_TOKEN);
                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        state = StateType.DONE;
                        break;
                    case IN_GT:
                        if(c == '=')
                        {
                            currentToken = new Token(TokenType.GTEQ_TOKEN);
                        }
                        else 
                        {
                            currentToken = new Token(TokenType.GT_TOKEN);
                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        state = StateType.DONE;
                        break;
                    case IN_NOT:
                        if(c == '=')
                        {
                            currentToken = new Token(TokenType.NOTEQ_TOKEN);
                        }
                        else 
                        {
                            // if there is a ! without a = then it is an error
                            currentToken = new Token(TokenType.ERROR_TOKEN);
                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        state = StateType.DONE;
                        break;
                    case IN_COMMENT_START:
                        if(c == '*')
                        {
                            state = StateType.IN_COMMENT;
                        }
                        else
                        {
                            currentToken = new Token(TokenType.DIV_TOKEN);
                            skippedChar = true;
                            skippedCharValue = c;
                            state = StateType.DONE;
                        }
                        break;
                    case IN_COMMENT:
                        // Looking for the end of a comment
                        if(c == '*')
                        {                        
                            state = StateType.IN_COMMENT_END;                     
                        }
                        break;
                    case IN_COMMENT_END:
                        if(c == '/')
                        {
                            state = StateType.START;
                        }
                        else
                        {
                            skippedChar = true;
                            skippedCharValue = c;
                            state = StateType.IN_COMMENT;
                        }
                        break;
                    case IN_NUM:
                        if(c == '.')
                        {
                            state = StateType.IN_DOUBLE;
                            dataString += c;
                        }
                        else if(!Character.isDigit(c))
                        {
                            //Prevent varaibles that start with numbers
                            if(Character.isLetter(c)){
                                currentToken = new Token(TokenType.ERROR_TOKEN); 
                                state = StateType.DONE;
                                break;
                            }                   

                            try
                            {
                                Integer intValue = Integer.parseInt(dataString);
                                currentToken = new Token(TokenType.NUM_TOKEN, intValue); 
                                state = StateType.DONE;
                                dataString = ""; //Reset Data string
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                            
                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        else 
                        {
                            dataString += c;
                        }
                        break;
                    case IN_DOUBLE:
                        if(!Character.isDigit(c))
                        {
                            try
                            {
                                Double doubleValue = Double.parseDouble(dataString);
                                currentToken = new Token(TokenType.DOUBLE_TOKEN, doubleValue);
                                state = StateType.DONE;
                                dataString = ""; //Reset Data string
                            } 
                            catch(Exception e){
                                e.printStackTrace();
                            }

                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        else 
                        {
                            dataString += c;
                        }
                        break;
                    case IN_ID:
                        if(!Character.isLetter(c) && !Character.isDigit(c))
                        {
                            currentToken = checkForKeyword(dataString);
                            state = StateType.DONE;

                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        else 
                        {
                            dataString += c;
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

        return currentToken;
    }

    //Function for checking if identifier is a keyword otherwise return the identifier
    private Token checkForKeyword(String dataString){
        if(dataString.equals("else"))
        {
            return new Token(TokenType.ELSE_TOKEN);
        }
        else if(dataString.equals("if"))
        {
            return new Token(TokenType.IF_TOKEN);
        }
        else if(dataString.equals("int"))
        {
            return new Token(TokenType.INT_TOKEN);
        }
        else if(dataString.equals("return"))
        {
            return new Token(TokenType.RETURN_TOKEN);
        }
        else if(dataString.equals("void"))
        {
            return new Token(TokenType.VOID_TOKEN);
        }
        else if(dataString.equals("while"))
        {
            return new Token(TokenType.WHILE_TOKEN);
        }
        return new Token(TokenType.ID_TOKEN, dataString);
    }
}
