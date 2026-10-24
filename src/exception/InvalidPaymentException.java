package exception;

// a payment did not match the tab
public class InvalidPaymentException extends Exception {

    public InvalidPaymentException(String message) {
        super(message);
    }
}
