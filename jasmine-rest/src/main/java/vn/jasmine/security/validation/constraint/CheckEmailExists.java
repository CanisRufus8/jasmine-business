package vn.jasmine.security.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.jasmine.security.validation.validator.EmailExistValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckEmailExists {

    String message() default "Email exists, please try again.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
