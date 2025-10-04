package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.xml.codelist.TypeConverter;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link TypeConverter}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Up2Converter {

    /**
     * The processor must be managed by {@link BeanContext}
     *
     * @return the class of the processor
     */
    Class<? extends TypeConverter<?>> value();

}
