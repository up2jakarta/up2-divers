package io.github.up2jakarta.lov;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Runtime Annotation for {@link ConstantProvider} that marks code-list as deprecated.
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface Deprecated {

    /**
     * Flag that marks code-list constant as excluded from the list of values.
     *
     * @return the exclude flag
     * @see java.lang.Deprecated#forRemoval()
     */
    boolean exclude() default true;

}
