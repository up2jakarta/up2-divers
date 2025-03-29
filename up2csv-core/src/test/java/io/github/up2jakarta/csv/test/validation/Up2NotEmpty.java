package io.github.up2jakarta.csv.test.validation;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Up2NotEmptyValidator.class})
@Error(value = Tests.ERROR_CODE, severity = SeverityType.WARNING)
@SuppressWarnings("unused")
public @interface Up2NotEmpty {

    String message() default "{jakarta.validation.constraints.NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
