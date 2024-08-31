package io.github.up2jakarta.csv.test.ext;

import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import io.github.up2jakarta.csv.extension.SeverityType;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class Dummy1Processor extends ConfigurableProcessor<Dummy1> {

    public static final String TU_P_001 = "TU-P001";

    static String process(String value) {
        if (value == null || value.isBlank()) {
            throw new NullPointerException("NPE");
        }
        if (value.equals("dummy")) {
            throw new DummyException(value);
        }
        if (value.equals("property")) {
            throw new PropertyException(SeverityType.FATAL, TU_P_001, value);
        }
        throw new RuntimeException(value);
    }

    public String process(String value, Dummy1 ignore) {
        return process(value);
    }

}
