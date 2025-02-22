package io.github.up2jakarta.csv.extension;

/**
 * Contract interface for business data types.
 *
 * @param <T> the concrete data-type
 * @see io.github.up2jakarta.csv.input.InputError#setType(DataType)
 */
public interface DataType<T extends DataType<T>> extends CodeList<T> {

}
