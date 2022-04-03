package top.fedoseev.restaurant.voting.util.validation;

import lombok.experimental.UtilityClass;
import top.fedoseev.restaurant.voting.exception.EntityNotFoundException;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;

@UtilityClass
public class ValidationUtil {

    public void checkModification(int count, int id) {
        if (count == 0) {
            throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND_BY_ID, id);
        }
    }

}
