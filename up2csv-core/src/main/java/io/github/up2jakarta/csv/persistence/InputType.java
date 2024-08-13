package io.github.up2jakarta.csv.persistence;

import io.github.up2jakarta.csv.misc.CodeList;

/**
 * Contact marker for the type of input row.
 */
public interface InputType<T extends Enum<T>> extends CodeList<T> {

}
