package top.fedoseev.restaurant.voting.exception;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(ErrorMessage message, Object... args) {
        super(message, args);
    }
}
