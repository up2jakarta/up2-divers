package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.api.TypeConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NumberConverter extends TypeConverter<Number> {

    protected NumberConverter() {
        super(Number.class, SeverityType.ERROR, "TST");
    }

    @Override
    public Number parse(String value) {
        return new BigDecimal(value);
    }

    @Override
    public String format(Number value) {
        return value.toString();
    }
}
