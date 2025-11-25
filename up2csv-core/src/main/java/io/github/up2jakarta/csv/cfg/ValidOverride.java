package io.github.up2jakarta.csv.cfg;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Shortcut Annotation of JSR-303 {@link jakarta.validation.Valid} that validation activation and groups.
 *
 * @see jakarta.validation.Validator#validate(Object, Class[])
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD})
@Repeatable(value = ValidOverride.List.class)
public @interface ValidOverride {

    /**
     * @return the flag that disables JSR-303 validation for the annotated segment or fragment.
     */
    boolean disable() default false;

    /**
     * @return the path of the fragment whose configuration is being overridden.
     */
    String[] path() default {};

    /**
     * Returns the validation groups (JSR-303).
     *
     * @return the validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Up2J Annotation that supports {@link Repeatable} {@link ValidOverride}.
     */
    @Retention(RUNTIME)
    @Target({FIELD, TYPE})
    @Documented
    @interface List {

        ValidOverride[] value();

    }
}
