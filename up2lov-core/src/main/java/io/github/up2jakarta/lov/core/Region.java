package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.bst.Cache;

import java.util.Map.Entry;

/**
 * {@link Cache} Region that supports {@link Entry} computing.
 */
public interface Region<K, V> {

    /**
     * If the specified key exists then the existing entry will be returned even if its value is <code>null</code>.
     * <p>
     * If the specified key is not exists, computes its value using the specified resolver and caches the new entry
     * before returns it.
     * <p>
     * If the resolver returns <code>null</code>, the entry will be created within <code>null</code> value.
     * <p>
     * If the resolver itself throws any exception, the exception is rethrown and no entry is created.
     * <p>
     * This convenient method is an alternative of {@link java.util.Map#computeIfAbsent(Object, java.util.function.Function)}
     * that supports <code>null</code> entry-values and checked exceptions.
     *
     * @param key      the entry key
     * @param resolver the value resolver
     * @param <X>      the resolver exception type
     * @return non-null entry related to the specified key
     * @throws X if the resolver throws {@link X}
     */
    <X extends Throwable> Entry<K, V> get(K key, Resolver<K, V, X> resolver) throws X;

    /**
     * Updates the specified cache entry with the specified value and returns the updated entry.
     *
     * @param entry the cache entry
     * @param value the new value
     * @return the updated entry
     * @see io.github.up2jakarta.lov.bst.Immutable
     */
    default Entry<K, V> update(Entry<K, V> entry, V value) {
        entry.setValue(value);
        return entry;
    }

    /**
     * Evicts the expired entries
     */
    void evict();

    /**
     * @return the cache size
     */
    int size();

}
