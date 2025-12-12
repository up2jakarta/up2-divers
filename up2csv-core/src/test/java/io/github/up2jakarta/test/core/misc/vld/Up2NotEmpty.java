package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.fmt.misc.Tests;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Up2NotEmptyValidator.class})
@Error(value = Tests.ERROR_CODE, level = SeverityType.WARNING)
@SuppressWarnings("unused")
public @interface Up2NotEmpty {

    String message() default "{jakarta.validation.constraints.NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
