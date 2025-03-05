package io.github.up2jakarta.csv.resolver;

import io.github.up2jakarta.csv.annotation.Up2Base64;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionResolver;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.Base64;

@Named
@Singleton
public final class Base64Resolver extends ConversionResolver<Up2Base64> {

    private static final Base64.Decoder DECODER = Base64.getDecoder();

    @Override
    public Conversion<byte[]> resolve(Up2Base64 config, Field property) throws BeanException {
        final Class<?> pType = property.getType();
        if (pType == byte[].class) {
            return v -> DECODER.decode(v.getBytes(config.encoding()));
        }
        throw new BeanException(property, "must not be annotated by @Up2Base64");
    }

}
