package io.github.up2jakarta.lov.core;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.logging.Logger;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Base Implementation of Memory Cache that supports full concurrency for retrievals and updates.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public abstract sealed class Cache<K, V> permits Cache.InMemory {

    /**
     * @see java.util.Map#computeIfAbsent(Object, java.util.function.Function)
     */
    public final <X extends Throwable> V get(K key, Computer<V, X> computer) throws X {
        return this.get(key, (k) -> computer.get());
    }

    /**
     * @see java.util.Map#computeIfAbsent(Object, java.util.function.Function)
     * @see java.util.Map#computeIfPresent(Object, java.util.function.BiFunction)
     * @see java.util.Map#replace(Object, Object)
     */
    public abstract <X extends Throwable> V get(K key, Processor<K, V, V, X> processor) throws X;

    /**
     * @see java.util.Map#computeIfAbsent(Object, java.util.function.Function)
     */
    public abstract <X extends Throwable> V get(K key, Resolver<? super K, V, X> computer) throws X;

    /**
     * @see java.util.Map#size()
     */
    public abstract int size();

    static abstract sealed class InMemory<K, V, W, T, E> extends Cache<K, V> permits WKCache, WVCache, SVCache {
        private static final Logger LOGGER = Logger.getLogger(Cache.class.getName());

        private final Function<Entry<T, E>, ? extends Reference<W>> reference;
        private final ReferenceQueue<W> queue;
        private final ReentrantLock mutex;
        private final Map<T, E> map;

        InMemory(Function<Entry<T, E>, ? extends Reference<W>> wre, Comparator<? super T> cmp) {
            this.queue = new ReferenceQueue<>();
            this.mutex = new ReentrantLock();
            this.map = new TreeMap<>(cmp);
            this.reference = wre;
        }

        static <C> Comparator<C> safe(Comparator<? super C> comparator) {
            notNull(comparator, Cache.class, "comparator");
            return (a, b) -> {
                if (a == b) {
                    return 0;
                } else if (a == null) {
                    return -1;
                } else if (b == null) {
                    return 1;
                }
                return comparator.compare(a, b);
            };
        }

        static <C> String format(Reference<C> reference) {
            final C value = reference.get();
            if (value == null) {
                return "?";
            }
            return value.toString();
        }

        private void clean() {
            var run = false;
            while (queue.poll() != null) {
                run = true;
            }
            if (run) {
                final int size = this.size();
                map.entrySet().removeIf(e -> reference.apply(e).refersTo(null));
                LOGGER.fine(() -> "Evicting " + (size - this.size()) + "/" + size + " entries");
            }
        }

        final <X extends Throwable> V compute(K key, Processor<Map<T, E>, ReferenceQueue<W>, V, X> computer) throws X {
            notNull(key, Cache.class, "key");
            mutex.lock();
            try {
                this.clean();
                return computer.get(map, queue);
            } finally {
                mutex.unlock();
            }
        }

        @Override
        public final int size() {
            return map.size();
        }
    }

}
