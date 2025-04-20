package exceptions;

public class PaymentFailedException extends JewelleryStoreException {
    public PaymentFailedException(String paymentMethod) {
        super("Payment failed using " + paymentMethod);
    }
}