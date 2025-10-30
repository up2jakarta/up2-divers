package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IRecord;

/**
 * Contract marker (target of parsing) for parsed beans managed by Up2CSV engine.
 * This marker tells the engine to setting the source row automatically.
 *
 * @param <R> the input row type
 */
public interface Recordable<R extends IRecord<?>> extends Segment {

    /**
     * @return the record that being parsed.
     */
    R getRecord();

    /**
     * Sets the source row, useful to keep tracking of sources.
     *
     * @param record the input source
     */
    void setRecord(R record);

}
