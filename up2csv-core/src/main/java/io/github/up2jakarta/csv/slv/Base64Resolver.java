package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2Base64;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeSupport;
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
public final class Base64Resolver implements TypeResolver<byte[], Up2Base64> {

    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @Override
    public TypeAdapter<byte[]> resolve(Field pf, Class<byte[]> pt, Up2Base64 pc) throws BeanException {
        final Optional<Error> error = error(pf, pt);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_BASE_64);
        final Charset charset = Charset.forName(pc.encoding());
        final TypeConverter<byte[]> parser = v -> {
            try {
                return DECODER.decode(v.getBytes(charset));
            } catch (Exception cause) {
                throw new TypeException(level, code, cause.getMessage(), cause);
            }
        };
        return new TypeSupport<>(byte[].class, parser, ENCODER::encodeToString);
    }
}
