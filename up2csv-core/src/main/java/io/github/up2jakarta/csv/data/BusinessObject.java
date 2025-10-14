package io.github.up2jakarta.csv.data;

public interface BusinessObject extends Segment, Referencable {

    /**
     * Sets the unique business reference
     */

    void setReference(String reference);

}
