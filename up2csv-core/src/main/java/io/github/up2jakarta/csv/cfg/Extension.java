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
@Repeatable(Extension.List.class)
public @interface Extension {

    /**
     * @return the conversion extension type
     */
    Class<? extends ConversionExtension<?, ?>> value();

    /**
     * Up2 Annotation that supports {@link Repeatable} {@link Extension}.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface List {

        /**
         * @return the extensions
         */
        Extension[] value();

    }
}
