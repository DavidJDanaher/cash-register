package main.java.excpetions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String value) {
        super(String.format("There are insufficient %s dollar bills.", value));
    }
}
