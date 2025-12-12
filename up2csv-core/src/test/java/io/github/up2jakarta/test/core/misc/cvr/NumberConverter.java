package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.SafeAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public final class NumberConverter extends SafeAdapter<Number> {

    @Autowired
    public NumberConverter() {
        super(Number.class, SeverityType.ERROR, "TST");
    }

    @Override
    protected Number doParse(String value) {
        return new BigDecimal(value);
    }

    @Override
    protected String doFormat(Number value) {
        return value.toString();
    }
}
