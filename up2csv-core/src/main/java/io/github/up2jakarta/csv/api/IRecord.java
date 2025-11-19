package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.CodeList;

/**
 * Contact interface for {@link io.github.up2jakarta.csv.cfg.Truncated} input record.
 *
 * @param <T> the input segment type
 */
public interface IRecord<T extends CodeList<T>> extends Segment {

    /**
     * @return the input segment type
     */
    T getType();

    /**
     * @return the input truncated data
     */
    String[] getData();

}
