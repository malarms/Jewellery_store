package exceptions;

public class UserNotFoundException extends Exception {
    // Constructor with username
    public UserNotFoundException(String username) {
        super("User '" + username + "' not found in the system");
    }

    // Constructor with user ID
    public UserNotFoundException(int userId) {
        super("User with ID " + userId + " not found");
    }

    // Constructor with custom message
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}