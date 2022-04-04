package top.fedoseev.restaurant.voting.exception;

import java.text.MessageFormat;

public enum ErrorMessage {
    ENTITY_NOT_FOUND_BY_ID("Entity with id={0} was not found"),
    USER_NOT_FOUND("User with {0} {1} was not found"),
    RESTAURANT_NOT_FOUND("Restaurant with {0} {1} was not found");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return MessageFormat.format(this.message, args);
    }

}
