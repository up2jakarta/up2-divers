package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.bst.Cache;
import io.github.up2jakarta.lov.bst.WeakKeyMap;

import java.util.Map.Entry;

import static java.util.Optional.ofNullable;

/**
 * Simple implementation of {@link Cache} based on binary-search tree with weak-reference keys.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public final class WKCache<K extends Comparable<K>, V> extends SafeCache<K, V> {

    public WKCache() {
        super(new WeakKeyMap<>(Comparable::compareTo));
    }

    @Override
    public <R, X extends Throwable> R get(final K key, Resolver<V, V, X> resolver, Processor<K, V, R, X> processor) throws X {
        return super.compute(key, (m) -> {
            final Entry<K, V> entry = m.get(key, (k) -> null);
            final K oldKey = ofNullable(entry.getKey()).orElse(key);
            final V oldValue = entry.getValue();
            final V newValue = resolver.get(oldValue);
            if (oldValue != newValue) {
                m.update(entry, newValue);
            }
            return processor.get(oldKey, newValue);
        });
    }

    @Override
    public <X extends Throwable> V get(K key, Resolver<? super K, V, X> resolver) throws X {
        return super.compute(key, (m) -> m.get(key, resolver::get).getValue());
    }
}
