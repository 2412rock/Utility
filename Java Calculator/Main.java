import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.PrintStream;

public class Main implements CalculatorInterface {

    public static final String PLUS_TOKEN = "+";
    public static final String MINUS_TOKEN = "-";
    public static final String DIVIDE_TOKEN = "/";
    public static final String MULT_TOKEN = "*";
    public static final String POWER_TOKEN = "^";

    public static final String PAR_LEFT_TOKEN = "(";
    public static final String PAR_RIGHT_TOKEN = ")";
    public static final String NULL = "0";

    public static final int LOW_PRECEDENCE = 1;
    public static final int MEDIUM_PRECEDENCE = 2;
    public static final int HIGH_PRECEDENCE = 3;
    public static final int NO_PRECEDENCE = -1;

    private static PrintStream out;
    private int numberOfDoubles,numberOfOperators;


    public TokenList readTokens(String input) {
        numberOfDoubles = 0;
        Scanner in = new Scanner(input);
        TokenList result = new WordList();

        while (in.hasNext()) {

            String token = in.next();

                //If the token is a real number
            if (tokenIsDouble(token)) {
                numberOfDoubles++;
                result.add(parseNumber(token));

                //If the token is an operator
            } else if (tokenIsOperator(token)) {
                numberOfOperators++;
                result.add(parseOperator(token));

                //If the token is a parenthesis
            } else if (tokenIsParenthesis(token)) {
                result.add(parseParenthesis(token));

                //If the token is an invalid character the calculation is aborted and a new expression is expected from the user
            } else {
                out.println("Invalid input. Insert new expression.");
                start();
            }
        }
        return result;
    }

    public boolean tokenIsDouble(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    //Creates a new instance of a token number
    private Token parseNumber(String token) {
        Token parsedNumber = new Word(token, -1, Token.NUMBER_TYPE);
        return parsedNumber;

    }

    private boolean tokenIsOperator(String token) {
        if (token.equals(PLUS_TOKEN)) {
            return true;
        } else if (token.equals(MINUS_TOKEN)) {
            return true;
        } else if (token.equals(MULT_TOKEN)) {
            return true;
        } else if (token.equals(DIVIDE_TOKEN)) {
            return true;
        } else if (token.equals(POWER_TOKEN)) {
            return true;
        } else {
            return false;
        }
    }



    //Checks whether token is a parenthesis
    private boolean tokenIsParenthesis(String token) {
        return token.equals(PAR_LEFT_TOKEN) || token.equals(PAR_RIGHT_TOKEN);
    }



    //Calculates the precedence of a character and creates a new instance of an operator token
    private Token parseOperator(String token) {

        int precedence = NO_PRECEDENCE;


        //Addition/Subtraction operators: precedence = 1
        if (token.equals(PLUS_TOKEN) || token.equals(MINUS_TOKEN)) {
            precedence = LOW_PRECEDENCE;
        }

        //Multiplication/Division operators: precedence = 2
        else if (token.equals(MULT_TOKEN) || token.equals(DIVIDE_TOKEN)) {
            precedence = MEDIUM_PRECEDENCE;
        }

        //Power: precedence = 3
        else if (token.equals(POWER_TOKEN)) {
            precedence = HIGH_PRECEDENCE;
        }
        return new Word(token, precedence, Token.OPERATOR_TYPE);
    }



    //Creates a new instance of a token parenthesis
    private Token parseParenthesis(String token) {
        return new Word(token, NO_PRECEDENCE, Token.PARENTHESIS_TYPE);
    }



    //Two operands are popped off the stack, the operator is evaluated and the operation is carried out
    //The result is pushed back onto the stack
    private void performOperation(Token operator, DoubleStack stack) {
        try {
            if (stack.size() > 0) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();

                //Addition
                if (operator.getValue().equals(PLUS_TOKEN)) {
                    stack.append(firstOperand + secondOperand);

                    //Subtraction
                } else if (operator.getValue().equals(MINUS_TOKEN)) {
                    stack.append(firstOperand - secondOperand);
                }

                //Multiplication
                else if (operator.getValue().equals(DIVIDE_TOKEN)) {
                    stack.append(firstOperand / secondOperand);
                }

                //Division
                else if (operator.getValue().equals(MULT_TOKEN)) {
                    stack.append(firstOperand * secondOperand);
                }

                //Power
                else if (operator.getValue().equals(POWER_TOKEN)) {
                    stack.append(Math.pow(firstOperand, secondOperand));
                }
            }
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Not enough doubles on stack.");
        }
    }


    //Takes as input a list of tokens in RPN notation (Reverse Polish Notation and returns the result as a double.
    public Double rpn(TokenList tokens) {

        DoubleStack operStack = new Stack();
        for (int i = 0; i < tokens.size(); i++) {
            //Token is converted to String and printed
            String currentToken = tokens.get(i).getValue();

            //If the token is a number then push it onto the stack
            if (tokenIsDouble(currentToken)) {
                operStack.push(Double.parseDouble(currentToken));

            /*Pop the top 2 numbers from the stack,
              evaluate the operator with the two numbers as arguments and
              push the result of the operation back to the stack;*/
            } else if (tokenIsOperator(currentToken)) {
                //verifica daca sunt numere pe stack
                performOperation(tokens.get(i), operStack);
            }
        }
        //The last value on top of the stack is the result of the RPN expression
        if (operStack.size() == 1) {
            return operStack.top();
        } else {
            out.printf("Error: Invalid input, remaining tokens on stack\nThe number of remaining doubles on the stack is:\n");
            return (double) operStack.size();
        }
    }



    //The Shunting Yard algorithm takes an expression in infix notation and converts into postfix (RPN) notation.
    public TokenList shuntingYard(TokenList tokens) {

        TokenList outputList = new WordList() ;     //Final output in RPN format
        TokenStack operatorStack = new TStack();    //Auxiliary operator stack
        int counter = 0;

        try {
            while (counter < tokens.size()) {

                Token currentToken = tokens.get(counter);

                //If the token is double
                if(tokenIsDouble(currentToken.getValue())){
                    outputList.add(currentToken);

                /*If the token (o1) is an operator then
                  while there is an operator (o2) on top of the stack with a higher or
                  equal precedence, pop o2 from the stack and add it to the output list;
                  Then push o1 onto the stack;*/
                } else if(tokenIsOperator(currentToken.getValue())){     //o1
                    while(operatorStack.top().getPrecedence() >= currentToken.getPrecedence()){
                        outputList.add(operatorStack.pop());
                    }
                    operatorStack.push(currentToken);
                }
                //if the token is a left parenthesis then push it onto the stack
                if(currentToken.getValue().equals(PAR_LEFT_TOKEN)) {
                    operatorStack.push(currentToken);
                }


                    /*the token is a right parenthesis then
                      while the top of the stack is not a left parenthesis,
                      pop operator off the stack and add it to the output list.
                      Then pop the left parenthesis from the stack;*/
                if(currentToken.getValue().equals(PAR_RIGHT_TOKEN)) {
                    while(!operatorStack.top().getValue().equals(PAR_LEFT_TOKEN) && !operatorStack.top().getValue().equals(NULL)){

                        outputList.add(operatorStack.pop());
                    }
                    operatorStack.pop();
                }
                counter++;
            }
        }

        catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            while (!operatorStack.top().getValue().equals(NULL)) {
                outputList.add(operatorStack.pop());
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return outputList;
    }




    public void start() {

        //The calculator runs indefinitely; that is, a new mathematical expression is parsed automatically once it's inserted
        Scanner input = new Scanner(System.in);

        while(input.hasNext()) {

            out = new PrintStream(System.out);

            System.out.println(rpn(shuntingYard(readTokens(input.nextLine()))));
        }
    }



    public static void main(String[] argv) {
        new Main().start();
    }
}