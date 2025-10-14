package io.github.up2jakarta.csv.data;

import java.io.IOException;

@FunctionalInterface
public interface SegmentWriter {

    /**
     * Writes the given record with formatted data.
     *
     * @param data the record columns
     */
    void accept(String[] data) throws IOException;

}
