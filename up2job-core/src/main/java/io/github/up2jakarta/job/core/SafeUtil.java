package io.github.up2jakarta.job.core;

import java.util.function.Function;

@SuppressWarnings("unused")
public interface SafeUtil {

    static void call(Function<Exception, ? extends RuntimeException> creator, Callable... ops) {
        RuntimeException error = null;
        for (final Callable operator : ops) {
            try {
                operator.apply();
            } catch (Exception ex) {
                if (error == null) {
                    error = creator.apply(ex);
                }
            }
        }
        if (error != null) {
            throw error;
        }
    }

    static void safe(Callable operator) {
        try {
            operator.apply();
        } catch (Exception ignore) {
        }
    }

    static <D> D safe(Producer<D> supplier, D defaultValue) {
        try {
            return supplier.get();
        } catch (Exception ignore) {
            return defaultValue;
        }
    }

    @FunctionalInterface
    interface Callable {
        void apply() throws Exception;
    }

    @FunctionalInterface
    interface Producer<T> {
        T get() throws Exception;
    }

}
