package io.github.up2jakarta.lov.bst;

import io.github.up2jakarta.lov.core.Computer;
import io.github.up2jakarta.lov.core.Processor;
import io.github.up2jakarta.lov.core.Resolver;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.function.Consumer;

/**
 * Base Implementation of Memory Cache that supports full concurrency for retrievals and updates.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public abstract class Cache<K, V> {

    /**
     * Evicts the cache entries from the specified reference queue.
     *
     * @param queue  the queue of cache entries
     * @param action the remove action
     * @param <E>    the cache entry type
     */
    protected static <E> void evict(ReferenceQueue<?> queue, Consumer<E> action) {
        for (Object expired; (expired = queue.poll()) != null; ) {
            //noinspection unchecked
            action.accept((E) expired);
        }
    }

    /**
     * Formats the specified reference to {@link String} value.
     *
     * @param reference the value reference
     * @return the formatted value
     */
    protected static String format(Reference<?> reference) {
        final Object value = reference.get();
        if (value == null) {
            return "?";
        }
        return value.toString();
    }

    /**
     * Returns the cached value of the specified key if exists or else caches and returns the computed value.
     *
     * @param key      the cache key
     * @param computer the value computer
     * @param <X>      the computer exception type
     * @return the value related to the specified key
     * @throws X if the computer throws {@link X}
     */
    public final <X extends Throwable> V get(K key, Computer<V, X> computer) throws X {
        return this.get(key, k -> computer.get());
    }

    /**
     * Processes and returns the expected result {@link R} of the cached value of the specified key.
     *
     * @param key       the cache key
     * @param resolver  the value resolver
     * @param processor the value processor
     * @param <X>       the resolver/processor exception type
     * @return the result related to the specified key
     * @throws X if the resolver/processor throws {@link X}
     */
    public abstract <R, X extends Throwable> R get(K key, Resolver<V, V, X> resolver, Processor<K, V, R, X> processor) throws X;

    /**
     * Returns the cached value of the specified key if exists or else caches and returns the computed value.
     *
     * @param key      the cache key
     * @param resolver the value resolver
     * @param <X>      the resolver exception type
     * @return the value related to the specified key
     * @throws X if the resolver throws {@link X}
     */
    public abstract <X extends Throwable> V get(K key, Resolver<? super K, V, X> resolver) throws X;

    /**
     * @return the cache size
     */
    public abstract int size();

}
