package Exceptions;
public class NonNumericInputException extends Exception {
    public NonNumericInputException(String message) {
        super(message);
    }
    public boolean Input(String input) throws InvalidInputException {
        if (input.matches(".[a-zA-Z].")) {
            throw new InvalidInputException("Please enter a valid  input only numbers");
        }
        return true;
    }
}