package io.github.up2jakarta.csv.test.ext;

import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class Dummy2Processor extends ConfigurableProcessor<Dummy2> {

    public String process(String value, Dummy2 ignore) {
        return Dummy1Processor.process(value);
    }

}
