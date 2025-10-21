package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigInteger;

@Named
@Singleton
public final class NumberResolver extends ConversionResolver<Up2Number> {

    @Override
    public PropertyConverter<? extends Number> forParsing(Up2Number config, Field property, Class<?> type) throws BeanException {
        if (type == BigInteger.class) {
            return BigInteger::new;
        }
        if (type == Integer.class || type == int.class) {
            return Integer::parseInt;
        }
        if (type == Long.class || type == long.class) {
            return Long::parseLong;
        }
        if (type == Short.class || type == short.class) {
            return Short::parseShort;
        }
        if (type == Byte.class || type == byte.class) {
            return Byte::parseByte;
        }
        throw new BeanException(property, "must not be annotated by @Up2Number");
    }

}
