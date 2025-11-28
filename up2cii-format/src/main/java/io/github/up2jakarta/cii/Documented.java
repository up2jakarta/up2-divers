package io.github.up2jakarta.cii;

import io.github.up2jakarta.cii.core.Agency;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Up2J Compile Annotation that documents code-list definition.
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface Documented {

    /**
     * @return code-list name.
     */
    String value();

    /**
     * @return code-list agency.
     */
    Agency agency();

    /**
     * @return code-list version.
     */
    String version();

}

