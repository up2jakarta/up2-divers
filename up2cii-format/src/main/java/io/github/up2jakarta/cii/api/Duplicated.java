package io.github.up2jakarta.cii.api;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Repeatable(DuplicatedList.class)
public @interface Duplicated {

    /**
     * The duplicated name, some {@link io.github.up2jakarta.csv.extension.CodeList} has duplicated enum constants.
     *
     * @return the name of the duplicated {@link io.github.up2jakarta.csv.extension.CodeList} enum constant
     */
    String value();

}
