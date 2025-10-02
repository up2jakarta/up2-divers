package io.github.up2jakarta.csv.resolver;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Up2Boolean;
import io.github.up2jakarta.csv.extension.ConversionResolver;
import io.github.up2jakarta.csv.extension.PropertyFormatter;
import io.github.up2jakarta.csv.extension.PropertyParser;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.PropertyException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.Optional;

@Named
@Singleton
public final class BooleanResolver extends ConversionResolver<Up2Boolean> {

    @Override
    public PropertyParser<? extends Boolean> forParsing(Up2Boolean config, Field property) throws BeanException {
        final Class<?> fieldType = property.getType();
        if (fieldType == Boolean.class || fieldType == boolean.class) {
            return v -> {
                if (config.trueValue().equals(v)) {
                    return true;
                }
                if (config.falseValue().equals(v)) {
                    return false;
                }
                final Optional<Error> error = getError(property);
                final SeverityType type = error.map(Error::severity).orElse(SeverityType.ERROR);
                final String code = error.map(Error::value).orElse(Errors.ERROR_BOOLEAN);
                throw new PropertyException(type, code, "Unknown value [" + v + "] for Boolean");
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
