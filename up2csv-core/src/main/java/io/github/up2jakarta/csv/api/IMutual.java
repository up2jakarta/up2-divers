package io.github.up2jakarta.csv.api;

/**
 * Contact interface for mutual coupled types, useful for enforcing type checking.
 *
 * @param <F> the first type
 * @param <S> the second type
 */
public interface IMutual<F extends IMutual<S, F>, S extends IMutual<F, S>> {

}
