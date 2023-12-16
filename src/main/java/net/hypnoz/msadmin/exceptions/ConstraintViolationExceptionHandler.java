package net.hypnoz.msadmin.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ConstraintViolationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", e.getMessage());
        body.put("error", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
