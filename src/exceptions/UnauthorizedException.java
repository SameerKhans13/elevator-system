package exceptions;

// Custom exception for authentication failures
public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
        super(message);
    }
}
