package io.github.up2jakarta.lov.bst;

import static io.github.up2jakarta.lov.bst.None.undefined;
import static io.github.up2jakarta.lov.bst.RedBlackMap.KVEntry;
import static io.github.up2jakarta.lov.bst.RedBlackSet.Element;
import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Internal key-set Node.
 */
abstract sealed class KSNode<K, N extends Node<K, N>> implements Node<K, N> permits Element, KVEntry {

    private final K key;
    private boolean color = true;
    private Node<K, N> parent, right, left;

    KSNode(K key) {
        this.key = notNull(key, this.getClass(), "key");
        this.parent = this.left = this.right = undefined();
    }

    @Override
    public final Node<K, N> setParent(Node<K, N> parent) {
        return this.parent = parent;
    }

    @Override
    public final Node<K, N> setRight(Node<K, N> right) {
        return this.right = right;
    }

    @Override
    public final Node<K, N> setLeft(Node<K, N> left) {
        return this.left = left;
    }

    @Override
    public final void setColor(boolean color) {
        this.color = color;
    }

    @Override
    public final Node<K, N> getParent() {
        return parent;
    }

    @Override
    public final Node<K, N> getRight() {
        return right;
    }

    @Override
    public final Node<K, N> getLeft() {
        return left;
    }

    @Override
    public final boolean isBlack() {
        return color;
    }

    @Override
    public final boolean isRed() {
        return !color;
    }

    @Override
    public final void setBlack() {
        this.color = true;
    }

    @Override
    public final void setRed() {
        this.color = false;
    }

    @Override
    public final K getKey() {
        return key;
    }

}
