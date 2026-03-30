package exceptions;

// Exception for weight limit exceeded
public class WeightLimitExceededException extends Exception {
    public WeightLimitExceededException(String message) {
        super(message);
    }
}
