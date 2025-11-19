package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Up2Temporal;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeWrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.time.*;
import java.time.temporal.Temporal;

@Named
@Singleton
public final class TemporalResolver extends TypeResolver<Up2Temporal> {

    @Override
    public TypeAdapter<? extends Temporal> resolve(Field property, Class<?> type, Up2Temporal config) throws BeanException {
        if (type == LocalTime.class) {
            return new TypeWrapper<>(LocalTime.class, LocalTime::parse);
        }
        if (type == LocalDate.class) {
            return new TypeWrapper<>(LocalDate.class, LocalDate::parse);
        }
        if (type == LocalDateTime.class) {
            return new TypeWrapper<>(LocalDateTime.class, LocalDateTime::parse);
        }
        if (type == OffsetTime.class) {
            return new TypeWrapper<>(OffsetTime.class, OffsetTime::parse);
        }
        if (type == OffsetDateTime.class) {
            return new TypeWrapper<>(OffsetDateTime.class, OffsetDateTime::parse);
        }
        if (type == ZonedDateTime.class) {
            return new TypeWrapper<>(ZonedDateTime.class, ZonedDateTime::parse);
        }
        if (type == Instant.class) {
            return new TypeWrapper<>(Instant.class, Instant::parse);
        }
        if (type == YearMonth.class) {
            return new TypeWrapper<>(YearMonth.class, YearMonth::parse);
        }
        if (type == Year.class) {
            return new TypeWrapper<>(Year.class, Year::parse);
        }
        throw new BeanException(property, "must not be annotated by @Up2Temporal");
    }

}
