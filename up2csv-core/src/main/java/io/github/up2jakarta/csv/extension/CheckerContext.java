package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.exception.BeanException;

import java.lang.reflect.Field;

public interface CheckerContext {

    default void beforeSuperSegment(Class<? extends Segment> superType) throws BeanException {
    }

    default void afterSuperSegment(Class<? extends Segment> superType) throws BeanException {
    }

    default void beforePositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
    }

    default void afterPositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
    }

    default void beforeFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
    }

    default void afterFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
    }

    default void unknownProperty(Field property, Class<?> propertyType) throws BeanException {
    }

}
