package io.github.up2jakarta.lov.bst;

/**
 * Interface Marker for {@link java.util.Map.Entry} with read-only value.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public sealed interface Immutable<K, V> extends KVNode<K, V> permits WeakValueMap.WVEntry, SoftValueMap.SVEntry {

    /**
     * @see io.github.up2jakarta.lov.core.Region#update(java.util.Map.Entry, Object)
     */
    @Override
    default V setValue(V value) {
        throw new UnsupportedOperationException();
    }

}
