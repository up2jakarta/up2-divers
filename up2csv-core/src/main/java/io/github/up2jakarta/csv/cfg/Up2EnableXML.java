package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.core.XmlAdapterExtension;
import io.github.up2jakarta.csv.core.XmlEnumExtension;

import java.lang.annotation.*;

/**
 * Up2 {@link Conversion} extension that supports JPA types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// Extensions
@Extension(value = XmlAdapterExtension.class)
@Extension(value = XmlEnumExtension.class)
public @interface Up2EnableXML {
}

