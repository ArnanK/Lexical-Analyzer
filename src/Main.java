import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File(
            "ParseCodeSP24.txt"
        );
        

        LexicalAnalyzer lexer = new LexicalAnalyzer(file);
        
    }

    
}
