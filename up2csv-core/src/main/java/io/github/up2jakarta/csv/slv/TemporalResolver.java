package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.SimpleResolver;
import io.github.up2jakarta.csv.cfg.Up2Temporal;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
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
            return new Up2Adapter<>(LocalTime.class, LocalTime::parse);
        }
        if (LocalDate.class.equals(pt)) {
            return new Up2Adapter<>(LocalDate.class, LocalDate::parse);
        }
        if (LocalDateTime.class.equals(pt)) {
            return new Up2Adapter<>(LocalDateTime.class, LocalDateTime::parse);
        }
        if (OffsetTime.class.equals(pt)) {
            return new Up2Adapter<>(OffsetTime.class, OffsetTime::parse);
        }
        if (OffsetDateTime.class.equals(pt)) {
            return new Up2Adapter<>(OffsetDateTime.class, OffsetDateTime::parse);
        }
        if (ZonedDateTime.class.equals(pt)) {
            return new Up2Adapter<>(ZonedDateTime.class, ZonedDateTime::parse);
        }
        if (Instant.class.equals(pt)) {
            return new Up2Adapter<>(Instant.class, Instant::parse);
        }
        if (YearMonth.class.equals(pt)) {
            return new Up2Adapter<>(YearMonth.class, YearMonth::parse);
        }
        if (Year.class.equals(pt)) {
            return new Up2Adapter<>(Year.class, Year::parse);
        }
        throw new BeanException(pf, "must not be annotated with @Up2Temporal");
    }

}
