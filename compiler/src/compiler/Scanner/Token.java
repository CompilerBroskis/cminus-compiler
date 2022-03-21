package compiler.Scanner;

public class Token 
{
    static public enum TokenType
    {
        ID_TOKEN, // identifier
        INT_TOKEN, // int
        DOUBLE_TOKEN, // double
        IF_TOKEN, // if
        ELSE_TOKEN, // else
        RETURN_TOKEN, // return
        VOID_TOKEN, // void
        WHILE_TOKEN, // while
        LT_TOKEN, // <
        GT_TOKEN, // >
        PLUS_TOKEN, // +
        MINUS_TOKEN, // -
        MUL_TOKEN, // *
        DIV_TOKEN, // /
        SC_TOKEN, // ;
        COMMA_TOKEN, // ,
        LP_TOKEN, // (
        RP_TOKEN, // )
        LB_TOKEN, // [
        RB_TOKEN, // ]
        LCB_TOKEN, // {
        RCB_TOKEN, // }
        ASSIGN_TOKEN, // =
        NOTEQ_TOKEN, // !=
        LTEQ_TOKEN, // <=
        GTEQ_TOKEN, // >=
        EQEQ_TOKEN, // ==
        EOF_TOKEN,
        ERROR_TOKEN
    }

    private TokenType tokenType;
    private Object tokenData;

    public Token(TokenType tokenType)
    {
        this(tokenType, null);
    }

    public Token(TokenType tokenType, Object tokenData)
    {
        this.tokenType = tokenType;
        this.tokenData = tokenData;
    }

    // GETTERS
    public TokenType getTokenType()
    {
        return tokenType;
    }

    public Object tokenData()
    {
        return tokenData;
    }
}
