package io.github.up2jakarta.csv.data;

/**
 * Contract interface for segment having unique reference, useful when the {@link BusinessId} is undefined.
 *
 * @param <T> the reference type.
 */
public interface BusinessObject<T extends Comparable<T>> extends Referencable<T> {

    /**
     * Sets the unique business reference.
     *
     * @param value the unique business reference
     */
    void setReference(T value);

}
