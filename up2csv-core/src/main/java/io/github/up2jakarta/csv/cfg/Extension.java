package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.api.ext.ConversionExtension;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link Conversion} for third-party types.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Repeatable(Extensions.class)
public @interface Extension {

    /**
     * @return the conversion extension type
     */
    Class<? extends ConversionExtension<?, ?>> value();

}
