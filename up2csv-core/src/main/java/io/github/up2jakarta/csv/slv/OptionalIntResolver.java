package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.SimpleResolver;
import io.github.up2jakarta.csv.cfg.Up2OptionalInt;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeFormatter;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.OptionalInt;

@Named
@Singleton
public final class OptionalIntResolver extends SimpleResolver<OptionalInt, Up2OptionalInt> {

    @Override
    protected TypeAdapter<OptionalInt> resolve(Field pf, Class<OptionalInt> pt) throws BeanException {
        final TypeConverter<OptionalInt> parser = v -> {
            final int number = Integer.parseInt(v);
            return OptionalInt.of(number);
        };
        final TypeFormatter<OptionalInt> format = v -> {
            if (v.isPresent()) {
                return String.valueOf(v.getAsInt());
            }
            return null;
        };
        return new Up2Adapter<>(OptionalInt.class, parser, format);
    }

}
