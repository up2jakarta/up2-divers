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
import java.util.OptionalLong;

@Named
@Singleton
public final class OptionalLongResolver extends SimpleResolver<OptionalLong, Up2OptionalInt> {

    @Override
    protected TypeAdapter<OptionalLong> resolve(Field pf, Class<OptionalLong> pt) throws BeanException {
        final TypeConverter<OptionalLong> parser = v -> {
            final int number = Integer.parseInt(v);
            return OptionalLong.of(number);
        };
        final TypeFormatter<OptionalLong> format = v -> {
            if (v.isPresent()) {
                return String.valueOf(v.getAsLong());
            }
            return null;
        };
        return new Up2Adapter<>(OptionalLong.class, parser, format);
    }

}
