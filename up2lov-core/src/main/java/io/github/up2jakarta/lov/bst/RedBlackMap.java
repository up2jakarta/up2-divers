package io.github.up2jakarta.lov.bst;

import java.util.Comparator;

/**
 * Simple implementation of {@link java.util.SequencedMap} and {@link io.github.up2jakarta.lov.core.Region} based on
 * red-black binary search tree.
 *
 * @param <K> the key type
 * @param <V> the key value
 */
public non-sealed class RedBlackMap<K, V> extends KVTree<K, V> {

    public RedBlackMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override
    public final void evict() {
        this.clear();
    }

    @Override
    final KVNode<K, V> create(K key, V value) {
        return new KVEntry<>(key, value);
    }

    static final class KVEntry<K, V> extends KSNode<K, KVNode<K, V>> implements KVNode<K, V> {
        private V value;

        private KVEntry(K key, V value) {
            super(key);
            this.value = value;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            final V existing = this.value;
            this.value = value;
            return existing;
        }

        @Override
        public String toString() {
            return this.getKey() + "=" + value;
        }
    }
}
