package io.github.up2jakarta.csv.data;

/**
 * Contract interface for business-objects have unique reference.
 *
 * @see io.github.up2jakarta.csv.api.IFullRecord
 * @see BusinessObject
 */
public interface Referencable extends Segment {

    /**
     * @return the unique business reference
     */
    String getReference();

}
