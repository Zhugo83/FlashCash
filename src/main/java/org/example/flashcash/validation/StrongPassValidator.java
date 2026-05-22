package org.example.flashcash.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.flashcash.model.UserAccount;

public class StrongPassValidator implements ConstraintValidator<StrongPass, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,64})");
    }
}
