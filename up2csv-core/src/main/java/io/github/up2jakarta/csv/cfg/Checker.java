package io.github.up2jakarta.csv.cfg;

import io.github.up2jakarta.csv.api.ext.SegmentListener;

import java.lang.annotation.*;

/**
 * Up2 Annotation that supports {@link SegmentListener} for segment checking.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Repeatable(Checker.List.class)
public @interface Checker {

    /**
     * @return the class checker type
     */
    Class<? extends SegmentListener> value();

    /**
     * Up2 Annotation that supports {@link Repeatable} {@link Checker}.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE)
    @interface List {

        /**
         * @return the segment checkers list
         */
        Checker[] value();

    }
}
