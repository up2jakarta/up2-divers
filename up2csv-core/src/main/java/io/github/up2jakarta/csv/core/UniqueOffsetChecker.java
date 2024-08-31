package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.CheckerContext;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SegmentListener;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Named
@Singleton
public final class UniqueOffsetChecker implements SegmentListener {

    @Override
    public CheckerContext beforeSegment(Class<? extends Segment> segmentType) {
        return new ContextImpl();
    }

    private static class ContextImpl implements CheckerContext {
        private final List<Integer> offsets = new LinkedList<>();

        @Override
        public void beforePositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
            if (!offsets.add(offset)) {
                throw new BeanException(property, "@Position[value] must be unique");
            }
        }
    }
}
