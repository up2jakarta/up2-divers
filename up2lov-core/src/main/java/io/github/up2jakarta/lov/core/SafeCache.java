package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.bst.Cache;

import java.util.concurrent.locks.ReentrantLock;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Internal Synchronized Cache.
 */
abstract sealed class SafeCache<K, V> extends Cache<K, V> permits WKCache, WVCache, SVCache {

    private final ReentrantLock mutex = new ReentrantLock();
    private final Region<K, V> delegate;

    SafeCache(Region<K, V> delegate) {
        this.delegate = delegate;
    }

    final <R, X extends Throwable> R compute(K key, Resolver<Region<K, V>, R, X> computer) throws X {
        notNull(key, Cache.class, "key");
        mutex.lock();
        try {
            delegate.evict();
            return computer.get(delegate);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public final int size() {
        return delegate.size();
    }

}
