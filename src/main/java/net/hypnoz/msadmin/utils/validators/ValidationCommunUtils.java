package net.hypnoz.msadmin.utils.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

public class ValidationCommunUtils {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    private void ValidationUtils() {

    }

    public static <T> void validate(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
