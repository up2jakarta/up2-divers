package io.github.up2jakarta.csv.cfg;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, TYPE})
@Repeatable(value = FragmentOverride.List.class)
public @interface FragmentOverride {

    /**
     * The mapping type will remain the same as is defined in the segment class unless overridden.
     * <p>
     * If the offset is negative, the annotated property will be excluded i.e. is not mapped anymore.
     * <p>
     * <b>Note that this one will completely erase the other one defined at property level</b>
     *
     * @return the new fragment configuration
     */
    Fragment value() default @Fragment(-1);

    /**
     * @return the path of the fragment whose mapping is being overridden.
     */
    String[] path();

    /**
     * Up2J Annotation that supports {@link Repeatable} {@link FragmentOverride}.
     */
    @Retention(RUNTIME)
    @Target({FIELD, TYPE})
    @Documented
    @interface List {

        FragmentOverride[] value();

    }
}
