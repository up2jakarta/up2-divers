package io.github.up2jakarta.job.core;

import java.util.function.Function;

@FunctionalInterface
public interface BusinessGenerator<B extends BusinessType<B>, T extends BusinessObject<B, ?>> extends Function<T, byte[]> {

}
