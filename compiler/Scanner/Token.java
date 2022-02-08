package compiler.Scanner;

public class Token 
{
    static public enum TokenType
    {
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
        EOF_TOKEN
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
