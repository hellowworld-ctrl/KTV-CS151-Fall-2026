package exception;

// an order could not be placed
public class InvalidOrderException extends Exception {

    public InvalidOrderException(String message) {
        super(message);
    }
}
