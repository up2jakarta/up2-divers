package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.lov.CodeList;

/**
 * Contract interface for business term that ensures identification of each data by business users.
 *
 * @param <T> the concrete term implementation
 * @see io.github.up2jakarta.csv.api.IEvent#getType()
 * @see TermResolver
 */
public interface ITerm<T extends ITerm<T>> extends CodeList<T> {

}
