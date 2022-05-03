package top.fedoseev.restaurant.voting.exception;

import java.text.MessageFormat;

public enum ErrorMessage {
    ENTITY_NOT_FOUND_BY_ID("Entity with id={0} was not found"),
    NOT_FOUND_BY_ID("{0} with id={1} was not found"),
    DISH_NOT_FOUND_BY_ID_AND_MENU_ID_AND_RESTAURANT_ID("Dish with id={0}, menuId={1} and restaurantId={2} was not found"),
    MENU_NOT_FOUND_BY_ID_AND_RESTAURANT_ID("Menu with id={0} and restaurantId={1} was not found"),
    VOTE_NOT_FOUND_BY_ID_AND_USER_ID("Vote with id={0} and userId={1} was not found"),
    USER_NOT_FOUND("User with {0} {1} was not found"),
    OBJECTS_NOT_EQUALS("{0} must be equal to {1}"),
    DAY_HAS_COME("The day {0} has already come"),
    VOTING_HAS_FINISHED("Today''s voting has already finished at {0}"),
    SORT_NOT_ALLOWED_BY_FIELD("You tried to sort by {0} fields but allowed only {1}");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return MessageFormat.format(this.message, args);
    }

}
