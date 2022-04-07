package jobservice.exceptions;

/**
 * Exception to throw when a given Job Application is not found in the database.
 */
public class JobApplicationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3061703979403115577L;

    /**
     * Exception with no message or cause.
     */
    public JobApplicationNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public JobApplicationNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public JobApplicationNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public JobApplicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
