package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.Base64Resolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that Base64 {@link Byte[]} types.
 *
 * @see java.util.Base64#getDecoder()
 * @see java.util.Base64#getEncoder()
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Resolver(Base64Resolver.class)
public @interface Up2Base64 {

    String encoding() default "UTF-8";

}
