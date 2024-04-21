package com.beautysalon.api.v1.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, Object> {

    private String password;
    private String passwordConfirm;

    @Override
    public void initialize(PasswordConfirm passwordConfirm) {
        this.password = passwordConfirm.password();
        this.passwordConfirm = passwordConfirm.passwordConfirm();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object passwordConfirmValue = new BeanWrapperImpl(value).getPropertyValue(passwordConfirm);

        return (passwordValue != null && passwordValue.equals(passwordConfirmValue))
                || (passwordValue == null && passwordConfirmValue == null);
    }
}
