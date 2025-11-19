package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.fct.IJoin;
import io.github.up2jakarta.csv.api.fct.ILink;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.core.AccessException;

import java.util.Collection;

import static io.github.up2jakarta.csv.core.BSBuilder.EP;
import static io.github.up2jakarta.lov.core.Beans.cast;
import static java.util.Objects.requireNonNull;

/**
 * Link accessor that's able to link beans of a relationship parent-child (association)
 *
 * @param <T> the child type
 * @param <P> the parent type
 */
public non-sealed abstract class BeanLinker<T extends Segment, P extends Segment> implements ILinker<T, P>, EP {

    public final Class<T> classType;
    public final Class<P> parentType;
    private final ILink<P, T> setter;
    private final IJoin<P, T> getter;

    /**
     * Constructor with the specified arguments.
     *
     * @param parentType the parent type
     * @param type       the child type
     * @param getter     the getter accessor
     * @param setter     the link function
     */
    public BeanLinker(Class<P> parentType, Class<T> type, IJoin<P, T> getter, ILink<P, T> setter) {
        this.getter = requireNonNull(getter, "getter");
        this.setter = requireNonNull(setter, "setter");
        this.classType = requireNonNull(type, "class-type");
        this.parentType = requireNonNull(parentType, "parent-type");
    }

    @Override
    public final Collection<T> from(P parent) {
        return getter.apply(parent);
    }

    @Override
    public final void link(P parent, T child) {
        setter.accept(parent, child);
    }

    /**
     * Simple base implementation of {@link IType} for non-enum implementations.
     */
    public static abstract class Type<B extends DataType<B>, I extends Type<B, I>> extends BeanLinker<Segment, Segment> implements IType<B, I> {
        public final String code;
        public final SeverityType level;

        @SuppressWarnings("unchecked")
        protected <T extends Segment, P extends Segment> Type(Class<P> pt, Class<T> ct, IJoin<P, T> jn, ILink<P, T> ln) {
            super(cast(pt), cast(ct), (IJoin<Segment, Segment>) jn, (ILink<Segment, Segment>) ln);
            final Error config = getError(ct);
            if (config == null) {
                throw new AccessException(ct, "class", "must be annotated with @Error");
            }
            this.level = config.level();
            this.code = config.value();
        }

        private static Error getError(Class<?> type) {
            Error config;
            do {
                config = type.getAnnotation(Error.class);
                type = type.getSuperclass();
            } while (config == null && Segment.class.isAssignableFrom(type));
            return config;
        }

        @Override
        public final String getEventCode() {
            return code;
        }

        @Override
        public final SeverityType getEventLevel() {
            return level;
        }

        @Override
        public final Class<Segment> getClassType() {
            return this.classType;
        }

        @Override
        public final Class<Segment> getParentType() {
            return this.parentType;
        }
    }

}
