package io.github.up2jakarta.csv.annotation;

import jakarta.validation.Valid;

import java.lang.annotation.*;

/**
 * Up2 Shortcut Annotation of JSR-303 {@link jakarta.validation.Valid} that validation activation and groups.
 *
 * @see jakarta.validation.Validator#validate(Object, Class[])
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Valid
public @interface Validated {

    /**
     * Enable the automatic validation (JSR-303).
     *
     * @return <code>true</code> if the validation is enabled;
     */
    boolean enable() default true;

    /**
     * Set the validation groups (JSR-303).
     *
     * @return the validation groups
     */
    Class<?>[] groups() default {};

}
