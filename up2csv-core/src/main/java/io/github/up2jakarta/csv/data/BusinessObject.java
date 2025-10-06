package io.github.up2jakarta.csv.data;

public interface BusinessObject extends Segment, Identifiable<String> {

    /**
     * Sets the unique business identifier
     */

    void setReference(String key);

}
