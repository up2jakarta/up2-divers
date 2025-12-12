package io.github.up2jakarta.test.core.misc.ext;

import io.github.up2jakarta.csv.cfg.Resolver;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(Up2DummyResolver.class)
public @interface Up2Dummy {
}
