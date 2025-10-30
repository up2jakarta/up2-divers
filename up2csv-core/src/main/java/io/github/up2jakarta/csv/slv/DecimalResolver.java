package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.api.ext.PropertyFormatter;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigDecimal;

@Named
@Singleton
public final class DecimalResolver extends ConversionResolver<Up2Decimal> {

    private static BigDecimal round(BigDecimal value, Up2Decimal decimal) {
        if (value.scale() > decimal.value()) {
            return value.setScale(decimal.value(), decimal.roundingMode());
        }
        return value;
    }

    @Override
    public PropertyConverter<? extends Number> forParsing(Up2Decimal config, Field property, Class<?> type) throws BeanException {
        if (type == BigDecimal.class) {
            return v -> round(new BigDecimal(v), config);
        }
        if (type == Double.class || type == double.class) {
            return v -> round(new BigDecimal(v), config).doubleValue();
        }
        if (type == Float.class || type == float.class) {
            return v -> round(new BigDecimal(v), config).floatValue();
        }
        throw new BeanException(property, "must not be annotated by @Up2Decimal");
    }

    @Override
    public PropertyFormatter<? extends Number> forFormatting(Up2Decimal config, Field property, Class<?> type) {
        return v -> {
            final BigDecimal n = switch (v) {
                case BigDecimal bd -> bd;
                case Double d -> new BigDecimal(d);
                case Float f -> new BigDecimal(f);
                default -> new BigDecimal(v.toString());
            };
            return round(n, config).toString();
        };
    }

}
