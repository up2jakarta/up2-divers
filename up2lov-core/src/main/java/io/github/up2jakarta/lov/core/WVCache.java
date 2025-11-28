package io.github.up2jakarta.lov.core;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static io.github.up2jakarta.lov.core.Cache.InMemory;
import static io.github.up2jakarta.lov.core.WVCache.WeakValue;
import static java.util.Comparator.comparing;

/**
 * Simple implementation of {@link Cache} based on binary-search tree with weak-reference values.
 * <p>
 * Note that the comparison between keys is done with <code>==</code> not {@link Object#equals(Object)}.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public final class WVCache<K, V> extends InMemory<K, V, V, K, WeakValue<V>> {

    public WVCache(Comparator<? super K> comparator) {
        super(Entry::getValue, safe(comparator));
    }

    public <T extends Comparable<T>> WVCache(Function<? super K, T> extractor) {
        this(comparing(notNull(extractor, Cache.class, "extractor")));
    }

    private V replace(Map<K, WeakValue<V>> map, ReferenceQueue<V> queue, K key, WeakValue<V> reference, V value) {
        if (value != null && !reference.refersTo(value)) {
            map.replace(key, reference, new WeakValue<>(value, queue));
        }
        return value;
    }

    private V put(Map<K, WeakValue<V>> map, ReferenceQueue<V> queue, K key, V value) {
        if (value != null) {
            map.put(key, new WeakValue<>(value, queue));
        }
        return value;
    }

    @Override
    public <X extends Throwable> V get(K key, Processor<K, V, V, X> processor) throws X {
        return super.compute(key, (m, q) -> {
            final WeakValue<V> reference = m.get(key);
            if (reference != null) {
                return this.replace(m, q, key, reference, processor.get(key, reference.get()));
            }
            return this.put(m, q, key, processor.get(key, null));
        });
    }

    @Override
    public <X extends Throwable> V get(K key, Resolver<? super K, V, X> resolver) throws X {
        return super.compute(key, (m, q) -> {
            final WeakValue<V> reference = m.get(key);
            if (reference == null) {
                return this.put(m, q, key, resolver.get(key));
            }
            final V value = reference.get();
            if (value == null) {
                return this.replace(m, q, key, reference, resolver.get(key));
            }
            return value;
        });
    }

    static class WeakValue<V> extends WeakReference<V> {
        private WeakValue(V value, ReferenceQueue<V> queue) {
            super(value, queue);
        }

        @Override
        public String toString() {
            return format(this);
        }
    }
}
