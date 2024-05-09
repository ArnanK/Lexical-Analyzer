import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File(
            "../ParseCodeSP24.txt"
        );
        
        LexicalAnalyzer lexer = new LexicalAnalyzer(file);
        
    }

    
}
