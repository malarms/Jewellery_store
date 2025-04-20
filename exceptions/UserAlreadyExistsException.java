package exceptions;

public class UserAlreadyExistsException extends Exception {
    // Constructor with username
    public UserAlreadyExistsException(String username) {
        super("User '" + username + "' already exists in the system");
    }

    // Constructor with custom message
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}