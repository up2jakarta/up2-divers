package io.github.up2jakarta.csv.resolver;

import io.github.up2jakarta.csv.annotation.Up2Decimal;
import io.github.up2jakarta.csv.extension.ConversionResolver;
import io.github.up2jakarta.csv.extension.PropertyFormatter;
import io.github.up2jakarta.csv.extension.PropertyParser;
import io.github.up2jakarta.csv.misc.BeanException;
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
    public PropertyParser<? extends Number> forParsing(Up2Decimal config, Field property) throws BeanException {
        final Class<?> pType = property.getType();
        if (pType == BigDecimal.class) {
            return v -> round(new BigDecimal(v), config);
        }
        if (pType == Double.class || pType == double.class) {
            return v -> round(new BigDecimal(v), config).doubleValue();
        }
        if (pType == Float.class || pType == float.class) {
            return v -> round(new BigDecimal(v), config).floatValue();
        }
        throw new BeanException(property, "must not be annotated by @Up2Decimal");
    }

    @Override
    public PropertyFormatter<? extends Number> forFormatting(Up2Decimal config, Field property) {
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
