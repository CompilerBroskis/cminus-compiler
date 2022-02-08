package compiler.Scanner;

public class Token 
{
    static public enum TokenType
    {
        ID_TOKEN,
        INT_TOKEN,
        DOUBLE_TOKEN,
        IF_TOKEN,
        LT_TOKEN,
        GT_TOKEN,
        PLUS_TOKEN,
        MINUS_TOKEN,
        MUL_TOKEN,
        DIV_TOKEN,
        SC_TOKEN,
        COMMA_TOKEN,
        LP_TOKEN,
        RP_TOKEN,
        LB_TOKEN,
        RB_TOKEN,
        LCB_TOKEN,
        RCB_TOKEN,
        ASSIGN_TOKEN,
        NOTEQ_TOKEN, //!=
        LTEQ_TOKEN, //<=
        GTEQ_TOKEN, //>=
        EQEQ_TOKEN, //==
        EOF_TOKEN,
        ERROR_TOKEN
    }

    private TokenType tokenType;
    private Object tokenData;

    public Token(TokenType tokenType)
    {
        this.tokenType = tokenType;
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
