package top.fedoseev.restaurant.voting.exception;

public abstract class BaseException extends RuntimeException {

    protected final ErrorMessage errorMessage;
    protected final Object[] args;

    public BaseException(ErrorMessage message, Object... args) {
        super(message.format(args));
        this.errorMessage = message;
        this.args = args;
    }

    public BaseException(ErrorMessage message, Throwable throwable, Object... args) {
        super(message.format(args), throwable);
        this.errorMessage = message;
        this.args = args;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
