package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.SimpleResolver;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeSupport;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigInteger;

@Named
@Singleton
public final class NumberResolver extends SimpleResolver<Number, Up2Number> {

    @Override
    protected TypeAdapter<? extends Number> resolve(Field pf, Class<Number> pt) throws BeanException {
        if (BigInteger.class.equals(pt)) {
            return new TypeSupport<>(BigInteger.class, BigInteger::new);
        }
        if (Integer.class.equals(pt) || int.class.equals(pt)) {
            return new TypeSupport<>(Integer.class, Integer::parseInt);
        }
        if (Long.class.equals(pt) || long.class.equals(pt)) {
            return new TypeSupport<>(Long.class, Long::parseLong);
        }
        if (Short.class.equals(pt) || short.class.equals(pt)) {
            return new TypeSupport<>(Short.class, Short::parseShort);
        }
        if (Byte.class.equals(pt) || byte.class.equals(pt)) {
            return new TypeSupport<>(Byte.class, Byte::parseByte);
        }
        throw new BeanException(pf, "must not be annotated with @Up2Number");
    }

}
