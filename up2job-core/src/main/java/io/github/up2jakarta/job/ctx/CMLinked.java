package io.github.up2jakarta.job.ctx;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The context never returns null value when the getter is annotated with.
 *
 * @see java.util.LinkedHashMap
 * @see java.util.LinkedHashSet
 * @see java.util.LinkedList
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface CMLinked {

}
