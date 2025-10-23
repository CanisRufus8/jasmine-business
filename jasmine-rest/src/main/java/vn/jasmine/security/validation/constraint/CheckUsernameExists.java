package vn.jasmine.security.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import vn.jasmine.security.validation.validator.UsernameExistsValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameExistsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckUsernameExists {

    String message() default "Username is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
