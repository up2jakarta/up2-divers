package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.extension.ConversionResolver;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link ConversionResolver} used for resolver's shortcut annotations.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Resolver {

    /**
     * The resolver must be managed by {@link io.github.up2jakarta.csv.extension.BeanContext}
     *
     * @return the {@link io.github.up2jakarta.csv.extension.Conversion} resolver
     */
    Class<? extends ConversionResolver<?>> value();

}
