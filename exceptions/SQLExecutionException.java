package exceptions;

public class SQLExecutionException extends JewelleryStoreException {
    public SQLExecutionException(String query) {
        super("Failed to execute SQL query: " + query);
    }
}