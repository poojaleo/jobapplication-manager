package jobservice.exceptions;

public class EmptyJobApplicationListException extends RuntimeException {

    private static final long serialVersionUID = -2201905186377991964L;

    public EmptyJobApplicationListException(){ super();}

    public EmptyJobApplicationListException(String message) {
        super(message);
    }

    public EmptyJobApplicationListException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyJobApplicationListException(Throwable cause) {
        super(cause);
    }
}
