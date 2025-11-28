package io.github.up2jakarta.csv.core.misc.ext;

import io.github.up2jakarta.csv.core.misc.DummyException;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.SafeAdapter;
import org.springframework.stereotype.Component;

@Component
public final class DummyConverter extends SafeAdapter<Integer> {

    public static final String TU_P_005 = "TU-P005";

    public DummyConverter() {
        super(Integer.class, SeverityType.ERROR, TU_P_005);
    }

    @Override
    public Integer doParse(String value) {
        if ("dummy".equals(value)) {
            try {
                Dummy1Processor.process("NPE");
            } catch (RuntimeException ex) {
                throw new DummyException("dummy wrapped message", ex);
            }
        }
        return Integer.parseInt(value);
    }

    @Override
    public String doFormat(Integer value) {
        return String.valueOf(value);
    }
}
