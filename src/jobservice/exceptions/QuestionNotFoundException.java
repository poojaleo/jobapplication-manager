package jobservice.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -7213772835683434903L;

    public QuestionNotFoundException() {
        super();
    }

    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionNotFoundException(Throwable cause) {
        super(cause);
    }
}
