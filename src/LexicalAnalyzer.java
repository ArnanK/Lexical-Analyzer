import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class LexicalAnalyzer {
    /* Enumeration Types */
    enum charType{
        DIGIT,
        LETTER,
        UNKNOWN,
        EOF, //end of file
    }

    /* Global Variables */
    charType charClass;
    ArrayList<Character> lexeme = new ArrayList<Character>();
    char nextChar;
    int lexLen;
    
    /* Token Objects */
    private ArrayList<Token> tokens = new ArrayList<Token>();
    
    /* HashMap to store the relation between each lexeme with their respective Token 
     * O(1) ~ Time Complexity
    */
    private HashMap<String,Token> lexemsToTokens = new HashMap<String,Token>();
    


    public LexicalAnalyzer(File file) throws IOException{
        fillTokens();
        lexer(file);
    }

    private void addChar(char ch){
        if(lexeme.size() <= 98){
            lexeme.add(ch);
        }else{
            System.out.println("Error - lexeme is too long");
        }
    }

    private void getChar(){
        
    }



    private void lookup(char ch){
        switch (ch) {
            case '(':
                lexemsToTokens.put("(", tokens.get(11));
                break;
            case ')':
                lexemsToTokens.put(")", tokens.get(12));
                break;
            case '{':
                lexemsToTokens.put("{", tokens.get(13));
                break;
            case '}':
                lexemsToTokens.put("}", tokens.get(14));
                break;
            case '+':
                lexemsToTokens.put("+", tokens.get(7));
                break;
            case '-':
                lexemsToTokens.put("-", tokens.get(8));
                break;
            case '*':
                lexemsToTokens.put("*", tokens.get(9));
                break;
            case '/':
                lexemsToTokens.put("/", tokens.get(10));
                break;
            default:
                break;
        }
    }

    private void  fillTokens(){
        tokens.add(new Token("SEMI_COLON"));//0
        tokens.add(new Token("IDENT"));//1
        tokens.add(new Token("INT_LIT"));//2
        tokens.add(new Token("DOUBLE_LIT"));//3
        tokens.add(new Token("CHAR_LIT"));//4
        tokens.add(new Token("STRING_LIT"));//5
        tokens.add(new Token("ASSIGN_OP"));//6
        tokens.add(new Token("ADD_OP"));//7
        tokens.add(new Token("SUB_OP"));//8
        tokens.add(new Token("MULT_OP"));//9
        tokens.add(new Token("DIV_OP"));//10
        tokens.add(new Token("LEFT_PAREN"));//11
        tokens.add(new Token("RIGHT_PAREN"));//12
        tokens.add(new Token("LEFT_BRACKET"));//13
        tokens.add(new Token("RIGHT_BRACKET"));//14
        tokens.add(new Token("COMMA"));//15
    }

    private charType getCharType(char ch){
        if(Character.isAlphabetic(ch)){
            return charType.LETTER;
        }else if(Character.isDigit(ch)){
            return charType.DIGIT;
        }else{
            return charType.UNKNOWN;
        }
    }

    private void lexer(File file) throws IOException{
    
        FileReader inputStream = null;
        int charIndex;
        try {
            inputStream = new FileReader(file);
            while((charIndex = inputStream.read()) != -1){
                charType charClass = getCharType((char)charIndex);
                
                switch(charClass){
                    case LETTER:
                        addChar((char) charIndex);
                        charClass = getCharType((char)charIndex);
                        while(charClass == charType.LETTER || charClass == charType.DIGIT){
                            charIndex = inputStream.read();
                            addChar((char) charIndex);
                            charClass = getCharType((char)charIndex);
                        }
                        lexemsToTokens.put(lexeme.toString(), tokens.get(1));
                        lexeme.clear();
                        break;

                    default:
                        break;


                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(inputStream != null){
                inputStream.close();
            }
        }
        
        System.out.println(lexemsToTokens.toString());

    }






    


}
