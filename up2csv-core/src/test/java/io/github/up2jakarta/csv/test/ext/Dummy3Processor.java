package io.github.up2jakarta.csv.test.ext;

import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class Dummy3Processor extends ConfigurableProcessor<Dummy3> {

    public String process(String value, Dummy3 ignore) {
        return Dummy1Processor.process(value);
    }

}
