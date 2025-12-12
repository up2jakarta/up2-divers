package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import org.springframework.stereotype.Component;

@Component
public class Dummy3Processor implements InputProcessor<Dummy3> {

    public String process(String value, Dummy3 ignore) {
        return Dummy1Processor.process(value);
    }

}
