package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.core.BSContext.VContext;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.hdl.ComplianceHandler;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;

import static io.github.up2jakarta.csv.core.BSBuilder.MST;
import static io.github.up2jakarta.csv.core.BSManager.Pod;
import static io.github.up2jakarta.csv.core.BSNode.Bean.*;
import static io.github.up2jakarta.csv.core.BSNode.Flat;
import static io.github.up2jakarta.csv.core.ext.Beans.getDefaultConstructor;
import static io.github.up2jakarta.csv.core.ext.Beans.newInstance;
import static io.github.up2jakarta.lov.core.Defaults.EMPTY;
import static io.github.up2jakarta.lov.core.Defaults.prototype;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;

/**
 * Internal business node.
 */
@SuppressWarnings("unchecked")
abstract sealed class BSNode<S extends Segment, D extends DataType<D>> implements MST permits Flat, Bean {
    final List<BSProperty<?, ?, D>> properties;
    final Validator validator;
    final boolean prototype;
    final boolean nullable;
    final VContext context;
    final Class<S> type;
    final int offset;

    BSNode(int i, Class<S> type, Validator v, VContext c, boolean n, boolean p, List<BSProperty<?, ?, D>> ps) {
        this.properties = ps;
        this.offset = i;
        this.validator = v;
        this.prototype = p;
        this.nullable = n;
        this.context = c;
        this.type = type;
    }

    BSNode(BSNode<S, D> source) throws BeanException {
        this.type = source.type;
        this.context = source.context;
        this.nullable = source.nullable;
        this.prototype = source.prototype;
        this.validator = source.validator;
        this.offset = source.offset;
        final List<BSProperty<?, ?, D>> ps = new ArrayList<>(source.properties.size());
        var same = true;
        for (final BSProperty<?, ?, D> p : source.properties) {
            final BSProperty<?, ?, D> r = p.reverse();
            ps.add(r);
            if (p != r) {
                same = false;
            }
        }
        this.properties = (same) ? source.properties : unmodifiableList(ps);
    }

    @SuppressWarnings("RedundantCast")
    private BSProperty<?, ?, D> find(final ConstraintViolation<?> violation) {
        final String path = violation.getPropertyPath().toString();
        final String[] names = path.split("\\.");
        var ps = this.properties;
        for (final String fn : names) {
            var cp = ps.stream().filter(p -> fn.equals(p.getName())).findAny().orElse(null);
            if (cp instanceof PFragment<?, ?, ?> fp) {
                ps = ((PFragment<?, ?, D>) fp).node.properties;
            } else {
                return cp;
            }
        }
        return null;
    }

    void validate(S bean, int offset, ComplianceHandler<D> handler) {
        if (context.enabled) {
            final Set<ConstraintViolation<Object>> violations = validator.validate(bean, context.groups);
            for (final ConstraintViolation<?> cv : violations) {
                final BSProperty<?, ?, D> p = this.find(cv);
                if (p != null) {
                    handler.handle(p.dataType, p.offset + offset, cv, p.error);
                } else {
                    handler.handle(null, null, cv, null);
                }
            }
        }
    }

    abstract BSNode<S, D> reverse() throws BeanException;

    /**
     * Internal Flat Node.
     */
    static final class Flat<S extends Segment, D extends DataType<D>> extends BSNode<S, D> {
        private final String[] cache;
        private final int index;

        Flat(int i, Class<S> t, Validator v, VContext c, Fragment f, List<BSProperty<?, ?, D>> ps) {
            super(i, t, v, c, f.nullable(), f.prototype(), ps);
            if (this.prototype) {
                this.index = Pod.min(this);
                this.cache = this.defaultValues(index);
            } else {
                this.index = -1;
                this.cache = EMPTY;
            }
        }

        Flat(Class<S> type, Validator validator, VContext context, List<BSProperty<?, ?, D>> ps) {
            super(-1, type, validator, context, false, false, ps);
            this.cache = EMPTY;
            this.index = -1;
        }

        private Flat(Bean<S, D, ?> source) throws BeanException {
            super(source);
            if (this.prototype) {
                this.index = Pod.min(this);
                this.cache = this.defaultValues(index);
            } else {
                this.index = -1;
                this.cache = EMPTY;
            }
        }

        private String[] defaultValues(int min) {
            final int length = Pod.max(this) + 1;
            if (length != 0 && min >= 0) {
                final String[] result = new String[length];
                for (final BSProperty<?, ?, D> p : properties) {
                    if (p instanceof PPosition<?, ?, ?> pp) {
                        result[p.offset] = pp.formatted;
                    } else if (p instanceof PFragment<?, ?, ?> fp) {
                        final Flat<Segment, D> node = (Flat<Segment, D>) fp.node;
                        final String[] values = node.cache;
                        if (values != EMPTY) {
                            System.arraycopy(values, 0, result, node.index, values.length);
                        }
                    }
                }
                return prototype(result, min);
            }
            return EMPTY;
        }

        void format(String[] result, int offset, S bean) {
            if (bean != null) {
                for (final BSProperty<?, ?, D> p : properties) {
                    if (p instanceof PPosition<?, ?, ?> pp) {
                        result[offset + p.offset] = pp.toString(bean);
                    } else if (p instanceof PFragment<?, ?, ?> fp) {
                        final Flat<Segment, D> node = (Flat<Segment, D>) fp.node;
                        final Segment value = ((PFragment<Segment, ?, D>) fp).value(bean);
                        node.format(result, offset, value);
                    }
                }
            } else if (cache != EMPTY) {
                System.arraycopy(cache, 0, result, offset + index, cache.length);
            }
        }

        void header(String[] header, int offset) {
            for (final BSProperty<?, ?, D> p : properties) {
                if (p instanceof PFragment<?, ?, ?> fp) {
                    final Flat<Segment, D> node = (Flat<Segment, D>) fp.node;
                    node.header(header, offset);
                } else if (p.dataType != null) {
                    header[offset + p.offset] = p.dataType.getName();
                }
            }
        }

        void validate(ComplianceHandler<D> handler, S bean, int offset) {
            if (bean != null && context.enabled) {
                for (final BSProperty<?, ?, D> property : properties) {
                    if (property instanceof PFragment<?, ?, ?> fp) {
                        final Flat<Segment, D> node = (Flat<Segment, D>) fp.node;
                        final Segment value = ((PFragment<Segment, ?, D>) fp).value(bean);
                        node.validate(handler, value, offset);
                    }
                }
                this.validate(bean, offset, handler);
            }
        }

        @Override
        Bean<S, D, ?> reverse() throws BeanException {
            final Constructor<S> cs = BSContext.from(type, properties);
            if (cs == null) {
                if (type.isRecord()) {
                    return new Bean.JR<>(this);
                }
                return new Bean.BM<>(this);
            }
            return new Bean.JB<>(this, cs);
        }
    }

    /**
     * Internal Mapper Node.
     */
    abstract static sealed class Bean<S extends Segment, D extends DataType<D>, C> extends BSNode<S, D> permits BM, BC {
        protected final Constructor<S> constructor;

        Bean(int i, Class<S> t, Validator v, VContext c, Fragment f, List<BSProperty<?, ?, D>> ps, Constructor<S> cs) {
            super(i, t, v, c, f.nullable(), f.prototype(), ps);
            this.constructor = cs;
        }

        Bean(Class<S> type, Validator validator, VContext context, List<BSProperty<?, ?, D>> ps, Constructor<S> cs) {
            super(-1, type, validator, context, false, false, ps);
            this.constructor = cs;
        }

        private Bean(Flat<S, D> source, Constructor<S> cs) throws BeanException {
            super(source);
            this.constructor = cs;
        }

        protected boolean parse(List<Segment> stack, C args, EventHandler<D> handler, int offset, String... record) {
            if (nullable && offset >= record.length) {
                return true;
            }
            var empty = true;
            for (final BSProperty<?, ?, D> property : properties) {
                if (property instanceof PFragment<?, ?, ?>) {
                    final PFragment<Segment, Object, D> fp = (PFragment<Segment, Object, D>) property;
                    final Segment value = ((Bean<?, D, C>) fp.node).parse(stack, handler, offset, record);
                    this.set(args, fp, fp.wrap(value));
                    if (value != null) {
                        fp.node.validate(value, offset, handler);
                        empty = false;
                    }
                } else {
                    final PPosition<Object, Object, D> pp = (PPosition<Object, Object, D>) property;
                    final int index = property.offset;
                    final String data = (index < record.length) ? record[index] : null;
                    final Object value = pp.parse(data, offset, handler);
                    this.set(args, pp, pp.wrap(value));
                    if (value != null) {
                        empty = false;
                    } else if (nullable && pp.required) {
                        return true;
                    }
                }
            }
            return nullable && empty;
        }

        protected final <R extends IRecord<?>> void update(S target, R source) {
            if (target instanceof Recordable<?> wrapper) {
                try {
                    ((Recordable<R>) wrapper).setRecord(source);
                } catch (Exception cause) {
                    throw new AccessException(target.getClass(), "setRecord", cause);
                }
            }
        }

        @Override
        protected final Flat<S, D> reverse() throws BeanException {
            return new Flat<>(this);
        }

        protected final S parse(EventHandler<D> handler, int offset, String... record) {
            return this.parse(new LinkedList<>(), handler, offset, record);
        }

        protected abstract S parse(List<Segment> stack, EventHandler<D> handler, int offset, String... record);

        protected abstract <V> void set(C bean, BSProperty<?, V, D> property, V value);

        /**
         * Internal Mapper Node for java-beans.
         */
        static final class BM<S extends Segment, D extends DataType<D>> extends Bean<S, D, S> {
            BM(int i, Class<S> t, Validator v, VContext c, Fragment f, List<BSProperty<?, ?, D>> ps) throws BeanException {
                super(i, t, v, c, f, ps, getDefaultConstructor(t));
            }

            BM(Class<S> type, Validator validator, VContext context, List<BSProperty<?, ?, D>> ps) throws BeanException {
                super(type, validator, context, ps, getDefaultConstructor(type));
            }

            BM(Flat<S, D> source) throws BeanException {
                super(source, getDefaultConstructor(source.type));
            }

            private S init(List<? extends Segment> stack) {
                if (offset != -1) {
                    return newInstance(constructor, stack.get(offset));
                }
                return newInstance(constructor);
            }

            @Override
            protected S parse(List<Segment> stack, EventHandler<D> handler, int offset, String... record) {
                final S bean = this.init(stack);
                stack.addLast(bean);
                final boolean empty = this.parse(stack, bean, handler, offset, record);
                stack.removeLast();
                return empty ? null : bean;
            }

            @Override
            protected <V> void set(S bean, BSProperty<?, V, D> property, V value) {
                property.value(bean, value);
            }
        }

        /**
         * Internal Mapper Node for java-beans within constructor setters.
         */
        abstract static sealed class BC<S extends Segment, D extends DataType<D>> extends Bean<S, D, Object[]> permits JR, JB {
            private final Map<BSProperty<?, ?, D>, Integer> indexes;
            private final Object[] prototype;

            BC(int i, Class<S> type, Validator v, VContext c, Fragment f, List<BSProperty<?, ?, D>> ps, Constructor<S> cs) throws BeanException {
                super(i, type, v, c, f, ps, cs);
                this.prototype = prototype(constructor);
                this.indexes = this.indexes();
            }

            BC(Class<S> type, Validator validator, VContext context, List<BSProperty<?, ?, D>> ps, Constructor<S> cs) throws BeanException {
                super(type, validator, context, ps, cs);
                this.prototype = prototype(constructor);
                this.indexes = this.indexes();
            }

            BC(Flat<S, D> source, Constructor<S> cs) throws BeanException {
                super(source, cs);
                this.prototype = prototype(constructor);
                this.indexes = this.indexes();
            }

            abstract Map<BSProperty<?, ?, D>, Integer> indexes() throws BeanException;

            @Override
            protected final S parse(List<Segment> stack, EventHandler<D> handler, int offset, String... record) {
                final Object[] arguments = new Object[prototype.length];
                System.arraycopy(prototype, 0, arguments, 0, prototype.length);
                if (this.offset != -1) {
                    arguments[0] = stack.get(offset);
                }
                stack.addLast(null);
                final boolean empty = this.parse(stack, arguments, handler, offset, record);
                stack.removeLast();
                return empty ? null : newInstance(constructor, arguments);
            }

            @Override
            protected final <V> void set(Object[] arguments, BSProperty<?, V, D> property, V value) {
                if (value != null) {
                    final int index = indexes.get(property);
                    arguments[index] = value;
                }
            }
        }

        /**
         * Internal Mapper Node for java-records.
         */
        static final class JR<S extends Segment, D extends DataType<D>> extends BC<S, D> {

            JR(Class<S> type, Validator v, VContext c, Fragment f, List<BSProperty<?, ?, D>> ps) throws BeanException {
                super(-1, type, v, c, f, ps, getDefaultConstructor(type));
            }

            JR(Class<S> type, Validator validator, VContext context, List<BSProperty<?, ?, D>> ps) throws BeanException {
                super(type, validator, context, ps, getDefaultConstructor(type));
            }

            private JR(Flat<S, D> source) throws BeanException {
                super(source, getDefaultConstructor(source.type));
            }

            @Override
            protected Map<BSProperty<?, ?, D>, Integer> indexes() {
                final Map<BSProperty<?, ?, D>, Integer> indexes = new HashMap<>(properties.size());
                final Parameter[] parameters = constructor.getParameters();
                final Iterator<BSProperty<?, ?, D>> it = properties.iterator();
                for (var i = 0; it.hasNext() && i < parameters.length; ) {
                    final BSProperty<?, ?, D> p = it.next();
                    for (var pm = parameters[i]; !pm.getName().equals(p.getName()) && ++i < parameters.length; ) {
                        pm = parameters[i];
                    }
                    indexes.put(p, i);
                }
                return unmodifiableMap(indexes);
            }
        }

        /**
         * Internal Mapper Node for immutable java-beans.
         */
        static final class JB<S extends Segment, D extends DataType<D>> extends BC<S, D> {

            JB(int i, Class<S> type, Validator v, VContext c, Fragment f, List<BSProperty<?, ?, D>> ps, Constructor<S> cs) throws BeanException {
                super(i, type, v, c, f, ps, cs);
            }

            JB(Class<S> type, Validator validator, VContext context, List<BSProperty<?, ?, D>> ps, Constructor<S> cs) throws BeanException {
                super(type, validator, context, ps, cs);
            }

            private JB(Flat<S, D> source, Constructor<S> cs) throws BeanException {
                super(source, cs);
            }

            @Override
            protected Map<BSProperty<?, ?, D>, Integer> indexes() throws BeanException {
                final Map<BSProperty<?, ?, D>, Integer> indexes = new HashMap<>(properties.size());
                final Parameter[] parameters = constructor.getParameters();
                for (BSProperty<?, ?, D> p : properties) {
                    indexes.put(p, this.index(parameters, p));
                }
                return unmodifiableMap(indexes);
            }

            private int index(Parameter[] parameters, BSProperty<?, ?, D> p) throws BeanException {
                for (var i = 0; i < parameters.length; i++) {
                    final Parameter pm = parameters[i];
                    if (pm.getName().equals(p.getName())) {
                        final Type pt = parameters[i].getParameterizedType();
                        if (!pt.equals(p.getGenericType())) {
                            throw new BeanException(p.getSource(), "should be of type " + pt.getTypeName());
                        }
                        return i;
                    }
                }
                throw new BeanException(p.getSource(), "must be in constructor arguments");
            }
        }
    }

}
