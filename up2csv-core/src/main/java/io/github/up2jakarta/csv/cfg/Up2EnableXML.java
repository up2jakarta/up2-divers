package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.core.ext.XmlAdapterExtension;
import io.github.up2jakarta.csv.core.ext.XmlEnumExtension;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} extension that supports XML type adapters.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
// Extensions
@Extension(value = XmlAdapterExtension.class)
@Extension(value = XmlEnumExtension.class)
public @interface Up2EnableXML {
}

