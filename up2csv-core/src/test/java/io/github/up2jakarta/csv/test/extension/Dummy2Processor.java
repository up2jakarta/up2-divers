package io.github.up2jakarta.csv.test.extension;

import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class Dummy2Processor implements ConfigurableProcessor<Dummy2> {

    public String process(String value, Dummy2 ignore) {
        return Dummy1Processor.process(value);
    }

}
