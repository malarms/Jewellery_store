package exceptions;

public class InsufficientStockException extends JewelleryStoreException {
    public InsufficientStockException(int available, int requested) {
        super(String.format("Insufficient stock (Available: %d, Requested: %d)", available, requested));
    }
}