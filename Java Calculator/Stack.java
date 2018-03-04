public class Stack implements DoubleStack {


    private Double[] doubleStack;
    private int numberOfElements;


    Stack() {
        numberOfElements = 0;
        doubleStack = new Double[numberOfElements];
    }


    @Override
    //Pushes a new double onto the doublestack
    public void push(Double element) {
        numberOfElements++;

        if (numberOfElements > doubleStack.length){
            doubleArraySize();
        }
        doubleStack[numberOfElements-1] = element;
    }



    @Override
    //Pops the top double on the doublestack and returns it
    public Double pop() {

        Double topOfStack = null;

        if (doubleStack.length !=0) {
            topOfStack = doubleStack[numberOfElements-1];
            doubleStack[numberOfElements-1] = null;
            numberOfElements--;
        }
        else {
            System.out.println("Empty doubleStack -pop");
        }
        return topOfStack;
    }



    @Override
    //Returns the top double on the doublestack
    public Double top() {
        Double topOfStack = null;

        if (doubleStack.length !=0) {
            topOfStack = doubleStack[numberOfElements-1];
        }
        else {
            System.out.println("Empty doubleStack -peek");
        }
        return topOfStack;
    }



    @Override
    //Returns the size of the doublestack
    public int size() {
        return numberOfElements;
    }



    //Pushes the result of the two top operands onto the doublestack
    public void append(double result){
        numberOfElements++;

        if (numberOfElements > doubleStack.length){
            doubleArraySize();
        }
        doubleStack[numberOfElements-1] = result;
    }



    //Auxiliary method for pushing a double onto the doublestack
    //The stack's size doubles every time there is no space for a new double
    private void doubleArraySize() {
        Double[] tempArray = new Double[numberOfElements];
        System.arraycopy(doubleStack, 0, tempArray, 0, numberOfElements-1);
        if (doubleStack.length == 0 ) {
            doubleStack= new Double[numberOfElements];
        }
        else {
            doubleStack= new Double[numberOfElements*2];
        }
        doubleStack = tempArray;
    }
}
