package io.github.up2jakarta.lov.bst;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Comparator;

import static io.github.up2jakarta.lov.bst.Cache.format;
import static io.github.up2jakarta.lov.bst.None.undefined;

/**
 * Simple implementation of {@link java.util.SequencedMap} and {@link io.github.up2jakarta.lov.core.Region} based on
 * binary-search tree with weak-reference keys.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public non-sealed class WeakKeyMap<K, V> extends KVTree<K, V> {

    private final ReferenceQueue<K> queue = new ReferenceQueue<>();

    public WeakKeyMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override
    public final void evict() {
        Cache.evict(queue, this::size, this::detach);
    }

    @Override
    final KVNode<K, V> create(K key, V value) {
        return new WKEntry<>(key, queue, value);
    }

    static final class WKEntry<K, V> extends WeakReference<K> implements KVNode<K, V> {
        private Node<K, KVNode<K, V>> parent, right, left;
        private boolean color = true;
        private V value;

        private WKEntry(K key, ReferenceQueue<K> queue, V value) {
            super(key, queue);
            this.value = value;
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
            return super.get();
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
            return format(this) + "=" + value;
        }
    }
}
