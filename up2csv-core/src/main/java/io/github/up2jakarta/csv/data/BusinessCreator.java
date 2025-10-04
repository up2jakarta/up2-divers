package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IError;

import java.util.List;
import java.util.function.BiFunction;

@FunctionalInterface
public interface BusinessCreator<T, I extends Recordable<?, ?>, E extends IError<?, ?, ?>> extends BiFunction<I, List<E>, T> {

}
