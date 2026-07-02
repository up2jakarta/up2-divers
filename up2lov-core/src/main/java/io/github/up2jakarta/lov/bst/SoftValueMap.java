package io.github.up2jakarta.lov.bst;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Comparator;

import static io.github.up2jakarta.lov.bst.Cache.format;
import static io.github.up2jakarta.lov.bst.None.undefined;

/**
 * Simple implementation of {@link java.util.SequencedMap} and {@link io.github.up2jakarta.lov.core.Region} based
 * on binary-search tree with soft-reference values.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public non-sealed class SoftValueMap<K, V> extends KVTree<K, V> {

    private final ReferenceQueue<V> queue = new ReferenceQueue<>();

    public SoftValueMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override
    public final Entry<K, V> update(Entry<K, V> entry, V value) {
        final K key = entry.getKey();
        if (entry instanceof SVEntry<K, V> node) {
            this.detach(node);
        } else {
            this.delete(key);
        }
        return this.insert(key, () -> new SVEntry<>(key, value, queue));
    }

    @Override
    public final void evict() {
        Cache.evict(queue, this::detach);
    }

    @Override
    final Immutable<K, V> create(K key, V value) {
        return new SVEntry<>(key, value, queue);
    }

    static final class SVEntry<K, V> extends SoftReference<V> implements Immutable<K, V> {
        private final K key;
        private boolean color = true;
        private Node<K, KVNode<K, V>> parent, right, left;

        private SVEntry(K key, V value, ReferenceQueue<V> queue) {
            super(value, queue);
            this.key = key;
            this.parent = this.left = this.right = undefined();
        }

        @Override
        public Node<K, KVNode<K, V>> setParent(Node<K, KVNode<K, V>> parent) {
            return this.parent = parent;
        }

        @Override
        public Node<K, KVNode<K, V>> setRight(Node<K, KVNode<K, V>> right) {
            return this.right = right;
        }

        @Override
        public Node<K, KVNode<K, V>> setLeft(Node<K, KVNode<K, V>> left) {
            return this.left = left;
        }

        @Override
        public void setColor(boolean color) {
            this.color = color;
        }

        @Override
        public Node<K, KVNode<K, V>> getParent() {
            return parent;
        }

        @Override
        public Node<K, KVNode<K, V>> getRight() {
            return right;
        }

        @Override
        public Node<K, KVNode<K, V>> getLeft() {
            return left;
        }

        @Override
        public boolean isBlack() {
            return color;
        }

        @Override
        public boolean isRed() {
            return !color;
        }

        @Override
        public void setBlack() {
            this.color = true;
        }

        @Override
        public void setRed() {
            this.color = false;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return super.get();
        }

        @Override
        public String toString() {
            return this.getKey() + "=" + format(this);
        }
    }
}
