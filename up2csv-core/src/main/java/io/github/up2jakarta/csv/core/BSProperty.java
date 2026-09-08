package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.ReferenceId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.api.hdl.EventLevel;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Processor;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSBuilder.MST;
import io.github.up2jakarta.csv.core.BSOperator.PId;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.hdl.EventHandler;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Wrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_PROCESSOR;
import static io.github.up2jakarta.csv.hdl.FastHandler.of;
import static io.github.up2jakarta.csv.prc.DefaultProcessor.undefined;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.lov.core.Beans.getTypeArgument;
import static io.github.up2jakarta.lov.core.Beans.getTypeName;
import static io.github.up2jakarta.lov.core.Localizable.CREATOR;
import static java.util.Arrays.asList;

/**
 * Internal {@link Segment} property.
 */
abstract sealed class BSProperty<T, D extends ITerm<D>> implements MST permits PFragment, PPosition {
    protected final int offset;
    protected final D dataType;
    protected final Error error;
    private final BSAccessor<T> access;

    private BSProperty(BSAccessor<T> access, D dataType, int offset) {
        this.error = access.source.getAnnotation(Error.class);
        this.dataType = dataType;
        this.access = access;
        this.offset = offset;
    }

    BSProperty(BSProperty<T, D> source, BSAccessor<T> access) {
        this.dataType = source.dataType;
        this.offset = source.offset;
        this.error = source.error;
        this.access = access;
    }

    BSProperty(BSProperty<T, D> source) throws BeanException {
        this(source, source.access.reverse(null));
    }

    BSProperty(BSAccessor<T> access, D dataType, int offset, Fragment fp) {
        this(access, dataType, offset + fp.value());
    }

    BSProperty(BSAccessor<T> access, D dt, int fo, Position pp) {
        this(access, dt, fo + pp.value());
    }

    static <B extends ITerm<B>> PId<B> toPId(Mode mode, PFragment<Segment, B>[] fs, PPosition<Object, B> pp) throws BeanException {
        final BSAccessor<Object> accessor = ((BSProperty<Object, B>) pp).access;
        final List<PFragment<Segment, B>> path;
        final PPosition<Object, B> getter;
        final BSAccessor<Object> setter;
        if (mode == Mode.WO) {
            setter = accessor;
            getter = pp.reverse();
            if (fs.length != 0) {
                final List<PFragment<Segment, B>> ps = new ArrayList<>(fs.length);
                for (final PFragment<Segment, B> fp : fs) {
                    ps.add(fp.reverse());
                }
                path = List.copyOf(ps);
            } else {
                path = List.of();
            }
        } else {
            getter = pp;
            path = asList(fs);
            if (pp.isReversible()) {
                setter = accessor.reverse(Mode.WO);
            } else {
                setter = accessor;
            }
        }
        return PId.of(path, getter, setter);
    }

    abstract Class<T> getType();

    abstract BSProperty<T, D> reverse() throws BeanException;

    final ReferenceId getReferenceId() {
        return access.source.getAnnotation(ReferenceId.class);
    }

    @SafeVarargs
    final boolean isAnnotationAbsent(Class<? extends Annotation>... types) {
        return !BSBuilder.isAnnotationPresent(access.source, types);
    }

    final Member getSource() {
        return access.source;
    }

    final boolean isReversible() {
        return access.isReversible();
    }

    final IParameter toParameter(Class<? extends Segment> st, Type type, int index) throws BeanException {
        final Type gt = access.source.getGenericType();
        if ((gt instanceof ParameterizedType pt) && IParameter.TYPES.contains(pt.getRawType())) {
            final Type rt = pt.getRawType();
            final Type at = pt.getActualTypeArguments()[0];
            if (type.equals(at) || this.getType() == type) {
                return new SParameter(index);
            }
            if (type instanceof ParameterizedType wt) {
                final Type ct = wt.getActualTypeArguments()[0];
                if ((wt.getRawType() == rt) && (ct == at || this.getType() == ct)) {
                    return (rt == Optional.class) ? new OParameter(index) : new WParameter(index);
                }
            }
            throw new BeanException(st, CREATOR, this.getName() + " should be of type " + gt.getTypeName());
        }
        if (type.equals(gt) || ((gt instanceof TypeVariable<?>) && type == this.getType())) {
            return new SParameter(index);
        }
        throw new BeanException(st, CREATOR, this.getName() + " should be of type " + gt.getTypeName());
    }

    final boolean isFinal() {
        return Modifier.isFinal(access.source.getModifiers());
    }

    final String getName() {
        return access.source.getName();
    }

    final T value(Segment bean) {
        return access.value(bean);
    }

    final void value(Segment bean, T value) {
        access.value(bean, value);
    }

    @Override
    public final int hashCode() {
        return offset;
    }

    @Override
    public final String toString() {
        return this.getName();
    }

    /**
     * Internal {@link Fragment} property
     */
    static final class PFragment<S extends Segment, B extends ITerm<B>> extends BSProperty<S, B> {
        final BSNode<S, B> node;

        PFragment(BSNode<S, B> node, BSAccessor<S> va, B type, int offset, Fragment pf) {
            super(va, type, offset, pf);
            this.node = node;
        }

        private PFragment(BSNode<S, B> node, PFragment<S, B> source) throws BeanException {
            super(source);
            this.node = node;
        }

        @Override
        Class<S> getType() {
            return node.type;
        }

        @Override
        PFragment<S, B> reverse() throws BeanException {
            return new PFragment<>(node.reverse(), this);
        }
    }

    /**
     * Internal {@link Position} property
     */
    static final class PPosition<T, D extends ITerm<D>> extends BSProperty<T, D> {
        final T defaultValue;
        final boolean required;
        private final String sequenceValue;
        private final TypeAdapter<T> adapter;
        private final IProcessor<D> processor;

        PPosition(BSAccessor<T> va, D dt, int fo, Position pp, IProcessor<D> ps, TypeAdapter<T> pa) throws BeanException {
            super(va, dt, fo, pp);
            this.adapter = pa;
            this.processor = ps;
            this.required = pp.required();
            this.defaultValue = this.defaultValue();
            this.sequenceValue = this.sequenceValue();
        }

        private PPosition(PPosition<T, D> source) throws BeanException {
            super(source);
            this.sequenceValue = source.sequenceValue;
            this.defaultValue = source.defaultValue;
            this.processor = source.processor;
            this.required = source.required;
            this.adapter = source.adapter;
        }

        private T defaultValue() throws BeanException {
            try {
                final String value = processor.process(null, 0, this, of(ERROR));
                return (value != null) ? this.adapter.parse(value) : null;
            } catch (Exception cause) {
                throw new BeanException(this.getSource(), "@Position[defaultValue] cannot be parsed");
            }
        }

        private String sequenceValue() throws BeanException {
            try {
                return (defaultValue != null) ? adapter.format(defaultValue) : null;
            } catch (Exception cause) {
                throw new BeanException(this.getSource(), "@Position[defaultValue] cannot be formatted");
            }
        }

        @Override
        Class<T> getType() {
            return this.adapter.getType();
        }

        String format(T value) {
            if (value != null && !value.equals(defaultValue)) {
                return adapter.format(value);
            }
            return sequenceValue;
        }

        T parse(String data, int offset, EventHandler<D> handler) {
            if (data != null) {
                data = processor.process(data, offset, this, handler);
            } else {
                return defaultValue;
            }
            if (data != null) {
                try {
                    return adapter.parse(data);
                } catch (RuntimeException cause) {
                    handler.handle(this.error, this.dataType, offset + this.offset, cause);
                }
            }
            return null;
        }

        @Override
        PPosition<T, D> reverse() throws BeanException {
            if (this.isReversible()) {
                return new PPosition<>(this);
            }
            return this;
        }
    }

    /**
     * Internal {@link Constructor} Parameter
     */
    static abstract sealed class IParameter permits OParameter, SParameter, WParameter {
        static final List<Type> TYPES = List.of(Optional.class, Wrapper.class);
        final int index;

        private IParameter(int index) {
            this.index = index;
        }

        static IParameter of(Class<?> type, int index) {
            if (type == Optional.class) {
                return new OParameter(index);
            }
            if (type == Wrapper.class) {
                return new WParameter(index);
            }
            return new SParameter(index);
        }

        abstract Object wrap(Object argument);
    }

    /**
     * Internal {@link Object} Parameter
     */
    private static final class SParameter extends IParameter {
        private SParameter(int index) {
            super(index);
        }

        Object wrap(Object argument) {
            return argument;
        }
    }

    /**
     * Internal {@link Optional} Parameter
     */
    private static final class OParameter extends IParameter {
        private OParameter(int index) {
            super(index);
        }

        Object wrap(Object argument) {
            return Optional.ofNullable(argument);
        }
    }

    /**
     * Internal {@link Wrapper} Parameter
     */
    private static final class WParameter extends IParameter {
        private WParameter(int index) {
            super(index);
        }

        Object wrap(Object argument) {
            return new Wrapper<>(argument);
        }
    }

    /**
     * Internal Input Processor
     */
    abstract static sealed class IProcessor<D extends ITerm<D>> implements MST permits SProcessor, MProcessor, NProcessor {

        private static <D extends ITerm<D>> SProcessor<?, D> build(Container ctx, Annotation ppa) throws BeanException {
            final Class<? extends Annotation> type = ppa.annotationType();
            final Processor processor = type.getAnnotation(Processor.class);
            final Class<? extends InputProcessor<?>> pType = processor.value();
            final Class<?> support = getTypeArgument(pType, InputProcessor.class, 0, void.class);
            if (type != support) {
                final CharSequence cn = getTypeName(type);
                throw new BeanException(type, "@Processor[value] must implements InputProcessor<" + cn + ">");
            }
            final InputProcessor<Annotation> delegate = Container.from(ctx, pType, processor.name());
            return new SProcessor<>(delegate, processor.skip(), ppa);
        }

        static <D extends ITerm<D>> IProcessor<D> build(Container ctx, Field pf, Position pc) throws BeanException {
            final List<SProcessor<?, D>> result = new LinkedList<>();
            if (!undefined(pc)) {
                result.addFirst(build(ctx, pc));
            }
            for (final Annotation ppa : pf.getAnnotations()) {
                final Class<? extends Annotation> type = ppa.annotationType();
                if (type != Position.class && type.isAnnotationPresent(Processor.class)) {
                    result.addLast(build(ctx, ppa));
                }
            }
            if (result.isEmpty()) {
                //noinspection unchecked
                return (IProcessor<D>) NProcessor.INSTANCE;
            }
            if (result.size() == 1) {
                return result.getFirst();
            }
            return new MProcessor<>(result);
        }

        abstract String process(String value, int offset, PPosition<?, D> property, EventHandler<D> handler);
    }

    /**
     * Internal Multiple Processor
     */
    private static final class MProcessor<D extends ITerm<D>> extends IProcessor<D> {
        private final List<SProcessor<?, D>> processors;

        private MProcessor(List<SProcessor<?, D>> processors) {
            this.processors = List.copyOf(processors);
        }

        @Override
        public String process(String value, int offset, PPosition<?, D> property, EventHandler<D> handler) {
            for (final SProcessor<?, D> processor : processors) {
                value = processor.process(value, offset, property, handler);
            }
            return value;
        }
    }

    /**
     * Internal Single Processor
     */
    private static final class SProcessor<A extends Annotation, D extends ITerm<D>> extends IProcessor<D> {
        private final A config;
        private final InputProcessor<A> delegate;
        private final Class<? extends RuntimeException> skip;

        private SProcessor(InputProcessor<A> delegate, Class<? extends RuntimeException> skip, A config) {
            this.delegate = delegate;
            this.config = config;
            this.skip = skip;
        }

        @Override
        public String process(String value, int offset, PPosition<?, D> property, EventHandler<D> handler) {
            try {
                return delegate.process(value, config);
            } catch (RuntimeException cause) {
                offset += property.offset;
                if (property.error != null) {
                    handler.handle(property.error, property.dataType, offset, cause);
                } else {
                    final EventLevel level = () -> skip.isInstance(cause) ? WARNING : ERROR;
                    handler.handle(level, () -> EC_PROCESSOR, property.dataType, offset, cause);
                }
                return value;
            }
        }
    }

    /**
     * Internal None Processor
     */
    private static final class NProcessor<D extends ITerm<D>> extends IProcessor<D> {
        private static final NProcessor<?> INSTANCE = new NProcessor<>();

        private NProcessor() {
        }

        @Override
        public String process(String value, int offset, PPosition<?, D> property, EventHandler<D> handler) {
            return value;
        }
    }
}
