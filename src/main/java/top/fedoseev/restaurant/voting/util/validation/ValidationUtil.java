package top.fedoseev.restaurant.voting.util.validation;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.exception.ValidationException;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public static  void checkAllowedToSort(Sort sort, Collection<String> allowedFields) {
        boolean allowed = sort.stream()
                .map(Sort.Order::getProperty)
                .allMatch(allowedFields::contains);
        if (!allowed) {
            Collector<CharSequence, ?, String> joining = Collectors.joining(", ", "[", "]");
            String actualSort = sort.stream()
                    .map(Sort.Order::getProperty)
                    .collect(joining);
            String expectedSort = allowedFields.stream()
                    .collect(joining);
            throw new ValidationException(ErrorMessage.SORT_NOT_ALLOWED_BY_FIELD, actualSort, expectedSort);
        }
    }
}
