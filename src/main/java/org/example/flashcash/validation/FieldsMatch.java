package org.example.flashcash.validation;

public @interface FieldsMatch {

    String first();
    String second();

    String message() default "Error";

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
