package exceptions;

public class JewelleryStoreException extends Exception {
    public JewelleryStoreException(String message) {
        super(message);
    }
    
    public JewelleryStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}