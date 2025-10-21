package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;
import io.github.up2jakarta.csv.core.BeanException;
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
    public PropertyConverter<? extends TemporalAmount> forParsing(Up2TemporalAmount config, Field property, Class<?> type) throws BeanException {
        if (type == Period.class) {
            return Period::parse;
        }
        if (type == Duration.class) {
            return Duration::parse;
        }
        throw new BeanException(property, "must not be annotated by @Up2TemporalAmount");
    }

}
