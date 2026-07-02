package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.lov.CodeList;

/**
 * Contact interface for {@link io.github.up2jakarta.csv.cfg.Truncated} input record.
 *
 * @param <T> the input segment type
 */
public interface IRecord<T extends CodeList<T>> extends Segment {

    /**
     * Returns the input discriminator, must not be null.
     *
     * @return the input segment type
     */
    T getType();

    /**
     * Returns the input data, must not be null.
     *
     * @return the input truncated data
     */
    String[] getData();

}
