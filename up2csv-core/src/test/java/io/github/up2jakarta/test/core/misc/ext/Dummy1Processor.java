package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.test.core.misc.DummyException;
import org.springframework.stereotype.Component;

@Component
public class Dummy1Processor implements InputProcessor<Dummy1> {

    public static final String TU_P_001 = "TU-P001";

    static String process(String value) {
        if (value == null) {
            return value;
        }
        if (value.isBlank() || "null".equals(value)) {
            throw new NullPointerException("null message");
        }
        if (value.equals("dummy")) {
            throw new DummyException("dummy message");
        }
        if (value.equals("property")) {
            throw new TypeException(SeverityType.FATAL, TU_P_001, value + " message");
        }
        throw new RuntimeException(value + " message");
    }

    @Override
    public String process(String value, Dummy1 ignore) {
        return process(value);
    }

}
