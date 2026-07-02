package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.cfg.Processor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Processor(value = Dummy3Processor.class, skip = IllegalArgumentException.class)
public @interface Dummy4 {
}
