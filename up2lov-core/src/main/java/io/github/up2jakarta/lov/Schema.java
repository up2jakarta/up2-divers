package io.github.up2jakarta.lov;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Up2J Compile Annotation that indicates the schema of {@link CodeList}.
 */
@Documented
@Target(TYPE)
@Retention(SOURCE)
public @interface Schema {

    /**
     * @return Schema agency.
     */
    String agency();

    /**
     * @return Schema version.
     */
    String version();

    /**
     * @return Schema date.
     */
    String date();

}

