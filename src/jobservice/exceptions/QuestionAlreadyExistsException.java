package jobservice.exceptions;
/**
 * Exception to throw when a given user's question already exists in the database.
 */
public class QuestionAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 5682404034073776636L;

    public QuestionAlreadyExistsException() {
        super();
    }

    public QuestionAlreadyExistsException(String message) {
        super(message);
    }

    public QuestionAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
