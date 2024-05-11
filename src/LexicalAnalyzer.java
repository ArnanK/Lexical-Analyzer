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
    String nextLexeme;
    Token nextToken;
    int lexLen;
    
    /* Token Objects */
    private ArrayList<Token> tokens = new ArrayList<Token>();
    
    /* Reserved Words */
    private HashMap<String,Token> reservedWords = new HashMap<String,Token>();


    /* HashMap to store the relation between each lexeme with their respective Token 
     * O(1) ~ Time Complexity
    */
    private HashMap<String,Token> lexemsToTokens = new HashMap<String,Token>();



    public LexicalAnalyzer(File file) throws IOException{
        fillTokens();
        fillReservedWords();

        FileReader inputStream = null;
        int charIndex;
        try{
            inputStream = new FileReader(file);
            while((charIndex=inputStream.read()) != -1){
                lex(inputStream, charIndex);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(inputStream != null){
                inputStream.close();
            }
        }

        //printLexemesTokens();
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
                addChar(ch);
                nextToken = tokens.get(11);
                break;
            case ')':
                lexemsToTokens.put(")", tokens.get(12));
                addChar(ch);
                nextToken = tokens.get(12);
                break;
            case '{':
                lexemsToTokens.put("{", tokens.get(13));
                addChar(ch);
                nextToken = tokens.get(13);
                break;
            case '}':
                lexemsToTokens.put("}", tokens.get(14));
                addChar(ch);
                nextToken = tokens.get(14);
                break;
            case '+':
                lexemsToTokens.put("+", tokens.get(7));
                addChar(ch);
                nextToken = tokens.get(7);
                break;
            case '-':
                lexemsToTokens.put("-", tokens.get(8));
                addChar(ch);
                nextToken = tokens.get(8);
                break;
            case '*':
                lexemsToTokens.put("*", tokens.get(9));
                addChar(ch);
                nextToken = tokens.get(9);
                break;
            case '/':
                lexemsToTokens.put("/", tokens.get(10));
                addChar(ch);
                nextToken = tokens.get(10);
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
        tokens.add(new Token("IF_STMT"));//16
        tokens.add(new Token("ELSE_STMT"));//17
    }

    private void fillReservedWords(){
        reservedWords.put("if", tokens.get(16));
        reservedWords.put("else", tokens.get(17));
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
    
    private void ignoreComments(){

    }

    private void lex(FileReader inputStream, int charIndex) throws IOException{
        charClass = getCharType((char)charIndex);

        switch(charClass){
            /* Parse Identifiers */
            case LETTER:
                addChar((char) charIndex);
                charIndex = inputStream.read(); //test next line
                charClass = getCharType((char)charIndex);
                while(charClass == charType.LETTER || charClass == charType.DIGIT){    
                    addChar((char) charIndex);
                    charIndex = inputStream.read();
                    charClass = getCharType((char)charIndex);

                }
                //System.out.println("Lexeme: "+lexeme.toString()+"\tToken: "+tokens.get(1).getToken());
                //lexemsToTokens.put(lexeme.toString(), tokens.get(1));
                String lexemeString = covnertString(lexeme);
                if(reservedWords.containsKey(lexemeString)){
                    nextToken = reservedWords.get(lexemeString);
                }else{
                    nextToken = tokens.get(1);

                }
                
                


                //lexeme.clear();
                break;
            
            case DIGIT:
                addChar((char) charIndex);
                charIndex = inputStream.read();
                charClass = getCharType((char)charIndex);
                while(charClass == charType.DIGIT){
                    addChar((char) charIndex);
                    charIndex = inputStream.read();
                    charClass = getCharType((char)charIndex);

                }
                //lexemsToTokens.put(lexeme.toString(), tokens.get(2));
                nextToken = tokens.get(2);

               // System.out.println("Lexeme: "+lexeme.toString()+"\tToken: "+tokens.get(2).getToken());

                //lexeme.clear();
                break;
            
            case UNKNOWN:
                lookup((char) charIndex);
                break;
            default:
                break;
            
        }
        
        System.out.println("Lexeme: "+lexeme.toString()+"\tToken: "+nextToken.getToken());
        lexeme.clear();
    }

    public void printLexemesTokens(){
        lexemsToTokens.forEach((key, value) -> {
            System.out.println("Lexeme: "+key+"\tToken: "+value.getToken());
        });
    }


    private String covnertString(ArrayList<Character> lexeme){
        StringBuilder builder = new StringBuilder(lexeme.size());
        for(Character ch:lexeme){
            builder.append(ch);
        }
        return builder.toString();
    }


    


}
