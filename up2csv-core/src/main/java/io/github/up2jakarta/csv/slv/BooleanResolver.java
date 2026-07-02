package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2Boolean;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.*;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_BOOLEAN;
import static io.github.up2jakarta.csv.core.ext.Beans.error;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

@Named
@Singleton
public final class BooleanResolver implements TypeResolver<Boolean, Up2Boolean> {

    @Override
    public TypeAdapter<Boolean> resolve(Field pf, Class<Boolean> pt, Up2Boolean pc) {
        final Optional<Error> error = error(pf, pt);
        final SeverityType level = error.map(Error::level).orElse(ERROR);
        final String code = error.map(Error::value).orElse(EC_BOOLEAN);
        final String trueValue = pc.trueValue();
        final String falseValue = pc.falseValue();
        final TypeConverter<Boolean> parser = v -> {
            if (trueValue.equals(v)) {
                return true;
            }
            if (falseValue.equals(v)) {
                return false;
            }
            throw new TypeException(level, code, "Unknown input [" + v + "] for Boolean");
        };
        final TypeFormatter<Boolean> format = v -> (v) ? trueValue : falseValue;
        return new Up2Adapter<>(Boolean.class, parser, format);
    }

}
