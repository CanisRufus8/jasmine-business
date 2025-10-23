package vn.jasmine.security.validation.validator;

import vn.jasmine.security.service.IUserService;
import vn.jasmine.security.validation.constraint.CheckEmailExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailExistValidator implements ConstraintValidator<CheckEmailExists, String> {

    private final IUserService userService;

    @Override
    public void initialize(CheckEmailExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (userService.existUserWithEmail(value)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("Email %s is already in use.", value));
            return false;
        }

        return true;
    }

}
