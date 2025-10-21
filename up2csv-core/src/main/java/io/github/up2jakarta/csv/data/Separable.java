package io.github.up2jakarta.csv.data;

import java.io.Serializable;

/**
 * Contract interface for multi-segments record definition, useful for aggregation.
 *
 * @see io.github.up2jakarta.csv.api.IRecord
 * @see Up2Aggregator#next()
 */
public interface Separable extends Serializable {

    /**
     * @return the business-object reference aka the container key that used to aggregate many records
     */
    String getBusinessReference();

}
