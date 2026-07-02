package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Business result creator that creates the wrapped result with the parsed bean and its related events.
 *
 * @param <T> the business-creator type
 * @param <I> the business-object type
 * @param <E> the event type
 * @see Up2Result
 */
@FunctionalInterface
public interface BusinessCreator<T, I extends Segment, E extends IEvent<?>> extends BiFunction<I, List<E>, T> {

}
