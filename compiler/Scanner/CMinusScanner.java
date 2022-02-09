package compiler.Scanner;

import java.io.BufferedReader;
import java.io.IOException;

import compiler.Scanner.Token.TokenType;

public class CMinusScanner implements Scanner
{
    private BufferedReader inFile;
    private Token nextToken;

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
        char skippedCharValue = '?';
        

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
                            state = StateType.IN_ASSIGN;
                            // char nextC = (char) inFile.read();
                            // if(nextC == '='){
                            //     currentToken = new Token(TokenType.EQEQ_TOKEN);
                            // }
                            // else {
                            //      currentToken = new Token(TokenType.ASSIGN_TOKEN);
                            //      //Mark skip and pass the value for the next loop
                            //      skippedChar = true;
                            //      skippedCharValue = nextC;
                            // }
                            // state = StateType.DONE;
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
                        else if ((c == ' ') || (c == '\t') || (c == '\n'))
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
                        if(c == '='){
                            currentToken = new Token(TokenType.LTEQ_TOKEN);
                        }
                        else {
                            currentToken = new Token(TokenType.LT_TOKEN);
                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        state = StateType.DONE;
                        break;
                    case IN_GT:
                        if(c == '='){
                            currentToken = new Token(TokenType.GTEQ_TOKEN);
                        }
                        else {
                            currentToken = new Token(TokenType.GT_TOKEN);
                            //Mark skip and pass the value for the next loop
                            skippedChar = true;
                            skippedCharValue = c;
                        }
                        state = StateType.DONE;
                        break;
                    case IN_NOT:
                        if(c == '='){
                            currentToken = new Token(TokenType.NOTEQ_TOKEN);
                        }
                        else {
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
                        if(c == '*'){                        
                            state = StateType.IN_COMMENT_END;                     
                        }
                        break;
                    case IN_COMMENT_END:
                        if(c == '/'){
                            state = StateType.START;
                        }
                        else
                        {
                            state = StateType.IN_COMMENT;
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
