package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.core.BSProperty.Accessor;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.persistence.AccessType;

import java.lang.reflect.Field;

import static java.lang.reflect.Modifier.isFinal;

/**
 * Defines access modes used to test the accessibility of a property.
 */
public abstract class BeanAccess {

    /**
     * Property read only access.
     */
    public static final BeanAccess RO = new BeanAccess() {
        @Override
        public <V> Accessor<V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
            if (AccessType.FIELD == at) {
                return new Accessor.FR<>(fp);
            }
            return new Accessor.PR<>(st, ft, fp);
        }
    };
    /**
     * Property write only access.
     */
    public static final BeanAccess WO = new BeanAccess() {
        @Override
        public <V> Accessor<V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
            final boolean ff = isFinal(fp.getModifiers());
            if (AccessType.FIELD == at) {
                if (ff) {
                    return new Accessor.FN<>(fp);
                }
                return new Accessor.FW<>(fp);
            }
            if (ff) {
                return new Accessor.PN<>(st, ft, fp);
            }
            return new Accessor.PW<>(st, ft, fp);
        }
    };

    private BeanAccess() {
    }

    /**
     * Creates and returns the property accessor depending on the specified {@link AccessType}
     *
     * @param at  the access type
     * @param st  the segment type
     * @param fp  the field property
     * @param ft  the property type
     * @param <V> the property type
     * @return the property accessor
     * @throws BeanException if the property is not accessible for the specified mode
     */
    abstract <V> Accessor<V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException;

}


