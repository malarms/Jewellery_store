package exceptions;

public class CustomerAlreadyExistsException extends JewelleryStoreException {
    public CustomerAlreadyExistsException(String identifier) {
        super("Customer with identifier '" + identifier + "' already exists");
    }
}