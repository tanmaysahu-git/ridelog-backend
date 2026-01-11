package com.ridelog.ridelog.common.exception;

import com.ridelog.ridelog.common.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        // Map each violated property to its error message
        Map<String, String> errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(), // field name
                        violation -> violation.getMessage()                  // violation message
                ));

        ErrorResponse error = new ErrorResponse("Constraint Violation", errors);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Data integrity violation";

        Throwable cause = ex.getCause();
        if (cause instanceof org.hibernate.exception.ConstraintViolationException cve) {
            String constraintName = cve.getConstraintName();
            if (constraintName != null) {
                String causeMsg = ex.getRootCause().getMessage();

                Pattern emailPattern = Pattern.compile("Key \\(email\\)=\\(.*\\) already exists");
                Matcher matcher = emailPattern.matcher(causeMsg);
                Pattern mobilePattern = Pattern.compile("Key \\(mobile_number\\)=\\(.*\\) already exists");
                if (matcher.find()) {
                    message = "Email already exists";
                } else if (mobilePattern.matcher(causeMsg).find()) {
                    message = "Mobile number already exists";
                } else if (constraintName.contains("users_check")) {
                    message = "At least one of email or mobile number must be provided";
                }
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(message, null));
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {

        Map<String, String> details = Map.of(
                "error", "Invalid username or password"
        );

        ErrorResponse response = new ErrorResponse(
                "Authentication Failed",
                details
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
}
