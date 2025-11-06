package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.slv.CodeListResolver;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import io.github.up2jakarta.xml.clv.CodeListProvider;
import io.github.up2jakarta.xml.clv.DefaultProvider;

import java.lang.annotation.*;

/**
 * Up2 {@link Conversion} resolver
 * that supports {@link io.github.up2jakarta.xml.clv.CodeList} types.
 *
 * @see CodeListConverter
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(CodeListResolver.class)
public @interface Up2CodeList {

    /**
     * The provider must be managed by {@link BeanContext}.
     *
     * @return the code-list provider.
     */
    Class<? extends CodeListProvider<?>> value() default DefaultProvider.class;

}
