package io.github.up2jakarta.csv.api.hdl;

/**
 * Contract interface for input repository that is able to count the errors related to the given input row
 * before processing data, helpful for well computing {@link IFullError.IKey#getKey()}
 *
 * @param <R> the input record type
 */
@FunctionalInterface
public interface IFullRepository<R extends IFullRecord<?, ?, ?>> {

    /**
     * Get and return the max key-order of existing errors related to the given input row.
     *
     * @param row the input row
     * @return the max of used key-order
     */
    int max(R row);

}
