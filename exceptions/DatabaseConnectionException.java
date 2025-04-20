package exceptions;

public class DatabaseConnectionException extends JewelleryStoreException {
    public DatabaseConnectionException(String message) {
        super("Database connection failed: " + message);
    }
}