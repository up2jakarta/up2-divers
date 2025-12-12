package io.github.up2jakarta.lov.bst;

/**
 * Internal Red-black Tree Node.
 */
sealed interface Node<K, N extends Node<K, N>> permits None, KVNode, KSNode {

    void setColor(boolean color);

    Node<K, N> getParent();

    Node<K, N> setParent(Node<K, N> parent);

    Node<K, N> getRight();

    Node<K, N> setRight(Node<K, N> right);

    Node<K, N> getLeft();

    Node<K, N> setLeft(Node<K, N> left);

    boolean isBlack();

    boolean isRed();

    void setBlack();

    void setRed();

    K getKey();

}
