package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Up2Base64;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Base64;

@Named
@Singleton
public final class Base64Resolver implements TypeResolver<byte[], Up2Base64> {

    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @Override
    public TypeAdapter<byte[]> resolve(Field pf, Class<byte[]> pt, Up2Base64 pc) {
        final Charset charset = Charset.forName(pc.encoding());
        final TypeConverter<byte[]> parser = v -> DECODER.decode(v.getBytes(charset));
        return new Up2Adapter<>(byte[].class, parser, ENCODER::encodeToString);
    }
}
