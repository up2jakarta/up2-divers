package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.Conversion;
import io.github.up2jakarta.csv.slv.Base64Resolver;

import java.lang.annotation.*;

/**
 * Up2 {@link Conversion} resolver that Base64 {@link Byte[]} types.
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
