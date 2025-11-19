package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.lov.CodeList;

/**
 * Contract interface for business data types.
 *
 * @param <T> the concrete data-type
 * @see io.github.up2jakarta.csv.api.IEvent#getType()
 */
public interface DataType<T extends DataType<T>> extends CodeList<T> {

    int N = Integer.MAX_VALUE;
    String DETACHED = "must not be detached";

    static String message(DataType<?> bg) {
        if (bg.getMax() == N) {
            return "cardinality must be greater than or equal to " + bg.getMin();
        } else if (bg.getMin() == 1 && bg.getMax() == 1) {
            return "cardinality must be 1 and only one";
        }
        return "cardinality must be between " + bg.getMin() + " and " + bg.getMax();
    }

    static boolean isValid(DataType<?> bg, int size) {
        return bg.getMin() <= size && size <= bg.getMax();
    }

    /**
     * @return the minimum of cardinality.
     */
    int getMin();

    /**
     * @return the maximum of cardinality.
     */
    int getMax();

}
