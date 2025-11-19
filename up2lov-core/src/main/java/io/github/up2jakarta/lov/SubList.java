package io.github.up2jakarta.lov;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
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

