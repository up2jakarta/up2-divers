package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.core.XmlAdapterExtension;
import io.github.up2jakarta.csv.core.XmlEnumExtension;

import java.lang.annotation.*;

/**
 * Up2 {@link io.github.up2jakarta.csv.extension.Conversion} extension that supports JPA types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// Extensions
@Extension(value = XmlAdapterExtension.class)
@Extension(value = XmlEnumExtension.class)
public @interface Up2EnableXML {
}

