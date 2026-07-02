package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.slv.CharacterResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J {@link io.github.up2jakarta.lov.TypeAdapter} resolver that supports {@link Character} types.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Resolver(CharacterResolver.class)
public @interface Up2Character {

    /**
     * @return the flag that activates the validation of {@link String#length()} to be equals to <code>1</code>
     */
    boolean value() default true;

}
