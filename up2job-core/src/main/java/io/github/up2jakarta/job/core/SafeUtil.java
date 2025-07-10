package io.github.up2jakarta.job.core;

@SuppressWarnings("unused")
public interface SafeUtil {

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
