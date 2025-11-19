package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2Boolean;
import io.github.up2jakarta.lov.*;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeWrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_BOOLEAN;
import static io.github.up2jakarta.csv.core.ext.Beans.error;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

@Named
@Singleton
public final class BooleanResolver extends TypeResolver<Up2Boolean> {

    @Override
    public TypeAdapter<Boolean> resolve(Field property, Class<?> type, Up2Boolean config) throws BeanException {
        if (type == Boolean.class || type == boolean.class) {
            final Optional<Error> error = error(property, type);
            final SeverityType level = error.map(Error::level).orElse(ERROR);
            final String code = error.map(Error::value).orElse(EC_BOOLEAN);
            final String trueValue = config.trueValue();
            final String falseValue = config.falseValue();
            final PropertyConverter<Boolean> parser = (v) -> {
                if (trueValue.equals(v)) {
                    return true;
                }
                if (falseValue.equals(v)) {
                    return false;
                }
                throw new PropertyException(level, code, "Unknown value [" + v + "] for Boolean");
            };
            final PropertyFormatter<Boolean> format = (v) -> (v) ? trueValue : falseValue;
            return new TypeWrapper<>(Boolean.class, parser, format);
        }
        throw new BeanException(property, "must not be annotated by @Up2Boolean");
    }

}
