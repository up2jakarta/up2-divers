package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.SimpleResolver;
import io.github.up2jakarta.csv.cfg.Up2TemporalAmount;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

@Named
@Singleton
public final class TemporalAmountResolver extends SimpleResolver<TemporalAmount, Up2TemporalAmount> {

    @Override
    protected TypeAdapter<? extends TemporalAmount> resolve(Field pf, Class<TemporalAmount> pt) throws BeanException {
        if (Period.class.equals(pt)) {
            return new Up2Adapter<>(Period.class, Period::parse);
        }
        if (Duration.class.equals(pt)) {
            return new Up2Adapter<>(Duration.class, Duration::parse);
        }
        throw new BeanException(pf, "must not be annotated with @Up2TemporalAmount");
    }

}
