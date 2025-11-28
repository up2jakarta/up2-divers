package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.SimpleResolver;
import io.github.up2jakarta.csv.cfg.Up2Temporal;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeSupport;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.time.*;
import java.time.temporal.Temporal;

@Named
@Singleton
public final class TemporalResolver extends SimpleResolver<Temporal, Up2Temporal> {

    @Override
    protected TypeAdapter<? extends Temporal> resolve(Field pf, Class<Temporal> pt) throws BeanException {
        if (LocalTime.class.equals(pt)) {
            return new TypeSupport<>(LocalTime.class, LocalTime::parse);
        }
        if (LocalDate.class.equals(pt)) {
            return new TypeSupport<>(LocalDate.class, LocalDate::parse);
        }
        if (LocalDateTime.class.equals(pt)) {
            return new TypeSupport<>(LocalDateTime.class, LocalDateTime::parse);
        }
        if (OffsetTime.class.equals(pt)) {
            return new TypeSupport<>(OffsetTime.class, OffsetTime::parse);
        }
        if (OffsetDateTime.class.equals(pt)) {
            return new TypeSupport<>(OffsetDateTime.class, OffsetDateTime::parse);
        }
        if (ZonedDateTime.class.equals(pt)) {
            return new TypeSupport<>(ZonedDateTime.class, ZonedDateTime::parse);
        }
        if (Instant.class.equals(pt)) {
            return new TypeSupport<>(Instant.class, Instant::parse);
        }
        if (YearMonth.class.equals(pt)) {
            return new TypeSupport<>(YearMonth.class, YearMonth::parse);
        }
        if (Year.class.equals(pt)) {
            return new TypeSupport<>(Year.class, Year::parse);
        }
        throw new BeanException(pf, "must not be annotated with @Up2Temporal");
    }

}
