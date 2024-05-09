import java.util.HashMap;

public class LexicalAnalyzer {
    private String[] reservedWords = {
        "if",
        "else",
        "for",
        "int",
        "double",
        "String",
        "char",
    };

    Token tokens[] = {
        new Token("SEMI_COLON"),
        new Token("INT_LIT"),
        new Token("DOUBLE_LIT"),
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
    
    private HashMap<String,Token> lexemsToTokens = new HashMap<String,Token>();
    


    public LexicalAnalyzer(){
    }





    


}
