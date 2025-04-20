package exceptions;

public class UnauthorizedAccessException extends JewelleryStoreException {
    public UnauthorizedAccessException(String requiredRole) {
        super("Access denied. Requires role: " + requiredRole);
    }
}