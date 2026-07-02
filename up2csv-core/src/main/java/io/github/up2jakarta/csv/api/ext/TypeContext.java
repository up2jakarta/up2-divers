package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.Field;

/**
 * Contract interface for checker-context listener.
 *
 * @see io.github.up2jakarta.csv.cfg.Checker
 * @see io.github.up2jakarta.csv.cfg.Extension
 */
public interface TypeContext {

    /**
     * Listener callback before scanning the super-class of a <code>segment</code>.
     *
     * @param type the super-class type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void beforeSuperSegment(Class<? extends Segment> type) throws BeanException {
    }

    /**
     * Listener callback after scanning the super-class of a <code>segment</code>.
     *
     * @param type the super-class type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void afterSuperSegment(Class<? extends Segment> type) throws BeanException {
    }

    /**
     * Listener callback before scanning a segment annotated with {@link io.github.up2jakarta.csv.cfg.Fragment}.
     * Before super-class segment handler or listener.
     *
     * @param fragment the field annotated with {@link io.github.up2jakarta.csv.cfg.Fragment}
     * @param type     the fragment type
     * @param offset   the fragment index
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void beforeFragmentProperty(Field fragment, Class<? extends Segment> type, int offset) throws BeanException {
    }

    /**
     * Listener callback after scanning a segment annotated with {@link io.github.up2jakarta.csv.cfg.Fragment}.
     *
     * @param fragment the field annotated with {@link io.github.up2jakarta.csv.cfg.Fragment}
     * @param type     the fragment type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void afterFragmentProperty(Field fragment, Class<? extends Segment> type) throws BeanException {
    }

    /**
     * Listener callback before scanning a field annotated with {@link io.github.up2jakarta.csv.cfg.Position}.
     *
     * @param property the field annotated with {@link io.github.up2jakarta.csv.cfg.Position}
     * @param type     the property type
     * @param offset   the position index
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void positionProperty(Field property, Class<?> type, int offset) throws BeanException {
    }

    /**
     * Listener callback after scanning an unknown property the not annotated with <code>Position</code> and <code>Fragment</code>.
     *
     * @param property the unknown field
     * @param type     the unknown field type
     * @throws BeanException for any missing or wrong bean configuration
     */
    default void unknownProperty(Field property, Class<?> type) throws BeanException {
    }

    /**
     * Listener callback after the end of scanning the <code>segment</code>.
     */
    default void close() throws BeanException {
    }

}
