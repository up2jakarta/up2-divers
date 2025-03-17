package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.extension.Parsed;
import io.github.up2jakarta.csv.input.InputError;

import java.util.List;
import java.util.function.BiFunction;

@FunctionalInterface
public interface BusinessCreator<T, I extends Parsed<?, ?>, E extends InputError<?, ?, ?>> extends BiFunction<I, List<E>, T> {

}
