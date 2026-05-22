package org.example.flashcash.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StrongPassValidator.class})
public @interface StrongPass {

    String message() default "Error";

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

}
