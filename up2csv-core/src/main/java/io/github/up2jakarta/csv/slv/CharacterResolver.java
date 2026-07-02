package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2Character;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_CHARACTER;
import static io.github.up2jakarta.csv.core.ext.Beans.error;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

@Named
@Singleton
public final class CharacterResolver implements TypeResolver<Character, Up2Character> {

    @Override
    public TypeAdapter<Character> resolve(Field pf, Class<Character> pt, Up2Character pc) {
        if (pc.value()) {
            final Optional<Error> error = error(pf, pt);
            final SeverityType level = error.map(Error::level).orElse(ERROR);
            final String code = error.map(Error::value).orElse(EC_CHARACTER);
            final TypeConverter<Character> parser = v -> {
                if (v.length() != 1) {
                    throw new TypeException(level, code, "Invalid input [" + v + "] for Character");
                }
                return v.charAt(0);
            };
            return new Up2Adapter<>(Character.class, parser);
        }
        return new Up2Adapter<>(Character.class, v -> v.charAt(0));
    }

}
