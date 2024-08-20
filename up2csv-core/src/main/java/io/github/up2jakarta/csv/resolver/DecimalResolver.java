package io.github.up2jakarta.csv.resolver;

import io.github.up2jakarta.csv.annotation.Up2Decimal;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionResolver;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigDecimal;

@Named
@Singleton
public final class DecimalResolver extends ConversionResolver<Up2Decimal> {

    public static BigDecimal parseDecimal(String value, Up2Decimal decimal) {
        final BigDecimal result = new BigDecimal(value);
        return result.setScale(decimal.value(), decimal.roundingMode());
    }

    @Override
    public Conversion<? extends Number> resolve(Up2Decimal config, Field property) throws BeanException {
        final Class<?> pType = property.getType();
        if (pType == BigDecimal.class) {
            return v -> parseDecimal(v, config);
        }
        if (pType == Double.class || pType == double.class) {
            return v -> parseDecimal(v, config).doubleValue();
        }
        if (pType == Float.class || pType == float.class) {
            return v -> parseDecimal(v, config).floatValue();
        }
        throw new BeanException(property, "must not be annotated by @Up2Decimal");
    }
}
