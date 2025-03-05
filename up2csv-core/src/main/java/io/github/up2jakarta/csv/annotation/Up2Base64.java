package io.github.up2jakarta.csv.annotation;

import io.github.up2jakarta.csv.resolver.Base64Resolver;

import java.lang.annotation.*;

/**
 * Up2 {@link io.github.up2jakarta.csv.extension.Conversion} resolver that Base64 {@link Byte[]} types.
 *
 * @see java.util.Base64#getDecoder()
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resolver(Base64Resolver.class)
public @interface Up2Base64 {

    String encoding() default "UTF-8";

}
