package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeWrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigInteger;

@Named
@Singleton
public final class NumberResolver extends TypeResolver<Up2Number> {

    @Override
    public TypeAdapter<? extends Number> resolve(Field property, Class<?> type, Up2Number config) throws BeanException {
        if (type == BigInteger.class) {
            return new TypeWrapper<>(BigInteger.class, BigInteger::new);
        }
        if (type == Integer.class || type == int.class) {
            return new TypeWrapper<>(Integer.class, Integer::parseInt);
        }
        if (type == Long.class || type == long.class) {
            return new TypeWrapper<>(Long.class, Long::parseLong);
        }
        if (type == Short.class || type == short.class) {
            return new TypeWrapper<>(Short.class, Short::parseShort);
        }
        if (type == Byte.class || type == byte.class) {
            return new TypeWrapper<>(Byte.class, Byte::parseByte);
        }
        throw new BeanException(property, "must not be annotated by @Up2Number");
    }

}
