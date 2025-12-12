package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.bst.Cache;
import io.github.up2jakarta.lov.bst.WeakValueMap;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.function.Function;

import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static java.util.Comparator.comparing;

/**
 * Simple implementation of {@link Cache} based on binary-search tree with weak-reference values.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public final class WVCache<K, V> extends SafeCache<K, V> {

    public WVCache(Comparator<? super K> comparator) {
        super(new WeakValueMap<>(comparator));
    }

    public <T extends Comparable<T>> WVCache(Function<? super K, T> extractor) {
        this(comparing(notNull(extractor, Cache.class, "extractor")));
    }

    @Override
    public <R, X extends Throwable> R get(final K key, Resolver<V, V, X> resolver, Processor<K, V, R, X> processor) throws X {
        return super.compute(key, (m) -> {
            final Entry<K, V> entry = m.get(key, (k) -> resolver.get(null));
            final V oldValue = entry.getValue();
            final V newValue = resolver.get(oldValue);
            if (oldValue != newValue) {
                m.update(entry, newValue);
            }
            return processor.get(key, newValue);
        });
    }

    @Override
    public <X extends Throwable> V get(K key, Resolver<? super K, V, X> resolver) throws X {
        return super.compute(key, (m) -> {
            final Entry<K, V> entry = m.get(key, resolver::get);
            final V value = entry.getValue();
            if (value == null) {
                return m.update(entry, resolver.get(key)).getValue();
            }
            return value;
        });
    }

}
