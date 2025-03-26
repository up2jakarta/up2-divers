package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.input.InputType;

/**
 * Contract marker (target of parsing) for parsed beans managed by Up2CSV engine.
 * This marker tells the engine to setting the source row automatically.
 *
 * @param <T> the input row type
 */
public interface Parsed<T extends InputType<T>, R extends InputSegment<T>> extends Segment {

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
