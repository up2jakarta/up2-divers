package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IEvent;

import java.util.List;
import java.util.function.BiFunction;

@FunctionalInterface
public interface BusinessCreator<T, I extends Segment, E extends IEvent<?>> extends BiFunction<I, List<E>, T> {

}
