package io.github.up2jakarta.csv.test.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Up2NotEmptyValidator implements ConstraintValidator<Up2NotEmpty, CharSequence> {
    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        if (charSequence == null) {
            return false;
        }
        return !charSequence.isEmpty();
    }
}
