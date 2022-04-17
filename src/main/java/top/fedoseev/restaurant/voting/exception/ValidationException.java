package top.fedoseev.restaurant.voting.exception;

public class ValidationException extends BaseException {
    public ValidationException(ErrorMessage message, Object... args) {
        super(message, args);
    }
}
