package io.github.up2jakarta.csv.test.extension;

import io.github.up2jakarta.csv.annotation.Processor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(value = Dummy2Processor.class)
public @interface Dummy2 {
}
