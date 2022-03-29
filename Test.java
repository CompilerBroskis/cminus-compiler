import compiler.Parser.CMinusParser;
import compiler.Scanner.CMinusScanner;

public class Test {
    
    public static void main(String[] args){
        try
        {
            CMinusScanner scanner = new CMinusScanner("./euclid.txt");
            CMinusParser parser = new CMinusParser("./test.txt");
            // while(scanner.viewNextToken().getTokenType() != TokenType.EOF_TOKEN){
            //     Token nextToken = scanner.getNextToken();
            //     System.out.println(nextToken.getTokenType());
            //     outputFile.write(nextToken.getTokenType().toString() + "\n");
            // }
            // Token nextToken = scanner.getNextToken();
            // System.out.println(nextToken.getTokenType());
            // outputFile.write(nextToken.getTokenType().toString() + "\n");
            // outputFile.close();

            parser.parse();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
