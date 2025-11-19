package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.lov.SeverityType;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link io.github.up2jakarta.csv.api.IEvent#getLevel()}
 * and {@link io.github.up2jakarta.csv.api.IEvent#getCode()}.
 *
 * @see io.github.up2jakarta.csv.api.IEvent
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Error {

    /**
     * @return the event code.
     */
    String value();

    /**
     * @return the event level
     */
    SeverityType level() default SeverityType.ERROR;

    /**
     * Interface marker that supports {@link Error} for JSR-303 validation.
     */
    interface Payload extends jakarta.validation.Payload {
    }

}
