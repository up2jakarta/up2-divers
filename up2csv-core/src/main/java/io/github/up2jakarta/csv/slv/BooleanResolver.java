package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.api.ext.PropertyFormatter;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2Boolean;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.EventHandler.ERROR_BOOLEAN;

@Named
@Singleton
public final class BooleanResolver extends ConversionResolver<Up2Boolean> {

    @Override
    public PropertyConverter<? extends Boolean> forParsing(Up2Boolean config, Field property, Class<?> type) throws BeanException {
        if (type == Boolean.class || type == boolean.class) {
            return v -> {
                if (config.trueValue().equals(v)) {
                    return true;
                }
                if (config.falseValue().equals(v)) {
                    return false;
                }
                final Optional<Error> error = getError(property);
                final SeverityType level = error.map(Error::severity).orElse(SeverityType.ERROR);
                final String code = error.map(Error::value).orElse(ERROR_BOOLEAN);
                throw new PropertyException(level, code, "Unknown value [" + v + "] for Boolean");
            };
        }
        throw new BeanException(property, "must not be annotated by @Up2Boolean");
    }

    @Override
    public PropertyFormatter<Boolean> forFormatting(Up2Boolean config, Field property) {
        return v -> {
            if (v) {
                return config.trueValue();
            }
            return config.falseValue();
        };
    }

}
