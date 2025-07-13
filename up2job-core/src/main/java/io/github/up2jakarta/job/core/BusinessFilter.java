package io.github.up2jakarta.job.core;

import java.util.function.Predicate;

@FunctionalInterface
public interface BusinessFilter<B extends BusinessType<B>, T extends BusinessObject<B, ?>> extends Predicate<T> {

}
