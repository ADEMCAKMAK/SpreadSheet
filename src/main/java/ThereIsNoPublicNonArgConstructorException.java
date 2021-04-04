

public class ThereIsNoPublicNonArgConstructorException extends RuntimeException {
    private static final long serialVersionUID = -5034923533875191246L;

    public ThereIsNoPublicNonArgConstructorException(String message, Throwable cause) {
        super(message, cause);
    }
}
