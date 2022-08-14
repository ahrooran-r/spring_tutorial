package learn.springframework.mvc.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CourseCodeConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
// above two are a must. following is optional
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseCode {

    // define the default attributes
    String value() default "AHR";

    // define default error message
    String message() default "must start with AHR";

    // define default groups
    Class<?>[] groups() default {};

    // define default payloads
    Class<? extends Payload>[] payload() default {};

}
