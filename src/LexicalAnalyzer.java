import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.PushbackReader;
import java.io.IOException;


public class LexicalAnalyzer {
    /* Enumeration Types */
    enum charType{
        DIGIT,
        LETTER,
        WS,
        UNKNOWN,
        EOF, //end of file
    }

    /* Global Variables */
    
    private ArrayList<Character> lexeme = new ArrayList<Character>();
    private Token nextToken;
    private int charIndex;
    private charType charClass;
    
    public ArrayList<ArrayList<Token>> TokenCollection = new ArrayList<ArrayList<Token>>();
    public ArrayList<Token> allTokens = new ArrayList<Token>();

    private int line;

    /* Token Objects */
    private ArrayList<Token> tokens = new ArrayList<Token>();
    
    /* Reserved Words */
    private HashMap<String,Token> reservedWords = new HashMap<String,Token>();



    public LexicalAnalyzer(File file) throws IOException{
        fillTokens();
        fillReservedWords();
        initializeTokenCollection();

        FileReader stream = null;
        PushbackReader inputStream = null;
        try{
            stream = new FileReader(file);
            inputStream = new PushbackReader(stream);
            while((charIndex=inputStream.read()) != -1){
                checkNewLine();
                lex(inputStream);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(inputStream != null){
                inputStream.close();
            }
        }

        printTokenCollection();
    }

    private void initializeTokenCollection(){
        line = 1;
        TokenCollection.add(new ArrayList<Token>());
    }

    private void checkNewLine(){
        if((char)charIndex == '\n'){
            line++;
            TokenCollection.add(new ArrayList<Token>());
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
        tokens.add(new Token("FOR_STMT"));//18
        tokens.add(new Token("WHILE_STMT"));//19
        tokens.add(new Token("INT_IDENT"));//20
        tokens.add(new Token("DOUBLE_IDENT"));//21
        tokens.add(new Token("PERIOD"));//22
        tokens.add(new Token("GREATER_BOOL"));//23
        tokens.add(new Token("LESS_BOOL"));//24
        tokens.add(new Token("TRUE"));//25
        tokens.add(new Token("FALSE"));//26
        tokens.add(new Token("GREATER_EQ_BOOL")); //27
        tokens.add(new Token("LESS_EQ_BOOL")); //28
        tokens.add(new Token("EQUAL_BOOL"));//29

    }

    private void fillReservedWords(){
        reservedWords.put("if", tokens.get(16));
        reservedWords.put("else", tokens.get(17));
        reservedWords.put("for", tokens.get(18));
        reservedWords.put("while", tokens.get(19));
        reservedWords.put("int", tokens.get(20));
        reservedWords.put("double", tokens.get(21));
        reservedWords.put("true", tokens.get(25));
        reservedWords.put("false", tokens.get(26));
    }


    private void addChar(char ch){
        if(lexeme.size() <= 98){
            if(String.valueOf(ch) != ""){
                lexeme.add(ch);
            }
        }else{
            System.out.println("Error - lexeme is too long");
        }
    }

    private int getChar(PushbackReader inputStream) throws IOException{
        return inputStream.read();
    }

    private charType getCharType(char ch){
        if(Character.isAlphabetic(ch)){
            return charType.LETTER;
        }else if(Character.isDigit(ch)){
            return charType.DIGIT;
        }else if(Character.isWhitespace(ch)){
            return charType.WS;
        }else{
            return charType.UNKNOWN;
        }
    }

    private void lookup(char ch, PushbackReader inputStream)throws IOException{
        switch (ch) {
            case '(':
                addChar(ch);
                this.nextToken = tokens.get(11);
                break;
            case ')':
                addChar(ch);
                this.nextToken = tokens.get(12);
                break;
            case '{':
                addChar(ch);
                this.nextToken = tokens.get(13);
                break;
            case '}':
                addChar(ch);
                this.nextToken = tokens.get(14);
                break;
            case ',':
                addChar(ch);
                this.nextToken = tokens.get(15);
                break;
            case '=':
                addChar(ch);
                charIndex = getChar(inputStream);
                if ((char) charIndex == '=') {
                    addChar((char) charIndex);
                    this.nextToken = tokens.get(29);
                } else {
                    inputStream.unread(charIndex);
                    this.nextToken = tokens.get(6);
                }
                break;
            case '+':
                addChar(ch);
                charIndex = getChar(inputStream);
                if ((char) charIndex == '=') {
                    addChar((char) charIndex);
                    this.nextToken = tokens.get(6);
                } else {
                    inputStream.unread(charIndex);
                    this.nextToken = tokens.get(7);
                }
                
                break;
            case '-':
                addChar(ch);
                this.nextToken = tokens.get(8);
                break;
            case '*':
                addChar(ch);
                this.nextToken = tokens.get(9);
                break;
            case '/':
                addChar(ch);
                this.nextToken = tokens.get(10);
                break;
            case ';':
                addChar(ch);
                this.nextToken = tokens.get(0);
                break;
            case '>':
                addChar(ch);
                charIndex = getChar(inputStream);
                if ((char) charIndex == '=') {
                    addChar((char) charIndex);
                    this.nextToken = tokens.get(27);
                } else {
                    inputStream.unread(charIndex);
                    this.nextToken = tokens.get(23);
                }
            case '<':
                addChar(ch);
                charIndex = getChar(inputStream);
                if ((char) charIndex == '=') {
                    addChar((char) charIndex);
                    this.nextToken = tokens.get(28);
                } else {
                    inputStream.unread(charIndex);
                    this.nextToken = tokens.get(24);
                }
                break;
            default:
                break;
        }
    }


    
    private void lex(PushbackReader inputStream) throws IOException{
        charClass = getCharType((char)charIndex);
        switch(charClass){
            /* Parse Identifiers */
            case LETTER:
                addChar((char) charIndex);
                charIndex = getChar(inputStream);
                charClass = getCharType((char)charIndex);

                while(charClass == charType.LETTER || charClass == charType.DIGIT){    
                    addChar((char) charIndex);
                    charIndex = getChar(inputStream);
                    charClass = getCharType((char)charIndex);
                    
                }
                inputStream.unread(charIndex);//revert changes which are not applicable
                String lexemeString = covnertString(lexeme);

                //checks if current lexeme is a reservedWord
                if(reservedWords.containsKey(lexemeString)){
                    nextToken = reservedWords.get(lexemeString);
                }else{
                    nextToken = tokens.get(1);
                }
                break;
                
            case DIGIT:
                int decimalCounter = 0;
                addChar((char) charIndex);
                charIndex = getChar(inputStream);
                charClass = getCharType((char)charIndex);

                //if next char is a digit or if next char is '.' and there is not more than 1 decimals already.
                while(charClass == charType.DIGIT || ((char)charIndex == '.' && decimalCounter++ <=1)){
                    addChar((char) charIndex);
                    charIndex = getChar(inputStream);
                    charClass = getCharType((char)charIndex);
                }
                inputStream.unread(charIndex);
                //if there is a decimal, store the token as DOUBLE, else store as INT
                if(decimalCounter > 0) nextToken = tokens.get(3);
                else nextToken = tokens.get(2);
                
                break;
            
            case UNKNOWN:
                lookup((char) charIndex, inputStream);
                break;
            default:
                break;
            
        }
        if(covnertString(lexeme) != ""){
            TokenCollection.get(line-1).add(nextToken);
            allTokens.add(nextToken);
            System.out.println("Lexeme: "+covnertString(lexeme)+"\tToken: "+this.nextToken.getToken());
        }
        lexeme.clear();

    }


    private void printTokenCollection(){
        System.out.println("--------------------------------------------------------------------------------");
        TokenCollection.forEach(line->{
            System.out.print("Line "+(TokenCollection.indexOf(line)+1) + ": ");
            line.forEach(token->{
                System.out.print(token.getToken()+" ");
            });
            System.out.println("");
        });
        System.out.println("--------------------------------------------------------------------------------");
    }

    
    private String covnertString(ArrayList<Character> lexeme){
        StringBuilder builder = new StringBuilder(lexeme.size());
        for(Character ch:lexeme){
            builder.append(ch);
        }
        return builder.toString();
    }


 

}
