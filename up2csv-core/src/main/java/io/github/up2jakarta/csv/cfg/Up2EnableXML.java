package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.core.ext.XmlAdapterExtension;
import io.github.up2jakarta.csv.core.ext.XmlEnumExtension;

import java.lang.annotation.*;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} extension that supports JPA types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// Extensions
@Extension(value = XmlAdapterExtension.class)
@Extension(value = XmlEnumExtension.class)
public @interface Up2EnableXML {
}

