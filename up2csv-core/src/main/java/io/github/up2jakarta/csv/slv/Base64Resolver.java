package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2Base64;
import io.github.up2jakarta.lov.PropertyConverter;
import io.github.up2jakarta.lov.PropertyException;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeWrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_BASE_64;
import static io.github.up2jakarta.csv.core.ext.Beans.error;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

@Named
@Singleton
public final class Base64Resolver extends TypeResolver<Up2Base64> {

    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @Override
    public TypeAdapter<byte[]> resolve(Field property, Class<?> type, Up2Base64 config) throws BeanException {
        if (type == byte[].class) {
            final Optional<Error> error = error(property, type);
            final SeverityType level = error.map(Error::level).orElse(ERROR);
            final String code = error.map(Error::value).orElse(EC_BASE_64);
            final Charset charset = Charset.forName(config.encoding());
            final PropertyConverter<byte[]> parser = v -> {
                try {
                    return DECODER.decode(v.getBytes(charset));
                } catch (Exception cause) {
                    throw new PropertyException(level, code, cause.getMessage(), cause);
                }
            };
            return new TypeWrapper<>(byte[].class, parser, ENCODER::encodeToString);
        }
        throw new BeanException(property, "must not be annotated by @Up2Base64");
    }
}
