package io.github.up2jakarta.csv.data;

import java.io.Serializable;

/**
 * Contract interface for business-objects have unique reference.
 *
 * @see io.github.up2jakarta.csv.api.hdl.IRecordEntity
 * @see BusinessObject
 */
public interface Referencable extends Serializable {

    /**
     * @return the unique business reference
     */
    String getReference();

}
