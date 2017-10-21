package main.java.resources.exceptions;

public class InvalidInputException extends ArrayIndexOutOfBoundsException {
        private String command;

        public InvalidInputException(String commandString) {
            super();

            this.command = commandString;
        }

        public String getMessage() {
            String message;

            if (this.command.equals("change")) {
                message = String.format("\nCommand must be the word \"%s\" followed by one integer representing the dollar\namount of change to make: \"%s 23\"", this.command, this.command);
            } else {
                message = String.format("\nCommand must be the word \"%s\" followed by five, space-delimited integers\nrepresenting the number of bills of each denomination (20s, 10s, 5s, 2s, 1s): \"%s 1 0 2 1 0\"", this.command, this.command);
            }

            return message;
        }
}
