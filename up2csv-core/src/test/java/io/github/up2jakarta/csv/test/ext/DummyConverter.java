package io.github.up2jakarta.csv.test.ext;

import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.extension.TypeConverter;
import org.springframework.stereotype.Component;

@Component
public class DummyConverter extends TypeConverter<Integer> {

    public static final String TU_P_005 = "TU-P005";

    protected DummyConverter() {
        super(Integer.class, SeverityType.ERROR, TU_P_005);
    }


    @Override
    public Integer parse(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String format(Integer value) {
        return String.valueOf(value);
    }
}
