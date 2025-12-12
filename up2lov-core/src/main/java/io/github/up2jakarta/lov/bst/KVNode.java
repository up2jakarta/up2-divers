package io.github.up2jakarta.lov.bst;

import io.github.up2jakarta.lov.bst.RedBlackMap.KVEntry;
import io.github.up2jakarta.lov.bst.WeakKeyMap.WKEntry;

import java.util.Map.Entry;

/**
 * Internal key-value Node.
 */
sealed interface KVNode<K, V> extends Entry<K, V>, Node<K, KVNode<K, V>> permits Immutable, KVEntry, WKEntry {

}
