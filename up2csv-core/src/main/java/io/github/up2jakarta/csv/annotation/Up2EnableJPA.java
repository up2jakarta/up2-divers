package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.core.JpaConvertExtension;
import io.github.up2jakarta.csv.core.JpaEnumeratedExtension;

import java.lang.annotation.*;

/**
 * Up2 {@link io.github.up2jakarta.csv.extension.Conversion} extension that supports XML types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// Extensions
@Extension(value = JpaEnumeratedExtension.class)
@Extension(value = JpaConvertExtension.class)
public @interface Up2EnableJPA {
}
