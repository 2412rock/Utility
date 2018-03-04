public class Word implements Token {

    private String value;
    private int type;
    private int precedence;


    Word(String value, int precedence, int type) {
        this.value = value;
        this.precedence = precedence;
        this.type = type;
    }



    @Override
    //Returns the value of the token
    public String getValue() {
        return this.value;
    }



    @Override
    //Returns the type of the token (Number:1, Operator:2, Parenthesis:3)
    public int getType() {
        return this.type;
    }



    @Override
    //Returns the precedence of the token (+ or -: 1,   * or /: 2,   ^:3)
    public int getPrecedence() {
        return this.precedence;
    }
}