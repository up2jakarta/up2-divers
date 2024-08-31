package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.exception.BeanException;

/**
 * Checker for annotated {@link Segment}.
 */
public interface SegmentListener {

    default boolean isActivated(Class<? extends Segment> segmentType) {
        return true;
    }

    CheckerContext beforeSegment(Class<? extends Segment> segmentType) throws BeanException;

    @SuppressWarnings("ALL")
    default void afterSegment(CheckerContext context) throws BeanException {
    }

}
