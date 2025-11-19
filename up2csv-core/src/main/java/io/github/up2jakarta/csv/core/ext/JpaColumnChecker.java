package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.core.AccessMode;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.Entity;

/**
 * JPA {@link jakarta.persistence.Column} checker implementation.
 */
@Named
@Singleton
public final class JpaColumnChecker implements TypeListener {

    @Override
    public boolean isActivated(Class<? extends Segment> type) {
        return type.getAnnotation(Entity.class) != null;
    }

    @Override
    public TypeContext beforeSegment(AccessMode mode, Class<? extends Segment> type) throws BeanException {
        final String prefix = JpaTableChecker.checkAndGetPrefix(type);
        return new JpaColumnContext(type, prefix);
    }

}
