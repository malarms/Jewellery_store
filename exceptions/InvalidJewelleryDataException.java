package exceptions;

public class InvalidJewelleryDataException extends JewelleryStoreException {
    public InvalidJewelleryDataException(String fieldName, Object value) {
        super("Invalid value '" + value + "' for field '" + fieldName + "'");
    }
}