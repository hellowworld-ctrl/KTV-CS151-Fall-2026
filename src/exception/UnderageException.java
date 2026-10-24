package exception;

// someone under 21 tried to order alcohol
public class UnderageException extends Exception {

    public UnderageException(String message) {
        super(message);
    }
}
