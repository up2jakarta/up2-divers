package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.xml.codelist.CodeList;

/**
 * Contact marker for the type of input-type.
 */
public interface InputType<B extends DataType<B>, T extends InputType<B, T>> extends CodeList<T> {

    default Class<? extends Segment> getClassType() {
        return this.joiner().getClassType();
    }

    B getBusinessType();

    String getErrorCode();

    <C extends Segment, P extends Segment> InputJoiner<C, P> joiner();

    default boolean holds(T type) {
        return type != this && type.joiner().getParentType() == this.joiner().getClassType();
    }

}
