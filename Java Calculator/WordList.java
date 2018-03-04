public class WordList implements TokenList {

    private Token[] tokensList;
    private int numberOfElements,numberOfDoubles;


    WordList(){
        numberOfElements = 0;
        tokensList = new Word[numberOfElements];
    }



    //Pushes a new token onto the list
    public void add(Token token) {

        numberOfElements++;

        if (numberOfElements > tokensList.length){
            doubleArraySize();
        }
        tokensList[numberOfElements-1] = token;
    }



    //Removes a token from the list given its index
    public void remove(int index){
        //If the last element needs to be removed
        if (index == numberOfElements-1) {
            tokensList[index] = null;
            numberOfElements--;
        }
        //If one of the rest elements need to be removed
        else {
            for (int i=index; i<numberOfElements-1; i++) {
                tokensList[i] = tokensList[i + 1];
            }
        }
        tokensList[numberOfElements-1] = null;
        numberOfElements--;
    }



    //Alters the value of a token given its index
    public void set(int index, Token token){
        tokensList[index] = token;
    }



    //Gets the value of the token given its index
    public Token get(int index){
        return tokensList[index];
    }



    //Returns the size of the tokenlist
    public int size(){
        return tokensList.length;
    }



    //Auxiliary method for pushing a token onto the tokenlist
    //The list's size doubles every time there is no space for a new token
    private void doubleArraySize() {
        Token[] tempArray = new Word[numberOfElements];
        System.arraycopy(tokensList, 0, tempArray, 0, numberOfElements-1);
        if (tokensList.length == 0 ) {
            tokensList = new Word[numberOfElements];
        }
        else {
            tokensList = new Word[numberOfElements*2];
        }
        tokensList = tempArray;
    }
}