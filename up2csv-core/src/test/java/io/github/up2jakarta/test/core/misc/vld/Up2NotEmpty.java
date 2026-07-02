package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Error;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.fmt.misc.Tests.ERROR_CODE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {Up2NotEmptyValidator.class})
@Error(value = ERROR_CODE, level = WARNING)
@SuppressWarnings("unused")
public @interface Up2NotEmpty {

    String message() default "{jakarta.validation.constraints.NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
