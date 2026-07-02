package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.core.ext.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} extension that supports JPA type-adapters.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
// Extensions
@Extension(value = JpaEnumeratedExtension.class)
@Extension(value = JpaConvertExtension.class)
// Checkers
@Checker(UniqueGapChecker.class)
@Checker(JpaTableChecker.class)
@Checker(JpaColumnChecker.class)
public @interface Up2EnableJPA {
}
