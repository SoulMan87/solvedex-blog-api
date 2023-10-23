package com.soulrebel.blog.exception;

import com.soulrebel.blog.rest.model.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails (
                new Date (),
                exception.getMessage (),
                request.getDescription (false)
        );

        return new ResponseEntity<> (errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(
            BlogAPIException exception,
            WebRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails (
                new Date (),
                exception.getMessage (),
                request.getDescription (false)
        );

        return new ResponseEntity<> (errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception,
            WebRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails (
                new Date (),
                exception.getMessage (),
                request.getDescription (false)
        );

        return new ResponseEntity<> (errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(
            AccessDeniedException exception,
            WebRequest request
    ) {
        ErrorDetails errorDetails = new ErrorDetails (
                new Date (),
                exception.getMessage (),
                request.getDescription (false)
        );

        return new ResponseEntity<> (errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, String> errors = extractErrorListFromStackTrace (exception);

        return new ResponseEntity<> (errors, HttpStatus.BAD_REQUEST);
    }

    private static Map<String, String> extractErrorListFromStackTrace(MethodArgumentNotValidException exception) {
        return exception.getBindingResult ().getAllErrors ().stream ()
                .map (error -> Map.entry (((FieldError) error).getField (), Objects.requireNonNull
                        (error.getDefaultMessage ())))
                .collect (Collectors.toMap (Map.Entry::getKey, Map.Entry::getValue));
    }
}
