package jobservice.exceptions;
/**
 * Exception to throw when a given User is not found in the database.
 */
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 5913980336726106652L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

}