package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.extension.ConversionExtension;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link io.github.up2jakarta.csv.extension.Conversion} for third-party types.
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
