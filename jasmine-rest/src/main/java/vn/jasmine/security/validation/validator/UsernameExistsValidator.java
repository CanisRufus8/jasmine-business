package vn.jasmine.security.validation.validator;

import vn.jasmine.security.service.IUserService;
import vn.jasmine.security.validation.constraint.CheckUsernameExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameExistsValidator implements ConstraintValidator<CheckUsernameExists, String> {

    private final IUserService userService;
    private static final String USERNAME = "username";

    @Override
    public void initialize(CheckUsernameExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (userService.existUserWithUsername(value)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("Username %s is already in use.", value));
            return false;
        }

        return !value.equals(USERNAME);
    }

}
