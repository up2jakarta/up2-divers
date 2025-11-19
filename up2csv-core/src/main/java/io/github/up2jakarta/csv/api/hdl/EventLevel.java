package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.lov.SeverityType;

import java.util.function.Supplier;

/**
 * {@link Supplier} for error-level, useful for lazy loading.
 */
@FunctionalInterface
public interface EventLevel extends Supplier<SeverityType> {
}
