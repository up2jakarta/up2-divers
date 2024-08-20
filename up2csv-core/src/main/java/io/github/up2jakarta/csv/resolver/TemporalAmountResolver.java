package io.github.up2jakarta.csv.resolver;

import io.github.up2jakarta.csv.annotation.Up2TemporalAmount;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionResolver;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

@Named
@Singleton
public final class TemporalAmountResolver extends ConversionResolver<Up2TemporalAmount> {

    @Override
    public Conversion<? extends TemporalAmount> resolve(Up2TemporalAmount config, Field property) throws BeanException {
        final Class<?> fieldType = property.getType();
        if (fieldType == Period.class) {
            return Period::parse;
        }
        if (fieldType == Duration.class) {
            return Duration::parse;
        }
        throw new BeanException(property, "must not be annotated by @Up2TemporalAmount");
    }
}
