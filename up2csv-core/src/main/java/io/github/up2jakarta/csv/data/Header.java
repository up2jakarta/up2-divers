package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.core.Up2Writer;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Annotation that supports the {@link ITerm} resolution.
 * This is a simple implementation, used to fill flat-data header names.
 *
 * @see io.github.up2jakarta.csv.api.IEvent#getType()
 * @see Up2Writer#header()
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface Header {

    /**
     * @return the {@link ITerm#getCode()}
     */
    String code();

    /**
     * @return the {@link ITerm#getName()}
     */
    String name();

}
