package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.Identifiable;

/**
 * Up2J contract interface of identifiable code-list that can be identified by {@link #getKey()}.
 *
 * @param <K> the identifier type
 * @param <T> the self-type implementation
 * @see EntityResolver
 */
public interface EntityList<K extends Comparable<K>, T extends CodeList<T> & Identifiable<K>> extends CodeList<T>, Identifiable<K> {

}
