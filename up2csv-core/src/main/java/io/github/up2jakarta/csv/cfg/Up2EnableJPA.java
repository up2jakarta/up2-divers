package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.core.ext.*;

import java.lang.annotation.*;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} extension that supports XML types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// Extensions
@Extension(value = JpaEnumeratedExtension.class)
@Extension(value = JpaConvertExtension.class)
// Checkers
@Checker(UniqueOffsetChecker.class)
@Checker(JpaTableChecker.class)
@Checker(JpaColumnChecker.class)
public @interface Up2EnableJPA {
}
