package net.hypnoz.msadmin.exceptions;

import net.mariasoft.administrations.web.rest.errors.BadRequestAlertException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {


    @ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity<String> handleBadRequestAlertException(BadRequestAlertException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorGlobal> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        UriComponents uriComponents =
                ServletUriComponentsBuilder.fromCurrentRequestUri().build();

        var errorGlobal = ErrorGlobal.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Erreur de validation pour " + request.getDescription(false))
                .instance(uriComponents.getPath())
                .build();

        // Obtenez toutes les erreurs de validation
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        errorGlobal.setErrors(errors);

        return new ResponseEntity<>(errorGlobal, HttpStatus.BAD_REQUEST);
    }
}