package exceptions;

public class InvalidCredentialsException extends Exception {
    // Basic constructor
    public InvalidCredentialsException() {
        super("Invalid username or password");
    }

    // Constructor with custom message
    public InvalidCredentialsException(String message) {
        super(message);
    }

    // Constructor with chained exception
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}