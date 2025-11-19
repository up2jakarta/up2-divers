package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.lov.PropertyConverter;
import io.github.up2jakarta.lov.PropertyFormatter;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeWrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Named
@Singleton
public final class DecimalResolver extends TypeResolver<Up2Decimal> {

    private static BigDecimal round(BigDecimal value, Up2Decimal decimal) {
        if (value.scale() > decimal.value()) {
            return value.setScale(decimal.value(), decimal.roundingMode());
        }
        return value;
    }

    @Override
    public TypeAdapter<? extends Number> resolve(Field property, Class<?> type, Up2Decimal config) throws BeanException {
        if (type == BigDecimal.class) {
            final PropertyConverter<BigDecimal> parser = (v) -> round(new BigDecimal(v), config);
            final PropertyFormatter<BigDecimal> format = (v) -> round(v, config).toString();
            return new TypeWrapper<>(BigDecimal.class, parser, format);
        }
        if (type == Double.class || type == double.class) {
            final PropertyConverter<Double> parser = (v) -> round(new BigDecimal(v), config).doubleValue();
            final PropertyFormatter<Double> format = (v) -> round(valueOf(v), config).toString();
            return new TypeWrapper<>(Double.class, parser, format);
        }
        if (type == Float.class || type == float.class) {
            final PropertyConverter<Float> parser = (v) -> round(new BigDecimal(v), config).floatValue();
            final PropertyFormatter<Float> format = (v) -> round(valueOf(v), config).toString();
            return new TypeWrapper<>(Float.class, parser, format);
        }
        throw new BeanException(property, "must not be annotated by @Up2Decimal");
    }

}
