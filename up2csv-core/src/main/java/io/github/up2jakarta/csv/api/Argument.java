package io.github.up2jakarta.csv.api;

import java.lang.annotation.Target;

/**
 * Up2J Internal Argument Definition for {@link io.github.up2jakarta.lov.Parameter}..
 */
@Target({})
public @interface Argument {

    /**
     * @return the {@link io.github.up2jakarta.lov.Parameter#value()}.
     */
    String key();

    /**
     * @return the argument value
     */
    String value();
}
