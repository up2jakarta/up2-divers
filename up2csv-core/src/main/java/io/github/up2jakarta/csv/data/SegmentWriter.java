package io.github.up2jakarta.csv.data;

/**
 * Extension of {@link java.util.function.Consumer} that throws any kind of exception (checked or not).
 *
 * @param <X> the type of exception could be thrown by the writer
 */
@FunctionalInterface
public interface SegmentWriter<X extends Exception> {

    /**
     * Writes the given record with formatted data.
     *
     * @param data the record columns
     * @throws X if any problem occurs during write processing, typically {@link java.io.IOException}
     */
    void accept(String[] data) throws X;

}
