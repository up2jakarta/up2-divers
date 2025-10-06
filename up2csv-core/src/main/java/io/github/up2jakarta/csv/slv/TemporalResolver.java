package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.ConversionResolver;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.cfg.Up2Temporal;
import io.github.up2jakarta.csv.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.time.*;
import java.time.temporal.Temporal;

@Named
@Singleton
public final class TemporalResolver extends ConversionResolver<Up2Temporal> {

    @Override
    public PropertyConverter<? extends Temporal> forParsing(Up2Temporal config, Field property) throws BeanException {
        final Class<?> fieldType = property.getType();
        if (fieldType == LocalTime.class) {
            return LocalTime::parse;
        }
        if (fieldType == LocalDate.class) {
            return LocalDate::parse;
        }
        if (fieldType == LocalDateTime.class) {
            return LocalDateTime::parse;
        }
        if (fieldType == OffsetTime.class) {
            return OffsetTime::parse;
        }
        if (fieldType == OffsetDateTime.class) {
            return OffsetDateTime::parse;
        }
        if (fieldType == ZonedDateTime.class) {
            return ZonedDateTime::parse;
        }
        if (fieldType == Year.class) {
            return Year::parse;
        }
        if (fieldType == YearMonth.class) {
            return Year::parse;
        }
        if (fieldType == Instant.class) {
            return Year::parse;
        }
        throw new BeanException(property, "must not be annotated by @Up2Temporal");
    }

}
