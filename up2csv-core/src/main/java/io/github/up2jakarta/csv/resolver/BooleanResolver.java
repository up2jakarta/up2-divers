package io.github.up2jakarta.csv.resolver;

import io.github.up2jakarta.csv.annotation.Up2Boolean;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionResolver;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;

@Named
@Singleton
public final class BooleanResolver extends ConversionResolver<Up2Boolean> {

    @Override
    public Conversion<? extends Boolean> resolve(Up2Boolean config, Field property) throws BeanException {
        final Class<?> fieldType = property.getType();
        if (fieldType == Boolean.class || fieldType == boolean.class) {
            return config.value()::equals;
        }
        throw new BeanException(property, "must not be annotated by @Up2Boolean");
    }
}
