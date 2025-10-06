package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.data.DataType;

/**
 * Contact interface for an input error.
 *
 * @param <D> the input row type
 */
public interface IError<D extends DataType<D>> extends io.github.up2jakarta.xml.api.IError {

    /**
     * @return the data offset of the input record
     */
    Integer getOffset();

    /**
     * @return the business data type
     */
    D getType();

}
