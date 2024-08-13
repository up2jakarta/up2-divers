package io.github.up2jakarta.csv.persistence;

/**
 * Contract marker (target of parsing) for parsed beans managed by Up2CSV engine.
 *
 * @param <T> the input row type
 */
public interface Parsed<T extends InputRow> {

    /**
     * Sets the source row, useful to keep tracking of sources.
     *
     * @param row the input row source
     */
    void setRow(T row);
}
