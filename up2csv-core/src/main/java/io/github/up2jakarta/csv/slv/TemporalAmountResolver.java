package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.TypeWrapper;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

@Named
@Singleton
public final class TemporalAmountResolver extends TypeResolver<Up2TemporalAmount> {

    @Override
    public TypeAdapter<? extends TemporalAmount> resolve(Field property, Class<?> type, Up2TemporalAmount config) throws BeanException {
        if (type == Period.class) {
            return new TypeWrapper<>(Period.class, Period::parse);
        }
        if (type == Duration.class) {
            return new TypeWrapper<>(Duration.class, Duration::parse);
        }
        throw new BeanException(property, "must not be annotated by @Up2TemporalAmount");
    }

}
