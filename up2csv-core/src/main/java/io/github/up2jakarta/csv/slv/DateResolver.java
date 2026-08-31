package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.SimpleResolver;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Up2Date;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_UTIL_DATE;
import static io.github.up2jakarta.csv.ext.Beans.error;
import static io.github.up2jakarta.lov.SeverityType.ERROR;

@Named
@Singleton
public final class DateResolver extends SimpleResolver<Date, Up2Date> {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected TypeAdapter<? extends Date> resolve(Field pf, Class<Date> pt) throws BeanException {
        if (Date.class.equals(pt)) {
            final Optional<Error> error = error(pf, pt);
            final SeverityType level = error.map(Error::level).orElse(ERROR);
            final String code = error.map(Error::value).orElse(EC_UTIL_DATE);
            final TypeConverter<Date> parser = v -> {
                try {
                    return DATE_FORMAT.parse(v);
                } catch (ParseException cause) {
                    throw new TypeException(level, code, cause.getMessage(), cause);
                }
            };
            return new Up2Adapter<>(Date.class, parser, DATE_FORMAT::format);
        }
        if (java.sql.Date.class.equals(pt)) {
            return new Up2Adapter<>(java.sql.Date.class, java.sql.Date::valueOf);
        }
        if (Time.class.equals(pt)) {
            return new Up2Adapter<>(Time.class, Time::valueOf);
        }
        if (Timestamp.class.equals(pt)) { // OK
            return new Up2Adapter<>(Timestamp.class, Timestamp::valueOf);
        }
        throw new BeanException(pf, "must not be annotated with @Up2Date");
    }

}
