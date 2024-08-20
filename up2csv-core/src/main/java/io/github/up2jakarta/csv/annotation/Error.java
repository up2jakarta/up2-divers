package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.extension.SeverityType;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports
 * {@link io.github.up2jakarta.csv.persistence.InputError#setSeverity(SeverityType)}
 * and {@link io.github.up2jakarta.csv.persistence.InputError#setCode(String)}.
 *
 * @see io.github.up2jakarta.csv.core.EventCreator
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Error {

    /**
     * @return the error code for a specific property.
     */
    String value();

    /**
     * The error severity for a specific property.
     *
     * @return the error severity
     */
    SeverityType severity() default SeverityType.ERROR;

    /**
     * Interface marker that supports {@link Error} for JSR-303 validation.
     */
    interface Payload extends jakarta.validation.Payload {
    }

}
