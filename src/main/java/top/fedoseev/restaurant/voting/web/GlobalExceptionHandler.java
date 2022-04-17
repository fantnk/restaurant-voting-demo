package top.fedoseev.restaurant.voting.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.exception.ValidationException;
import top.fedoseev.restaurant.voting.to.common.ErrorResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String HANDLE_EXCEPTION = "Handle exception";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponse handleEntityNotFoundException(NotFoundException exception) {
        log.error(HANDLE_EXCEPTION, exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleValidationException(ValidationException exception) {
        log.error(HANDLE_EXCEPTION, exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        log.error(HANDLE_EXCEPTION, exception);
        Function<ConstraintViolation<?>, String> keyMapper = e -> e.getPropertyPath().toString();
        Function<ConstraintViolation<?>, List<String>> valueMapper = e -> Collections.singletonList(e.getMessage());
        BinaryOperator<List<String>> mergeFunction = (l1, l2) -> Stream.concat(l1.stream(), l2.stream())
                .toList();

        Map<String, List<String>> errors = exception.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, LinkedHashMap::new));

        return new ErrorResponse(exception.getMessage(), errors);
    }

    @ExceptionHandler(javax.validation.ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ErrorResponse handleValidationException(javax.validation.ValidationException exception) {
        log.error(HANDLE_EXCEPTION, exception);
        return new ErrorResponse(exception.getMessage());
    }
}
