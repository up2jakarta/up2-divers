package io.github.up2jakarta.csv.slv;

import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.Up2Decimal;
import io.github.up2jakarta.csv.core.Up2Adapter;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.TypeConverter;
import io.github.up2jakarta.lov.TypeFormatter;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.math.BigDecimal;

@Named
@Singleton
public final class DecimalResolver implements TypeResolver<Number, Up2Decimal> {

    private static BigDecimal round(BigDecimal value, Up2Decimal decimal) {
        if (value.scale() > decimal.value()) {
            return value.setScale(decimal.value(), decimal.roundingMode());
        }
        return value;
    }

    @Override
    public TypeAdapter<? extends Number> resolve(Field pf, Class<Number> pt, Up2Decimal pc) throws BeanException {
        if (BigDecimal.class.equals(pt)) {
            final TypeConverter<BigDecimal> parser = v -> round(new BigDecimal(v), pc);
            final TypeFormatter<BigDecimal> format = v -> round(v, pc).toString();
            return new Up2Adapter<>(BigDecimal.class, parser, format);
        }
        if (Double.class.equals(pt) || double.class.equals(pt)) {
            final TypeConverter<Double> parser = v -> round(new BigDecimal(v), pc).doubleValue();
            final TypeFormatter<Double> format = v -> round(BigDecimal.valueOf(v), pc).toString();
            return new Up2Adapter<>(Double.class, parser, format);
        }
        if (Float.class.equals(pt) || float.class.equals(pt)) {
            final TypeConverter<Float> parser = v -> round(new BigDecimal(v), pc).floatValue();
            final TypeFormatter<Float> format = v -> round(BigDecimal.valueOf(v), pc).toString();
            return new Up2Adapter<>(Float.class, parser, format);
        }
        throw new BeanException(pf, "must not be annotated with @Up2Decimal");
    }

}
