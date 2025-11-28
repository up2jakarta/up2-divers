package io.github.up2jakarta.lov;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Up2J Compile Annotation that marks {@link CodeList} as sub-list of another code-list.
 */
@Documented
@Target(TYPE)
@Retention(SOURCE)
public @interface SubList {

    /**
     * @return the id of the main {@link CodeList}
     */
    String value();

    /**
     * @return the class of the main {@link CodeList}
     */
    Class<? extends CodeList> type() default CodeList.class;

}

