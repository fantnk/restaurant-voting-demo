package top.fedoseev.restaurant.voting.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import top.fedoseev.restaurant.voting.exception.BaseException;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.exception.ValidationException;
import top.fedoseev.restaurant.voting.exception.VoteIsTooLateException;
import top.fedoseev.restaurant.voting.to.common.ErrorResponse;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String HANDLE_EXCEPTION = "Handle exception";

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException exception,
                                                                  @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        logException(exception);
        Map<String, List<String>> errors = mapBindingErrors(exception.getFieldErrors());
        return createResponseEntity(status, new ErrorResponse(exception.getMessage(), errors));
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleBindException(@NonNull BindException exception, @NonNull HttpHeaders headers, @NonNull HttpStatus status,
                                                         @NonNull WebRequest request) {
        logException(exception);
        Map<String, List<String>> errors = mapBindingErrors(exception.getFieldErrors());

        return createResponseEntity(status, new ErrorResponse(exception.getMessage(), errors));
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, Object body, @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatus status, @NonNull WebRequest request) {
        log.error("Exception", exception);
        super.handleExceptionInternal(exception, body, headers, status, request);
        return createResponseEntity(status, new ErrorResponse(NestedExceptionUtils.getMostSpecificCause(exception).getMessage()));
    }

    @ExceptionHandler({
            VoteIsTooLateException.class,
            NotFoundException.class,
            EntityNotFoundException.class,
            PropertyReferenceException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse handleBadRequestExceptions(RuntimeException exception) {
        logException(exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse handleBaseException(BaseException exception) {
        logException(exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        logException(exception);
        Map<String, List<String>> errors = mapConstraintViolations(exception.getConstraintViolations());

        return new ErrorResponse(exception.getMessage(), errors);
    }

    @ExceptionHandler({
            javax.validation.ValidationException.class,
            ValidationException.class,
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected ErrorResponse handleValidationException(RuntimeException exception) {
        logException(exception);
        return new ErrorResponse(exception.getMessage());
    }

    private ResponseEntity<Object> createResponseEntity(HttpStatus status, ErrorResponse exception) {
        return ResponseEntity.status(status).body(exception);
    }

    private Map<String, List<String>> mapBindingErrors(List<FieldError> fieldErrors) {
        Function<FieldError, String> keyMapper = FieldError::getField;
        Function<FieldError, List<String>> valueMapper = e -> Collections.singletonList(e.getDefaultMessage());
        BinaryOperator<List<String>> mergeFunction = (l1, l2) -> Stream.concat(l1.stream(), l2.stream())
                .toList();

        return fieldErrors.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, LinkedHashMap::new));
    }

    private Map<String, List<String>> mapConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
        Function<ConstraintViolation<?>, String> keyMapper = e -> e.getPropertyPath().toString();
        Function<ConstraintViolation<?>, List<String>> valueMapper = e -> Collections.singletonList(e.getMessage());
        BinaryOperator<List<String>> mergeFunction = (l1, l2) -> Stream.concat(l1.stream(), l2.stream())
                .toList();

        return constraintViolations.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction, LinkedHashMap::new));
    }

    private void logException(Exception exception) {
        log.error(HANDLE_EXCEPTION, exception);
    }
}
