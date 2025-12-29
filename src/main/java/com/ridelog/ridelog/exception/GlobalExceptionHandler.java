package com.ridelog.ridelog.exception;

import com.ridelog.ridelog.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle service-level exceptions with error codes
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        Map<String, String> details = Map.of(
                "code", ex.getErrorCode(),
                "message", ex.getMessage()
        );
        ErrorResponse error = new ErrorResponse("Business Error", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Handle validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        ErrorResponse error = new ErrorResponse("Validation Failed", errors);
        return ResponseEntity.badRequest().body(error);
    }

    // Handle unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        ErrorResponse error = new ErrorResponse("Internal Server Error", Map.of("error", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
