package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;

import java.lang.reflect.Field;

/**
 * Contract interface for checker-context listener.
 *
 * @see io.github.up2jakarta.csv.cfg.Checker
 * @see io.github.up2jakarta.csv.cfg.Extension
 */
public interface CheckerContext {

    /**
     * Listener callback before scanning the super-class of a <code>segment</code>.
     *
     * @param superType the super-class type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void beforeSuperSegment(Class<? extends Segment> superType) throws BeanException {
    }

    /**
     * Listener callback after scanning the super-class of a <code>segment</code>.
     *
     * @param superType the super-class type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void afterSuperSegment(Class<? extends Segment> superType) throws BeanException {
    }

    /**
     * Listener callback before scanning a field annotated by {@link io.github.up2jakarta.csv.cfg.Position}.
     *
     * @param property     the field annotated by {@link io.github.up2jakarta.csv.cfg.Position}
     * @param propertyType the property type
     * @param offset       the position index
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void beforePositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
    }

    /**
     * Listener callback after scanning a field annotated by {@link io.github.up2jakarta.csv.cfg.Position}.
     *
     * @param property     the field annotated by {@link io.github.up2jakarta.csv.cfg.Position}
     * @param propertyType the property type
     * @param offset       the position index
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void afterPositionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
    }

    /**
     * Listener callback before scanning a segment annotated by {@link io.github.up2jakarta.csv.cfg.Fragment}.
     * Before super-class segment handler or listener.
     *
     * @param fragment     the field annotated by {@link io.github.up2jakarta.csv.cfg.Fragment}
     * @param fragmentType the fragment type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void beforeFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
    }

    /**
     * Listener callback after scanning a segment annotated by {@link io.github.up2jakarta.csv.cfg.Fragment}.
     *
     * @param fragment     the field annotated by {@link io.github.up2jakarta.csv.cfg.Fragment}
     * @param fragmentType the fragment type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void afterFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
    }

    /**
     * Listener callback after scanning an unknown property the not annotated by <code>Position</code> and <code>Fragment</code>.
     *
     * @param property     the unknown field
     * @param propertyType the unknown field type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void unknownProperty(Field property, Class<?> propertyType) throws BeanException {
    }

}
