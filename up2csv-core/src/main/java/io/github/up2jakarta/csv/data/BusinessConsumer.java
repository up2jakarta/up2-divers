package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IType;
import jakarta.validation.constraints.NotNull;

@FunctionalInterface
public interface BusinessConsumer<I extends Enum<I> & IType<?, I>> {

    void accept(@NotNull Segment source, @NotNull I type, @NotNull String[] data);

}
