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
    public PropertyConverter<? extends Temporal> forParsing(Up2Temporal config, Field property, Class<?> type) throws BeanException {
        if (type == LocalTime.class) {
            return LocalTime::parse;
        }
        if (type == LocalDate.class) {
            return LocalDate::parse;
        }
        if (type == LocalDateTime.class) {
            return LocalDateTime::parse;
        }
        if (type == OffsetTime.class) {
            return OffsetTime::parse;
        }
        if (type == OffsetDateTime.class) {
            return OffsetDateTime::parse;
        }
        if (type == ZonedDateTime.class) {
            return ZonedDateTime::parse;
        }
        if (type == Year.class) {
            return Year::parse;
        }
        if (type == YearMonth.class) {
            return Year::parse;
        }
        if (type == Instant.class) {
            return Year::parse;
        }
        throw new BeanException(property, "must not be annotated by @Up2Temporal");
    }

}
