package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Up2OptionalDouble;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeFormatter;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.OptionalDouble;

@Named
@Singleton
public final class OptionalDoubleResolver implements TypeResolver<OptionalDouble, Up2OptionalDouble> {

    private static BigDecimal round(BigDecimal value, Up2OptionalDouble decimal) {
        if (value.scale() > decimal.value()) {
            return value.setScale(decimal.value(), decimal.roundingMode());
        }
        return value;
    }

    @Override
    public TypeAdapter<OptionalDouble> resolve(Field pf, Class<OptionalDouble> pt, Up2OptionalDouble pc) throws BeanException {
        final TypeConverter<OptionalDouble> parser = v -> {
            final BigDecimal decimal = round(new BigDecimal(v), pc);
            return OptionalDouble.of(decimal.doubleValue());
        };
        final TypeFormatter<OptionalDouble> format = v -> {
            if (v.isPresent()) {
                return round(BigDecimal.valueOf(v.getAsDouble()), pc).toString();
            }
            return null;
        };
        return new Up2Adapter<>(OptionalDouble.class, parser, format);
    }

}
