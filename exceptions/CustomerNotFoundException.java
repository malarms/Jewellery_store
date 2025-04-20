package exceptions;

public class CustomerNotFoundException extends Exception {
    // Constructor with ID
    public CustomerNotFoundException(int id) {
        super("Customer with ID " + id + " not found");
    }
    
    // Constructor with email/string identifier
    public CustomerNotFoundException(String identifier) {
        super("Customer '" + identifier + "' not found");
    }
}