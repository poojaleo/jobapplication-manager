package jobservice.exceptions;

/**
 * Exception to throw when an attribute that shouldn't be changed is being changed.
 */
public class InvalidAttributeChangeException extends InvalidAttributeException {
    private static final long serialVersionUID = -1402249716746010239L;

    /**
     * Exception with no message or cause.
     */
    public InvalidAttributeChangeException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public InvalidAttributeChangeException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public InvalidAttributeChangeException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public InvalidAttributeChangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
