import java.util.HashMap;
import java.io.File;


public class LexicalAnalyzer {
    
    /* Token Objects */
    Token tokens[] = {
        new Token("SEMI_COLON"),
        new Token("INT_LIT"),
        new Token("DOUBLE_LIT"),
        new Token("STRING_LIT"),
        new Token("CHAR_LIT"),
        new Token("INDET"),
        new Token("ASSIGN_OP"),
        new Token("ADD_OP"),
        new Token("SUB_OP"),
        new Token("MULT_OP"),
        new Token("DIV_OP"),
        new Token("LEFT_PAREN"),
        new Token("RIGHT_PAREN"),
        new Token("LEFT_BRACKET"),
        new Token("RIGHT_BRACKET"),
        new Token("IF_STMT"),
        new Token("ELSE_STMT")

    };



    /* HashMap to store the relation between each lexeme with their respective Token 
     * O(1) ~ Time Complexity
    */
    private HashMap<String,Token> lexemsToTokens = new HashMap<String,Token>();
    


    public LexicalAnalyzer(File file){

    }

    private void getChar(){
        
    }







    


}
