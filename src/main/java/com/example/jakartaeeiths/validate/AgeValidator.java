package com.example.jakartaeeiths.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<Age, Integer>{
    @Override
    public void initialize(Age constraintAnnotation)  {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer aLong, ConstraintValidatorContext constraintValidatorContext) {
        return aLong >= 0 && aLong <= 150;
    }
}
