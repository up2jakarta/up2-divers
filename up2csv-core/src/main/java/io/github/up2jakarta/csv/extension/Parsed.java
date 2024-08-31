package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.input.InputRow;

/**
 * Contract marker (target of parsing) for parsed beans managed by Up2CSV engine.
 * This marker tells the engine to setting the source row automatically.
 *
 * @param <T> the input row type
 */
public interface Parsed<T extends InputRow> extends Segment {

    /**
     * Sets the source row, useful to keep tracking of sources.
     *
     * @param row the input row source
     */
    void setRow(T row);
}
