package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.cfg.Processor;
import io.github.up2jakarta.test.core.misc.DummyException;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Processor(value = Dummy1Processor.class, skip = DummyException.class)
public @interface Dummy1 {
}
