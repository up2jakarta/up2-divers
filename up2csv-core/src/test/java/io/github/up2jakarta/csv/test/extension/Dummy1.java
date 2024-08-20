package io.github.up2jakarta.csv.test.extension;

import io.github.up2jakarta.csv.annotation.Processor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(value = Dummy1Processor.class, skip = DummyException.class)
public @interface Dummy1 {
}
