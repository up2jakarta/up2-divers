package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;

/**
 * Contract marker (target of parsing) for parsed beans managed by Up2CSV engine.
 * This marker tells the engine to setting the source row automatically.
 *
 * @param <T> the segment type
 * @param <R> the input row type
 */
public interface Recordable<T extends IType<?, T>, R extends IRecord<T>> extends Segment {

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
