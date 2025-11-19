package io.github.up2jakarta.csv.api.hdl;

import java.util.function.Supplier;

/**
 * {@link Supplier} for error-code, useful for lazy loading.
 */
@FunctionalInterface
public interface EventCode extends Supplier<String> {
}
