package io.github.up2jakarta.csv.data;

/**
 * Contract interface for segment having unique reference.
 *
 * @see io.github.up2jakarta.csv.api.IFullRecord
 * @see BusinessObject
 */
public interface Referencable<T extends Comparable<T>> extends Segment {

    /**
     * @return the unique business reference
     */
    T getReference();

}
