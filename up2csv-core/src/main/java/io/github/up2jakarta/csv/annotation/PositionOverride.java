package io.github.up2jakarta.csv.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, TYPE})
@Documented
@Repeatable(value = PositionOverrides.class)
public @interface PositionOverride {

    /**
     * The position that is being mapped to the segment attribute.
     * The mapping type will remain the same as is defined in the segment class.
     * If the offset is negative, the annotated property will be excluded i.e. its value will be <code>null</code>.
     */
    Position value() default @Position(-1);

    /**
     * The path of the property whose mapping is being overridden.
     */
    String[] path();

}
