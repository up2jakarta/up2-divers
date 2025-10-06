package io.github.up2jakarta.csv.data;

/**
 * Contract interface for identifiable bean.
 *
 * @see io.github.up2jakarta.csv.api.IRecord
 * @see BusinessObject
 */
public interface Identifiable<K extends Comparable<K>> {

    /**
     * @return the unique business identifier
     */
    K getReference();

}
