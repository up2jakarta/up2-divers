package io.github.up2jakarta.lov.bst;

/**
 * Internal Undefined Node.
 */
final class None<K, N extends Node<K, N>> implements Node<K, N> {
    static final Node<?, ?> NAN = new None<>();

    private None() {
    }

    @SuppressWarnings("unchecked")
    static <K, N extends Node<K, N>> Node<K, N> undefined() {
        return (Node<K, N>) NAN;
    }

    @Override
    public Node<K, N> setParent(Node<K, N> parent) {
        return this;
    }

    @Override
    public Node<K, N> setRight(Node<K, N> right) {
        return this;
    }

    @Override
    public Node<K, N> setLeft(Node<K, N> left) {
        return this;
    }

    @Override
    public Node<K, N> getParent() {
        return this;
    }

    @Override
    public Node<K, N> getRight() {
        return this;
    }

    @Override
    public Node<K, N> getLeft() {
        return this;
    }

    @Override
    public boolean isBlack() {
        return true;
    }

    @Override
    public void setColor(boolean color) {
    }

    @Override
    public boolean isRed() {
        return false;
    }

    @Override
    public void setBlack() {
    }

    @Override
    public void setRed() {
    }

    @Override
    public K getKey() {
        return null;
    }

    @Override
    public String toString() {
        return "?";
    }

}
