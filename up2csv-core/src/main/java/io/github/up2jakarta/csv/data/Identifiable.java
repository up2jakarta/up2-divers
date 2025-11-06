package io.github.up2jakarta.csv.data;

import java.io.Serializable;

/**
 * Contract interface for identifiable bean, useful for entity persistence .
 *
 * @param <K> the unique key type
 * @see io.github.up2jakarta.csv.api.hdl.ITraceEvent
 * @see io.github.up2jakarta.csv.fmt.FullRecord
 */
public interface Identifiable<K> extends Serializable {

    /**
     * @return the unique key for persistence
     */
    K getKey();

}
