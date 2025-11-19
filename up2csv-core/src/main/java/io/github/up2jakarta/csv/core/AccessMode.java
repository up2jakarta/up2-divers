package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.core.BSProperty.PAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor.PFAccessor.FRAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor.PFAccessor.FWAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor.PPAccessor.PNAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor.PPAccessor.PRAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor.PPAccessor.PWAccessor;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.persistence.AccessType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Defines access modes used to test the accessibility of a property.
 */
public abstract class AccessMode implements CodeList<AccessMode> {

    /**
     * Property read only access.
     */
    public static final AccessMode RO = new AccessMode("RO", "Read only") {
        @Override
        public <V> PAccessor<Field, V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
            if (AccessType.FIELD == at) {
                return new FRAccessor<>(fp);
            }
            return new PRAccessor<>(st, ft, fp);
        }
    };
    /**
     * Property write only access.
     */
    public static final AccessMode WO = new AccessMode("WO", "Write only") {
        @Override
        public <V> PAccessor<Field, V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException {
            if (st.isRecord()) {
                return new PNAccessor<>(st, ft, fp);
            }
            if (Modifier.isFinal(fp.getModifiers())) {
                throw new BeanException(fp, "must not be final");
            }
            if (AccessType.FIELD == at) {
                return new FWAccessor<>(fp);
            }
            return new PWAccessor<>(st, ft, fp);
        }
    };

    private final String name;
    private final String code;

    private AccessMode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public final String getCode() {
        return code;
    }

    @Override
    public final String getName() {
        return name;
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
    abstract <V> PAccessor<Field, V> of(AccessType at, Class<? extends Segment> st, Field fp, Class<V> ft) throws BeanException;

}


