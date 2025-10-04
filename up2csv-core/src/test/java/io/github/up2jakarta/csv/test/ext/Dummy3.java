package io.github.up2jakarta.csv.test.ext;

import io.github.up2jakarta.csv.cfg.Processor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(value = Dummy3Processor.class, skip = IllegalArgumentException.class)
public @interface Dummy3 {
}
