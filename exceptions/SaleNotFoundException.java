package exceptions;

public class SaleNotFoundException extends Exception {
    // Constructor that takes an int (sale ID)
    public SaleNotFoundException(int saleId) {
        super("Sale with ID " + saleId + " not found");
    }
    
    // Add this constructor that takes a String message
    public SaleNotFoundException(String message) {
        super(message);
    }
}