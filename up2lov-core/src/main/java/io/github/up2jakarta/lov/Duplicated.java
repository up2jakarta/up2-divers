package io.github.up2jakarta.lov;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Up2J Annotation that marks {@link CodeList} as duplicated.
 */
@Documented
@Target(FIELD)
@Retention(SOURCE)
@Repeatable(Duplicated.List.class)
public @interface Duplicated {

    /**
     * @return the name of the duplicated {@link CodeList}.
     */
    String value();

    @Documented
    @Retention(SOURCE)
    @Target(FIELD)
    @interface List {

        /**
         * @return the related duplications
         */
        Duplicated[] value();

    }
}
