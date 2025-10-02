package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.input.InputType;
import jakarta.validation.constraints.NotNull;

@FunctionalInterface
public interface BusinessConsumer<I extends Enum<I> & InputType<?, I>> {

    void accept(@NotNull Segment source, @NotNull I type, @NotNull String[] data);

}
