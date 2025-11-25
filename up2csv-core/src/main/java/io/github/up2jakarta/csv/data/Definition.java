package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.core.Up2Writer;

import java.lang.annotation.*;

/**
 * Up2J Annotation that supports the {@link io.github.up2jakarta.csv.data.DataType} resolution.
 * This is a simple implementation, used to fill flat-data header names.
 *
 * @see io.github.up2jakarta.csv.api.IEvent#getType()
 * @see Up2Writer#header()
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Definition {

    /**
     * @return the {@link io.github.up2jakarta.csv.data.DataType#getCode()}
     */
    String code();

    /**
     * @return the {@link io.github.up2jakarta.csv.data.DataType#getName()}
     */
    String value();

}
