import java.util.ArrayList;

public class SyntaxAnalyzer {

    private ArrayList<ArrayList<Token>> TokenCollection;
    private int tokenIndex = 0;
    private int lineNumber = 1;

    public SyntaxAnalyzer(ArrayList<ArrayList<Token>> TokenCollection) {
        this.TokenCollection = TokenCollection;
        program();
    }

    private Token getTokenType() {
        if (lineNumber - 1 < TokenCollection.size() && tokenIndex < TokenCollection.get(lineNumber - 1).size()) {
            return TokenCollection.get(lineNumber - 1).get(tokenIndex);
        }
        return null;
    }

    private void nextToken() {
        tokenIndex++;
        if (tokenIndex >= TokenCollection.get(lineNumber - 1).size()-1) {//if exceeded tokens, nextline
            nextLine();
        }
    }

    private void nextLine() {
        tokenIndex = 0;
        lineNumber++;
    }

    //check if currToken is expected Token
    private boolean expect(String expectedType) {
        Token tokenType = getTokenType();
        if (tokenType != null && tokenType.getToken().equals(expectedType)) {
            nextToken();
            return true;
        } else {
            errorMessage();
            //nextLine();
            return false;
        }
    }

    private void errorMessage() {
        System.out.println("Line " + lineNumber + ": Error");
    }

    private void program() {
        while (lineNumber - 1 < TokenCollection.size()) {
            stmts();
            nextLine();
        }
    }

    private void stmts() {
        Token tokenType = getTokenType();
        if (tokenType == null) return;
        
        switch (tokenType.getToken()) {
            case "IF_STMT":
                if_stmt();
                break;
            case "ELSE_STMT":
                else_stmt();
                break;
            case "DIV_OP":
                comment_stmt();
                break;
            case "INT_IDENT":
            case "DOUBLE_IDENT":
                declare_stmt();
                break;
            case "IDENT":
                nextToken();
                assign_stmt();
                break;    
            default:
                //errorMessage();
                //nextLine();
                break;
        }
    }


    private void if_stmt() {
        nextToken();
        if (!expect("LEFT_PAREN")) return;
        if (!bool()) return;
        if (!expect("RIGHT_PAREN")) return;
    }

    private void else_stmt() {
        nextToken();
        stmts(); 
    }

    private void comment_stmt() {
        nextToken();
        Token currToken = getTokenType();
        if (currToken != null && currToken.getToken().equals("DIV_OP")) {
            //
        } else {
            errorMessage();
        }
    }

    //used to declare a variable
    private void declare_stmt() {
        nextToken();
        if (!expect("IDENT")) return;
        Token tokenType = getTokenType();
        if (tokenType != null && 
            tokenType.getToken().equals("ASSIGN_OP")) {//if assignment, check assign()
            assign_stmt();
        } else if (tokenType != null && tokenType.getToken().equals("COMMA")) { //multi line delcaration
            nextToken();
            if (!expect("IDENT")) return;
        }
        expect("SEMI_COLON"); //ends each line with semi colon
    }

    private void assign_stmt() {
        if (!expect("ASSIGN_OP")) return;
        Token tokenType = getTokenType();
        if (tokenType != null && tokenType.getToken().equals("ASSIGN_OP")) {
            errorMessage();
            return;
        }
        expr();
    }

    private void expr() {
        Token tokenType = getTokenType();
        if (tokenType == null) return;

        if (tokenType.getToken().equals("LEFT_PAREN")) {
            expect("LEFT_PAREN"); 
            expr(); //recurisve call
            expect("RIGHT_PAREN"); //ensures right paren is available
        } else if (tokenType.getToken().equals("INT_LIT") ||
                   tokenType.getToken().equals("DOUBLE_LIT") ||
                   tokenType.getToken().equals("IDENT")) {
            nextToken();
            tokenType = getTokenType();
            if (tokenType != null && (tokenType.getToken().equals("ADD_OP") ||
                                      tokenType.getToken().equals("SUB_OP") ||
                                      tokenType.getToken().equals("DIV_OP") ||
                                      tokenType.getToken().equals("MULT_OP"))) {
                nextToken();
                expr();//recursive call
            }
        }
    }

    private boolean bool() {
        Token currToken = getTokenType();
        if (currToken == null) return false;
        
        if (currToken.getToken().equals("TRUE") || currToken.getToken().equals("FALSE")) {
            nextToken();
            return true;
        } else if (arithmitic_value()) { //if arithimitic_value, check for op, and next arithmitic_value
            if (!check_op()) return false;
            if (arithmitic_value()) return true;
        }
        return false;
    }
    //check if currToken is arithimitc
    private boolean arithmitic_value() {
        Token currToken = getTokenType();
        if (currToken != null &&
            (currToken.getToken().equals("IDENT") ||
             currToken.getToken().equals("INT_LIT") ||
             currToken.getToken().equals("DOUBLE_LIT"))) {
            nextToken();
            return true;
        }
        return false;
    }
    
    private boolean check_op() {
        Token currToken = getTokenType();
        if (currToken != null &&
            (currToken.getToken().equals("EQUAL_OP") ||
             currToken.getToken().equals("GREATER_OP") ||
             currToken.getToken().equals("LESS_OP") ||
             currToken.getToken().equals("GE_OP") ||
             currToken.getToken().equals("LE_OP"))) {
            nextToken();
            return true;
        }
        return false;
    }
}
