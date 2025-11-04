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
@Repeatable(value = FragmentOverride.List.class)
public @interface FragmentOverride {

    /**
     * The fragment that is being mapped to the segment attribute.
     * The mapping type will remain the same as is defined in the segment class.
     * If the offset is negative, the annotated segment will be excluded i.e. its value will be <code>null</code>.
     */
    Fragment value() default @Fragment(-1);

    /**
     * The path of the fragment whose mapping is being overridden.
     */
    String[] path();

    /**
     * Up2 Annotation that supports {@link Repeatable} {@link FragmentOverride}.
     */
    @Retention(RUNTIME)
    @Target({FIELD, TYPE})
    @Documented
    @interface List {

        FragmentOverride[] value();

    }
}
