package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.AccessType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Defines access modes used to test the accessibility of a property.
 */
public abstract class PAMode {

    /**
     * Property read access.
     */
    public static final PAMode RO = new PAMode() {
        @Override
        public <V> PAccessor<Field, V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
            if (AccessType.FIELD == at) {
                return new PFAccessor.ROAccess<>(ft, fp);
            }
            return new PPAccessor.ROAccess<>(st, ft, fp);
        }
    };
    /**
     * Property write access.
     */
    public static final PAMode WO = new PAMode() {
        @Override
        public <V> PAccessor<Field, V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
            if (st.isRecord()) {
                return new PPAccessor.NOAccess<>(st, ft, fp);
            }
            if (Modifier.isFinal(fp.getModifiers())) {
                throw new BeanException(fp, "must not be final");
            }
            if (AccessType.FIELD == at) {
                return new PFAccessor.WOAccess<>(ft, fp);
            }
            return new PPAccessor.WOAccess<>(st, ft, fp);
        }
    };

    private PAMode() {
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
    public abstract <V> PAccessor<Field, V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException;

}


