package top.fedoseev.restaurant.voting.exception;

public class NotFoundException extends BaseException {
    public NotFoundException(ErrorMessage message, Object... args) {
        super(message, args);
    }
}
