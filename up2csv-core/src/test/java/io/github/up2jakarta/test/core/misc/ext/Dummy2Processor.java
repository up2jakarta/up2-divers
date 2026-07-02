package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.api.ext.SimpleProcessor;
import org.springframework.stereotype.Component;

@Component
public class Dummy2Processor extends SimpleProcessor<Dummy2> {

    @Override
    protected String process(String value) {
        return Dummy1Processor.process(value);
    }

}
