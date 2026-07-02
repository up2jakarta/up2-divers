package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.api.ext.SimpleProcessor;
import org.springframework.stereotype.Component;

@Component
public class Dummy3Processor extends SimpleProcessor<Dummy3> {

    @Override
    protected String process(String value) {
        return Dummy1Processor.process(value);
    }

}
