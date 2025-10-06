package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.api.ext.PropertyFormatter;
import io.github.up2jakarta.csv.cfg.Up2Base64;
import io.github.up2jakarta.csv.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.Base64;

@Named
@Singleton
public final class Base64Resolver extends ConversionResolver<Up2Base64> {

    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @Override
    public PropertyConverter<byte[]> forParsing(Up2Base64 config, Field property) throws BeanException {
        final Class<?> pType = property.getType();
        if (pType == byte[].class) {
            return v -> DECODER.decode(v.getBytes(config.encoding()));
        }
        throw new BeanException(property, "must not be annotated by @Up2Base64");
    }

    @Override
    public PropertyFormatter<byte[]> forFormatting(Up2Base64 config, Field property) {
        return ENCODER::encodeToString;
    }

}
