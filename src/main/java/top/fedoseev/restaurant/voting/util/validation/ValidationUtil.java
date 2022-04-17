package top.fedoseev.restaurant.voting.util.validation;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.exception.ValidationException;

import java.util.Objects;

@UtilityClass
public class ValidationUtil {

    public void checkModification(int count, int id) {
        if (count == 0) {
            throw new NotFoundException(ErrorMessage.ENTITY_NOT_FOUND_BY_ID, id);
        }
    }

    public static void checkEquals(Object expected, Object actual, @NonNull String compairingObjectsName) {
        if (!Objects.equals(expected, actual)) {
            throw new ValidationException(ErrorMessage.OBJECTS_NOT_EQUALS, compairingObjectsName, expected);
        }
    }
}
