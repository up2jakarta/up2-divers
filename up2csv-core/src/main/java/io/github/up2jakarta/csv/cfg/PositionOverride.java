package io.github.up2jakarta.csv.cfg;

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
@Repeatable(value = PositionOverride.List.class)
public @interface PositionOverride {

    /**
     * The mapping type will remain the same as is defined in the segment class unless overridden.
     * <p>
     * If the offset is negative, the annotated property will be excluded i.e. is not mapped anymore.
     *
     * @return the new position configuration
     */
    Position value() default @Position(-1);

    /**
     * @return the path of the property whose mapping is being overridden.
     */
    String[] path();

    /**
     * Up2J Annotation that supports {@link Repeatable} {@link PositionOverride}.
     */
    @Retention(RUNTIME)
    @Target({FIELD, TYPE})
    @Documented
    @interface List {

        PositionOverride[] value();

    }
}
