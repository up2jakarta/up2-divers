package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.cfg.Processor;
import io.github.up2jakarta.test.core.misc.DummyException;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Processor(value = Dummy1Processor.class, skip = DummyException.class)
public @interface Dummy1 {
}
