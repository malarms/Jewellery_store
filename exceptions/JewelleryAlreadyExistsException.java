package exceptions;

public class JewelleryAlreadyExistsException extends JewelleryStoreException {
    public JewelleryAlreadyExistsException(int id) {
        super("Jewellery with ID " + id + " already exists");
    }

    public JewelleryAlreadyExistsException(String message) {
        super(message);
    }
}