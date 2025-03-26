package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.xml.codelist.CodeList;

/**
 * Contract interface for business data types.
 *
 * @param <T> the concrete data-type
 * @see io.github.up2jakarta.csv.input.InputError#setType(DataType)
 */
public interface DataType<T extends DataType<T>> extends CodeList<T> {

    int N = Integer.MAX_VALUE;

    /**
     * @return the minimum of cardinality.
     */
    int getMin();

    /**
     * @return the maximum of cardinality.
     */
    int getMax();

    static String buildMessage(DataType<?> bg) {
        return "size must be between " + bg.getMin() + " and " + bg.getMax();
    }

    static boolean isValid(DataType<?> bg, int size) {
        return bg.getMin() <= size && size <= bg.getMax();
    }

}
