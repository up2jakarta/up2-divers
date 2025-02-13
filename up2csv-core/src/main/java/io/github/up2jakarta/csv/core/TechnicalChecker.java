package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.CheckerContext;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SegmentListener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static io.github.up2jakarta.csv.core.MapperFactory.LOGGER;

final class TechnicalChecker implements SegmentListener, CheckerContext {

    static final TechnicalChecker INSTANCE = new TechnicalChecker();

    private TechnicalChecker() {
    }

    private static void checkField(Field field) throws BeanException {
        final Class<?> type = field.getDeclaringClass();
        if (Character.isUpperCase(field.getName().charAt(0))) {
            LOGGER.warn("{}[{}] : should starts with an lowercase character", type.getSimpleName(), field.getName());
        }
        if (Modifier.isPublic(field.getModifiers())) {
            throw new BeanException(type, field, "must not be public");
        }
        if (Modifier.isFinal(field.getModifiers())) {
            throw new BeanException(type, field, "must not be final");
        }
        if (Modifier.isStatic(field.getModifiers())) {
            throw new BeanException(type, field, "must not be static");
        }
    }

    @Override
    public CheckerContext beforeSegment(Class<? extends Segment> segmentType) throws BeanException {
        if (segmentType.isLocalClass()) {
            throw new BeanException(segmentType, "local class is not allowed");
        }
        if (segmentType.isInterface()) {
            throw new BeanException(segmentType, "interface is not allowed");
        }
        if (Modifier.isAbstract(segmentType.getModifiers())) {
            throw new BeanException(segmentType, "abstract class is not allowed");
        }
        if (segmentType.isRecord()) {
            throw new BeanException(segmentType, "record class is not allowed");
        }
        if (segmentType.getEnclosingClass() != null && !Modifier.isStatic(segmentType.getModifiers())) {
            throw new BeanException(segmentType, "inner class is not allowed");
        }
        return this;
    }

    @Override
    public void beforePositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
        checkField(property);
    }

    @Override
    public void beforeFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
        beforeSegment(fragmentType);
        checkField(fragment);
    }
}
