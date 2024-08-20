package io.github.up2jakarta.csv.resolver;

import io.github.up2jakarta.csv.annotation.Up2Number;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionResolver;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigInteger;

@Named
@Singleton
public final class NumberResolver extends ConversionResolver<Up2Number> {

    @Override
    public Conversion<? extends Number> resolve(Up2Number config, Field property) throws BeanException {
        final Class<?> fieldType = property.getType();
        if (fieldType == BigInteger.class) {
            return BigInteger::new;
        }
        if (fieldType == Integer.class || fieldType == int.class) {
            return Integer::parseInt;
        }
        if (fieldType == Long.class || fieldType == long.class) {
            return Long::parseLong;
        }
        if (fieldType == Short.class || fieldType == short.class) {
            return Short::parseShort;
        }
        if (fieldType == Byte.class || fieldType == byte.class) {
            return Byte::parseByte;
        }
        throw new BeanException(property, "must not be annotated by @Up2Number");
    }
}
