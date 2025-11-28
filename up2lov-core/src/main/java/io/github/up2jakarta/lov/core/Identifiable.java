package io.github.up2jakarta.lov.core;

import java.io.Serializable;

/**
 * Contract interface for identifiable beans, useful for entity persistence.
 *
 * @param <K> the unique key type
 * @see io.github.up2jakarta.lov.EntityList
 */
public interface Identifiable<K> extends Serializable {

    /**
     * @return the unique key (identifier)
     */
    K getKey();

}
