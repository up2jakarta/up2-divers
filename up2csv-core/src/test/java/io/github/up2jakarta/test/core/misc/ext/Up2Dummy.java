package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.cfg.Resolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Resolver(Up2DummyResolver.class)
public @interface Up2Dummy {
}
