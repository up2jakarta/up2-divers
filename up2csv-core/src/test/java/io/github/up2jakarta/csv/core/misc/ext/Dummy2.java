package io.github.up2jakarta.csv.core.misc.ext;

import io.github.up2jakarta.csv.cfg.Processor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(value = Dummy2Processor.class)
public @interface Dummy2 {
}
