package io.github.up2jakarta.job.core;

import java.io.Closeable;
import java.io.Flushable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public interface SafeUtil {

    static void close(List<?> resources) {
        for (var resource : resources) {
            if (resource instanceof Flushable io) {
                safe(io::flush);
            }
            if (resource instanceof Closeable io) {
                safe(io::close);
            }
        }
    }

    static <V> void safe(SafeWrapper<RuntimeException> wrapper, Consumer<V> operator, V argument) {
        try {
            operator.accept(argument);
        } catch (RuntimeException ex) {
            wrapper.accept(ex);
        }
    }

    static <R, V> R safe(SafeWrapper<RuntimeException> wrapper, R defaultValue, Function<V, R> operator, V argument) {
        try {
            return operator.apply(argument);
        } catch (RuntimeException ex) {
            wrapper.accept(ex);
            return defaultValue;
        }
    }

    static SafeTranslator<?> safe(SafeTranslator<?> translator, Callable operator) {
        try {
            operator.apply();
        } catch (Exception ex) {
            translator.accept(ex);
        }
        return translator;
    }

    static SafeTranslator<?> safe(SafeTranslator<?> translator, Callable... operators) {
        for (final Callable operator : operators) {
            safe(translator, operator);
        }
        return translator;
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
