package main.java.resources.exceptions;

public class InsufficientFundsException extends Exception {
    private String value;

    public InsufficientFundsException(String value) {
        super();

        this.value = value;
    }

    public String getMessage() {
        String message;

        if (this.value.equals("change")) {
            message = "This register cannot make that change.";
        } else {
            message = "There are insufficient funds to complete this transaction.";
        }

        return message;
    }
}
