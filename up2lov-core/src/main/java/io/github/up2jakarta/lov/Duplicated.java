package io.github.up2jakarta.lov;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Repeatable(DuplicatedList.class)
public @interface Duplicated {

    /**
     * The duplicated name, some {@link CodeList} has duplicated enum constants.
     *
     * @return the name of the duplicated {@link CodeList} enum constant
     */
    String value();

}
