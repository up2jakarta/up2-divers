package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.extension.TypeConverter;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link TypeConverter}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Converter {

    /**
     * The processor must be managed by {@link io.github.up2jakarta.csv.extension.BeanContext}
     *
     * @return the class of the processor
     */
    Class<? extends TypeConverter<?>> value();

}
