package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.Segment;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static io.github.up2jakarta.csv.core.MapperFactory.LOGGER;

final class TechnicalChecker {

    private TechnicalChecker() {
    }

    private static void checkInner(Class<? extends Segment> beanType) throws BeanException {
        if (beanType.getEnclosingClass() != null && !Modifier.isStatic(beanType.getModifiers())) {
            throw new BeanException(beanType, "inner class is not allowed");
        }
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

    static void checkSegment(Class<? extends Segment> segmentType) throws BeanException {
        if (segmentType.isLocalClass()) {
            throw new BeanException(segmentType, "local class is not allowed");
        }
        if (segmentType.isInterface()) {
            throw new BeanException(segmentType, "interface is not allowed");
        }
        if (Modifier.isAbstract(segmentType.getModifiers())) {
            throw new BeanException(segmentType, "abstract class is not allowed");
        }
        if (segmentType.getTypeParameters().length != 0) {
            throw new BeanException(segmentType, "generic class is not allowed");
        }
        if (segmentType.isRecord()) {
            throw new BeanException(segmentType, "record class is not allowed");
        }
        checkInner(segmentType);
    }

    static void checkPositionProperty(Field property) throws BeanException {
        checkField(property);
    }

    static void checkFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
        checkInner(fragmentType);
        checkField(fragment);
    }
}
