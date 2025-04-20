package exceptions;

public class InvalidSaleException extends JewelleryStoreException {
    public InvalidSaleException(String reason) {
        super("Invalid sale: " + reason);
    }
}