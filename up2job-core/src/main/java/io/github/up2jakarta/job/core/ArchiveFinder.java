package io.github.up2jakarta.job.core;

import java.util.function.BiFunction;

@FunctionalInterface
public interface ArchiveFinder<B extends BusinessType<B>> extends BiFunction<B, Long, Archive> {

}
