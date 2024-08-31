package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.extension.CodeList;

/**
 * Contact marker for the type of input row.
 */
public interface InputType<T extends Enum<T> & InputType<T>> extends CodeList<T> {

}
