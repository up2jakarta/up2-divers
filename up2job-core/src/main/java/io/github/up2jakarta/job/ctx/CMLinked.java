package io.github.up2jakarta.job.ctx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The context never returns null value when the getter is annotated with.
 *
 * @see java.util.LinkedHashMap
 * @see java.util.LinkedHashSet
 * @see java.util.LinkedList
 */
@java.lang.annotation.Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CMLinked {

}
