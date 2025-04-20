package exceptions;

public class JewelleryNotFoundException extends JewelleryStoreException {
    public JewelleryNotFoundException(int id) {
        super("Jewellery with ID " + id + " not found");
    }
    public JewelleryNotFoundException(String message) {
        super(message);
    }
}