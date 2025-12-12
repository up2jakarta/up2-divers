package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import org.springframework.stereotype.Component;

@Component
public class Dummy2Processor implements InputProcessor<Dummy2> {

    public String process(String value, Dummy2 ignore) {
        return Dummy1Processor.process(value);
    }

}
