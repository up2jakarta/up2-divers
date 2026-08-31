package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.UnwrapByDefault;
import jakarta.validation.valueextraction.ValueExtractor;

@Named
@Singleton
@UnwrapByDefault
public class WrapperExtractor implements ValueExtractor<Wrapper<@ExtractedValue ?>> {

    @Override
    public void extractValues(Wrapper<?> value, ValueReceiver receiver) {
        receiver.value(null, value.get());
    }

}
