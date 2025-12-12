package io.github.up2jakarta.lov.bst;

import io.github.up2jakarta.lov.core.Region;
import io.github.up2jakarta.lov.core.Resolver;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static java.util.function.Function.identity;

/**
 * Internal key-value Tree.
 */
abstract sealed class KVTree<K, V> extends RedBlackTree<K, KVNode<K, V>> implements Region<K, V>, SequencedMap<K, V> permits RedBlackMap, WeakKeyMap, WeakValueMap, SoftValueMap {

    private EntryView entries;
    private MapView reversed;
    private ValueView values;
    private KeyView keys;

    public KVTree(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override
    public final <X extends Throwable> Entry<K, V> get(K key, Resolver<K, V, X> resolver) throws X {
        return this.insert(key, () -> this.create(key, resolver.get(key)));
    }

    private V setValue(Entry<K, V> entry, V newValue) {
        if (entry instanceof Immutable<K, V>) {
            final V oldValue = entry.getValue();
            if (oldValue != newValue) {
                this.update(entry, newValue);
            }
            return oldValue;
        }
        return entry.setValue(newValue);
    }

    @Override
    public final boolean containsValue(Object value) {
        for (final Iterator<Entry<K, V>> it = ascending(identity()); it.hasNext(); ) {
            final Entry<K, V> entry = it.next();
            if (Objects.equals(entry.getValue(), value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean containsKey(Object key) {
        final KVNode<K, V> entry = this.search((K) key);
        final boolean found = entry != null;
        if (found && entry.getValue() == null) {
            this.detach(entry);
            return false;
        }
        return found;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final V remove(Object key) {
        final KVNode<K, V> entry = this.delete((K) key);
        if (entry != null) {
            return entry.getValue();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean remove(Object key, Object value) {
        final KVNode<K, V> entry = this.search((K) key);
        if (entry != null && Objects.equals(entry.getValue(), value)) {
            this.detach(entry);
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final V get(Object key) {
        final KVNode<K, V> entry = this.search((K) key);
        if (entry != null) {
            return entry.getValue();
        }
        return null;
    }

    @Override
    public final V put(K key, V value) {
        final KVNode<K, V> entry = this.insert(key, () -> this.create(key, value));
        return setValue(entry, value);
    }

    @Override
    public final V putIfAbsent(K key, V value) {
        final KVNode<K, V> entry = this.insert(key, () -> this.create(key, value));
        final V oldValue = entry.getValue();
        if (oldValue == null) {
            return this.setValue(entry, value);
        }
        return oldValue;
    }

    @Override
    public final V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
        final KVNode<K, V> entry = this.insert(key, () -> this.create(key, function.apply(key)));
        final V oldValue = entry.getValue();
        if (oldValue == null) {
            return this.setValue(entry, function.apply(entry.getKey()));
        }
        return oldValue;
    }

    @Override
    public final V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
        final KVNode<K, V> entry = this.insert(key, () -> null);
        if (entry != null) {
            final V newValue = function.apply(entry.getKey(), entry.getValue());
            this.setValue(entry, newValue);
            return newValue;
        }
        return null;
    }

    @Override
    public final V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
        final KVNode<K, V> entry = this.insert(key, () -> this.create(key, function.apply(key, null)));
        final V newValue = function.apply(entry.getKey(), entry.getValue());
        this.setValue(entry, newValue);
        return newValue;
    }

    @Override
    public final V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
        final KVNode<K, V> entry = this.insert(key, () -> this.create(key, value));
        final V newValue = function.apply(entry.getValue(), value);
        this.setValue(entry, newValue);
        return newValue;
    }

    @Override
    public final V replace(K key, V value) {
        final KVNode<K, V> entry = this.insert(key, () -> null);
        if (entry != null) {
            return this.setValue(entry, value);
        }
        return null;
    }

    @Override
    public final boolean replace(K key, V oldValue, V newValue) {
        final KVNode<K, V> entry = this.insert(key, () -> null);
        if (entry != null && Objects.equals(entry.getValue(), oldValue)) {
            this.setValue(entry, newValue);
            return true;
        }
        return false;
    }

    @Override
    public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        notNull(function, this.getClass(), "function");
        for (final Iterator<Entry<K, V>> it = ascending(identity()); it.hasNext(); ) {
            final Entry<K, V> entry = it.next();
            final V value = function.apply(entry.getKey(), entry.getValue());
            this.setValue(entry, value);
        }
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> source) {
        source.forEach((k, v) -> this.insert(k, () -> this.create(k, v)));
    }

    @Override
    public final void forEach(BiConsumer<? super K, ? super V> action) {
        notNull(action, this.getClass(), "action");
        for (final Iterator<Entry<K, V>> it = ascending(identity()); it.hasNext(); ) {
            final Entry<K, V> entry = it.next();
            action.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public final Entry<K, V> firstEntry() {
        return this.min();
    }

    @Override
    public final Entry<K, V> lastEntry() {
        return this.max();
    }

    @Override
    public final Entry<K, V> pollFirstEntry() {
        final KVNode<K, V> node = this.min();
        if (node != null) {
            this.detach(node);
            return node;
        }
        return null;
    }

    @Override
    public final Entry<K, V> pollLastEntry() {
        final KVNode<K, V> node = this.max();
        if (node != null) {
            this.detach(node);
            return node;
        }
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        if (entries == null) {
            entries = new EntryView(true);
        }
        return entries;
    }

    @Override
    public Set<K> keySet() {
        if (keys == null) {
            keys = new KeyView(true);
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        if (values == null) {
            values = new ValueView(true);
        }
        return values;
    }

    abstract KVNode<K, V> create(K key, V value);

    @Override
    public SequencedMap<K, V> reversed() {
        if (reversed == null) {
            reversed = new MapView();
        }
        return reversed;
    }

    private final class MapView implements SequencedMap<K, V> {
        private EntryView entries;
        private ValueView values;
        private KeyView keys;

        @Override
        public SequencedMap<K, V> reversed() {
            return KVTree.this;
        }

        @Override
        public boolean containsValue(Object value) {
            return KVTree.this.containsValue(value);
        }

        @Override
        public int size() {
            return KVTree.this.size();
        }

        @Override
        public boolean isEmpty() {
            return KVTree.this.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return KVTree.this.containsKey(key);
        }

        @Override
        public V remove(Object key) {
            return KVTree.this.remove(key);
        }

        @Override
        public boolean remove(Object key, Object value) {
            return KVTree.this.remove(key, value);
        }

        @Override
        public V get(Object key) {
            return KVTree.this.get(key);
        }

        @Override
        public V put(K key, V value) {
            return KVTree.this.put(key, value);
        }

        @Override
        public V putIfAbsent(K key, V value) {
            return KVTree.this.putIfAbsent(key, value);
        }

        @Override
        public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
            return KVTree.this.computeIfAbsent(key, function);
        }

        @Override
        public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> function) {
            return KVTree.this.computeIfPresent(key, function);
        }

        @Override
        public V compute(K key, BiFunction<? super K, ? super V, ? extends V> function) {
            return KVTree.this.compute(key, function);
        }

        @Override
        public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> function) {
            return KVTree.this.merge(key, value, function);
        }

        @Override
        public V replace(K key, V value) {
            return KVTree.this.replace(key, value);
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            return KVTree.this.replace(key, oldValue, newValue);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            KVTree.this.replaceAll(function);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> source) {
            KVTree.this.putAll(source);
        }

        @Override
        public void clear() {
            KVTree.this.clear();
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            KVTree.this.forEach(action);
        }

        @Override
        public Entry<K, V> firstEntry() {
            return KVTree.this.max();
        }

        @Override
        public Entry<K, V> lastEntry() {
            return KVTree.this.min();
        }

        @Override
        public Entry<K, V> pollFirstEntry() {
            final KVNode<K, V> node = KVTree.this.max();
            if (node != null) {
                KVTree.this.detach(node);
                return node;
            }
            return null;
        }

        @Override
        public Entry<K, V> pollLastEntry() {
            final KVNode<K, V> node = KVTree.this.min();
            if (node != null) {
                KVTree.this.detach(node);
                return node;
            }
            return null;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            if (entries == null) {
                entries = new EntryView(false);
            }
            return entries;
        }

        @Override
        public Set<K> keySet() {
            if (keys == null) {
                keys = new KeyView(false);
            }
            return keys;
        }

        @Override
        public Collection<V> values() {
            if (values == null) {
                values = new ValueView(false);
            }
            return values;
        }
    }

    private final class EntryView extends AbstractSet<Entry<K, V>> {
        private final boolean natural;

        private EntryView(boolean natural) {
            this.natural = natural;
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            if (natural) {
                return ascending(identity());
            }
            return descending(identity());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean contains(Object entry) {
            if (!(entry instanceof Entry<?, ?> e)) {
                return false;
            }
            final KVNode<K, V> node = KVTree.this.search((K) e.getKey());
            return node != null && Objects.equals(node.getValue(), e.getValue());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(Object entry) {
            if (!(entry instanceof Entry<?, ?> e)) {
                return false;
            }
            final KVNode<K, V> node = KVTree.this.search((K) e.getKey());
            if (node != null && Objects.equals(node.getValue(), e.getValue())) {
                KVTree.this.detach(node);
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return KVTree.this.size();
        }

        @Override
        public void clear() {
            KVTree.this.clear();
        }

        @Override
        public Spliterator<Entry<K, V>> spliterator() {
            return RedBlackTree.spliterator(this.iterator(), this.size());
        }
    }

    private final class ValueView extends AbstractCollection<V> {
        private final boolean natural;

        private ValueView(boolean natural) {
            this.natural = natural;
        }

        @Override
        public Iterator<V> iterator() {
            if (natural) {
                return ascending(KVNode::getValue);
            }
            return descending(KVNode::getValue);
        }

        @Override
        public int size() {
            return KVTree.this.size();
        }

        @Override
        public boolean contains(Object value) {
            return containsValue(value);
        }

        @Override
        public void clear() {
            KVTree.this.clear();
        }

        @Override
        public Spliterator<V> spliterator() {
            return RedBlackTree.spliterator(this.iterator(), this.size());
        }
    }

    private final class KeyView extends AbstractSet<K> {
        private final boolean natural;

        private KeyView(boolean natural) {
            this.natural = natural;
        }

        @Override
        public Iterator<K> iterator() {
            if (natural) {
                return ascending(KVNode::getKey);
            }
            return descending(Node::getKey);
        }

        @Override
        public int size() {
            return KVTree.this.size();
        }

        @Override
        public boolean contains(Object key) {
            return containsKey(key);
        }

        @Override
        public boolean remove(Object key) {
            return KVTree.this.remove(key) != null;
        }

        @Override
        public void clear() {
            KVTree.this.clear();
        }

        @Override
        public Spliterator<K> spliterator() {
            return RedBlackTree.spliterator(this.iterator(), this.size());
        }
    }
}
