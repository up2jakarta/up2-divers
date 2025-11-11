package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.CheckerContext;
import io.github.up2jakarta.csv.api.ext.SegmentListener;
import io.github.up2jakarta.csv.core.AccessMode;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;
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
    public boolean isActivated(Class<? extends Segment> type) {
        return type.getAnnotation(Entity.class) != null;
    }

    @Override
    public CheckerContext beforeSegment(AccessMode mode, Class<? extends Segment> type) throws BeanException {
        final String prefix = JpaTableChecker.checkAndGetPrefix(type);
        return new JpaColumnContext(type, prefix);
    }

}
