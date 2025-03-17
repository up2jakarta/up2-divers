package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.extension.CodeList;
import io.github.up2jakarta.csv.extension.DataType;
import io.github.up2jakarta.csv.extension.Parsed;

/**
 * Contact marker for the type of input-type.
 */
public interface InputType<T extends InputType<T>> extends CodeList<T> {

    DataType<?> getGroupType();

    String getErrorCode();

    InputLinker<T, ? extends Parsed<T, ?>, ? extends Parsed<T, ?>> getLinker();

    default boolean holds(T type) {
        return type != this && type.getLinker().getParentType() == getLinker().getType();
    }

}
