package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.api.ext.ConversionResolver;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link ConversionResolver} used for resolver's shortcut annotations.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Resolver {

    /**
     * The resolver must be managed by {@link BeanContext}
     *
     * @return the {@link Conversion} resolver
     */
    Class<? extends ConversionResolver<?>> value();

}
