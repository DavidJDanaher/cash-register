package main.java.excpetions;

public class InsufficientFundsException extends Exception {
    String value;

    public InsufficientFundsException(String value) {
        super();

        this.value = value;
    }

    public String getMessage() {
        String message;

        if (this.value == "") {
            message = "There are insufficient funds to complete this transaction.";
        } else if (this.value == "change") {
            message = "This register cannot make that change.";
        } else {
            message = String.format("There are insufficient %s dollar bills.", this.value);
        }
        return message;
    }


}
