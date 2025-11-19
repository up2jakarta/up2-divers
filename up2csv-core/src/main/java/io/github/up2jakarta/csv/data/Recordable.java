package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IRecord;

/**
 * Contract interface for parsed bean that keep tracking of the source of data.
 * This marker tells the engine to setting the record source automatically.
 *
 * @param <R> the input record type
 */
public interface Recordable<R extends IRecord<?>> extends Segment {

    /**
     * @return the record that being parsed.
     */
    R getRecord();

    /**
     * Sets the input record source, useful for keep tracking of sources.
     *
     * @param record the input source
     */
    void setRecord(R record);

}
