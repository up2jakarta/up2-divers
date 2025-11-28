package io.github.up2jakarta.lov.core;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;

import static io.github.up2jakarta.lov.core.Cache.InMemory;
import static io.github.up2jakarta.lov.core.WKCache.WeakKey;

/**
 * Simple implementation of {@link Cache} based on binary-search tree with weak-reference keys.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public final class WKCache<K extends Comparable<K>, V> extends InMemory<K, V, K, WeakKey<K>, V> {

    public WKCache() {
        super(Entry::getKey, WeakKey::compareTo);
    }

    private V replace(Map<WeakKey<K>, V> map, WeakKey<K> key, V oldValue, V newValue) {
        if (newValue != null && newValue != oldValue) {
            map.replace(key, oldValue, newValue);
        }
        return newValue;
    }

    private V add(Map<WeakKey<K>, V> map, WeakKey<K> key, V value) {
        if (value != null) {
            map.put(key, value);
        }
        return value;
    }

    @Override
    public <X extends Throwable> V get(K k, Processor<K, V, V, X> processor) throws X {
        return super.compute(k, (m, q) -> {
            final WeakKey<K> key = new WeakKey<>(k, q);
            final V value = m.get(key);
            if (value == null) {
                return this.add(m, key, processor.get(k, null));
            }
            return this.replace(m, key, value, processor.get(k, value));
        });
    }

    @Override
    public <X extends Throwable> V get(K k, Resolver<? super K, V, X> resolver) throws X {
        return super.compute(k, (m, q) -> {
            final WeakKey<K> key = new WeakKey<>(k, q);
            final V value = m.get(key);
            if (value == null) {
                return this.add(m, key, resolver.get(k));
            }
            return value;
        });
    }

    static class WeakKey<K extends Comparable<K>> extends WeakReference<K> implements Comparable<WeakKey<K>> {
        private WeakKey(K value, ReferenceQueue<K> queue) {
            super(value, queue);
        }

        @Override
        public int compareTo(WeakKey<K> that) {
            if (this == that) {
                return 0;
            }
            final K b = that.get();
            if (b == null) {
                return 1;
            } else if (this.refersTo(b)) {
                return 0;
            }
            final K a = this.get();
            if (a == null) {
                return -1;
            }
            return a.compareTo(b);
        }

        @Override
        public String toString() {
            return format(this);
        }
    }
}
