package org.example.utils;

import lombok.experimental.UtilityClass;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author - lauren@waterballsa.tw (Lauren Hou)
 */
@UtilityClass
public class ValidationUtils {
    private static final ValidatorFactory factory = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static void validate(Object object) {
        var violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public static void validateProperty(Object object, String propertyName) {
        var violations = validator.validateProperty(object, propertyName);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
