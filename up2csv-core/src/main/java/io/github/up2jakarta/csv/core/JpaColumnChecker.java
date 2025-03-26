package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.extension.CheckerContext;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SegmentListener;
import io.github.up2jakarta.csv.misc.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.Entity;

/**
 * JPA {@link jakarta.persistence.Column} checker implementation.
 */
@Named
@Singleton
public final class JpaColumnChecker implements SegmentListener {

    @Override
    public boolean isActivated(Class<? extends Segment> segmentType) {
        return segmentType.getAnnotation(Entity.class) != null;
    }

    @Override
    public CheckerContext beforeSegment(Class<? extends Segment> segmentType) throws BeanException {
        final String prefix = JpaTableChecker.checkAndGetPrefix(segmentType);
        return new JpaColumnContext(segmentType, prefix);
    }

}
