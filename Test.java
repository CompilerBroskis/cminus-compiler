import java.io.File;
import java.io.FileWriter;

import compiler.Scanner.CMinusScanner;
import compiler.Scanner.JFlexScanner;
import compiler.Scanner.Token;
import compiler.Scanner.Token.TokenType;

public class Test {
    
    public static void main(String[] args){
        try
        {
            CMinusScanner scanner = new CMinusScanner("./euclid.txt");
            while(scanner.viewNextToken().getTokenType() != TokenType.EOF_TOKEN){
                Token nextToken = scanner.getNextToken();
                System.out.println(nextToken.getTokenType());
                outputFile.write(nextToken.getTokenType().toString() + "\n");
            }
            Token nextToken = scanner.getNextToken();
            System.out.println(nextToken.getTokenType());
            outputFile.write(nextToken.getTokenType().toString() + "\n");
            outputFile.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
