package io.github.up2jakarta.csv.test.extension;

import io.github.up2jakarta.csv.annotation.Processor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(value = Dummy3Processor.class, skip = IllegalArgumentException.class)
public @interface Dummy4 {
}
