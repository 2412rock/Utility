public class TStack implements TokenStack {

    private Token[] tokenStack;
    private int numberOfElements;


    TStack(){
        Token nullToken = new Word("0", -1, 0);
        numberOfElements = 1;
        tokenStack = new Token[numberOfElements];
        tokenStack[0] = nullToken;
    }



    @Override
    //Pushes a new token onto the tokenstack
    public void push(Token token) {
        numberOfElements++;

        if (numberOfElements > tokenStack.length){
            doubleArraySize();
        }
        tokenStack[numberOfElements-1] = token;
    }



    @Override
    //Pops the top token on the stack and returns it
    public Token pop() {
        Token topOfStack = null;

        if (tokenStack.length !=0) {
            topOfStack = tokenStack[numberOfElements-1];
            tokenStack[numberOfElements-1] = null;
            numberOfElements--;
        }
        else {
            System.out.println("Empty doubleStack -pop");
        }
        return topOfStack;
    }



    @Override
    //Returns the top token on the stack
    public Token top() {
        Token topOfStack = null;

        if (tokenStack.length !=0) {
            topOfStack = tokenStack[numberOfElements-1];
        }
        else {
            System.out.println("Empty doubleStack -peek");
        }
        return topOfStack;
    }



    @Override
    //Returns the size of the stack
    public int size() {
        return numberOfElements;
    }



    //Auxiliary method for pushing a token onto the stack
    //The stack's size doubles every time there is no space for a new token
    private void doubleArraySize() {
        Token[] tempArray = new Word[numberOfElements];
        System.arraycopy(tokenStack, 0, tempArray, 0, numberOfElements-1);
        if (tokenStack.length == 0 ) {
            tokenStack= new Word[numberOfElements];
        }
        else {
            tokenStack= new Word[numberOfElements*2];
        }
        tokenStack = tempArray;
    }
}
