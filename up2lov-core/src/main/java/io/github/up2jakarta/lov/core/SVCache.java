package io.github.up2jakarta.lov.core;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Map.Entry;

import static io.github.up2jakarta.lov.core.Cache.InMemory;
import static io.github.up2jakarta.lov.core.SVCache.SoftValue;

/**
 * Simple implementation of {@link Cache} based on binary-search tree with soft-reference values.
 * <p>
 * Note that the comparison between keys is done with <code>==</code> not {@link Object#equals(Object)}.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public final class SVCache<K extends Comparable<K>, V> extends InMemory<K, V, V, K, SoftValue<V>> {

    public SVCache() {
        super(Entry::getValue, safe(Comparable::compareTo));
    }

    private V replace(Map<K, SoftValue<V>> map, ReferenceQueue<V> queue, K key, SoftValue<V> reference, V value) {
        if (value != null && !reference.refersTo(value)) {
            map.replace(key, reference, new SoftValue<>(value, queue));
        }
        return value;
    }

    private V put(Map<K, SoftValue<V>> map, ReferenceQueue<V> queue, K key, V value) {
        if (value != null) {
            map.put(key, new SoftValue<>(value, queue));
        }
        return value;
    }

    @Override
    public <X extends Throwable> V get(K key, Processor<K, V, V, X> processor) throws X {
        return super.compute(key, (m, q) -> {
            final SoftValue<V> reference = m.get(key);
            if (reference != null) {
                return replace(m, q, key, reference, processor.get(key, reference.get()));
            }
            return put(m, q, key, processor.get(key, null));
        });
    }

    @Override
    public <X extends Throwable> V get(K key, Resolver<? super K, V, X> resolver) throws X {
        return super.compute(key, (m, q) -> {
            final SoftValue<V> reference = m.get(key);
            if (reference == null) {
                return put(m, q, key, resolver.get(key));
            }
            final V value = reference.get();
            if (value == null) {
                return replace(m, q, key, reference, resolver.get(key));
            }
            return value;
        });
    }

    static class SoftValue<V> extends SoftReference<V> {
        private SoftValue(V value, ReferenceQueue<V> queue) {
            super(value, queue);
        }

        @Override
        public String toString() {
            return format(this);
        }
    }
}
