package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.misc.SeverityType;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link io.github.up2jakarta.csv.persistence.InputError#setSeverity(SeverityType)}.
 *
 * @see io.github.up2jakarta.csv.core.EventCreator
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Severity {

    /**
     * The error severity for a specific field.
     * Note this value overrides the {@link SeverityType.Level} of javax.validation.constraints annotations.
     *
     * @return the error severity
     */
    SeverityType value();

}
