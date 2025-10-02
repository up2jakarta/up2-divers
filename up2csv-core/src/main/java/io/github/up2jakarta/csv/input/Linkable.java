package io.github.up2jakarta.csv.input;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.extension.Parsed;

public interface Linkable<B extends DataType<B>, I extends Linkable<B, I>> extends InputType<B, I> {

    @Override
    @SuppressWarnings("unchecked")
    default InputJoiner<?, ?> joiner() {
        return this.linker();
    }

    <C extends Parsed<I, ?>, P extends Parsed<I, ?>> InputLinker<I, C, P> linker();

}
