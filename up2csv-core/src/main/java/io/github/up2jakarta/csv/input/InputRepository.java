package io.github.up2jakarta.csv.input;

/**
 * Contract interface for input repository that is able to count the errors related to the given input row
 * before processing data, helpful for well computing {@link InputError.Key#getKey()}
 *
 * @param <R> the input row type
 */
public interface InputRepository<R extends InputSegment> {

    /**
     * Get and return the max key-order of existing errors related to the given input row.
     *
     * @param row the input row
     * @return the max of used key-order
     */
    int max(R row);

}
