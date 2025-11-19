package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.core.BSContext.BVContext;
import io.github.up2jakarta.csv.core.BSProperty.FProperty;
import io.github.up2jakarta.csv.core.BSProperty.PProperty;
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
import java.lang.reflect.Field;
import java.util.*;

import static io.github.up2jakarta.csv.core.BSBuilder.ST;
import static io.github.up2jakarta.csv.core.BSNode.BFNode;
import static io.github.up2jakarta.csv.core.BSNode.BPNode;
import static io.github.up2jakarta.csv.core.BSNode.BPNode.BMNode;
import static io.github.up2jakarta.csv.core.BSNode.BPNode.BRNode;
import static io.github.up2jakarta.csv.core.BSOperator.BProcessor;
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
abstract sealed class BSNode<S extends Segment, D extends DataType<D>> implements ST permits BFNode, BPNode {
    final List<BSProperty<?, ?, D>> properties;
    final Validator validator;
    final boolean prototype;
    final boolean nullable;
    final BVContext context;
    final Class<S> type;
    final int offset;

    BSNode(int i, Class<S> type, Validator v, BVContext c, boolean n, boolean p, List<BSProperty<?, ?, D>> ps) {
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
        for (final BSProperty<?, ?, D> p : source.properties) {
            ps.add(p.reverse());
        }
        this.properties = unmodifiableList(ps);
    }

    @SuppressWarnings("ALL")
    private BSProperty<?, ?, D> find(final ConstraintViolation<?> violation) {
        final String path = violation.getPropertyPath().toString();
        final String[] fieldNames = path.split("\\.");
        BSProperty<?, ?, D> property = null;
        List<BSProperty<?, ?, D>> properties = this.properties;
        for (final String fieldName : fieldNames) {
            property = properties.stream().filter(p -> fieldName.equals(p.getName())).findFirst().orElse(null);
            if (property instanceof FProperty<?, ?, ?> fp) {
                properties = ((FProperty<?, ?, D>) fp).node.properties;
            } else {
                return property;
            }
        }
        return property;
    }

    void validate(S b, int o, ComplianceHandler<D> h) {
        if (context.enabled) {
            final Set<ConstraintViolation<Object>> violations = validator.validate(b, context.groups);
            for (final ConstraintViolation<?> cv : violations) {
                final BSProperty<?, ?, D> p = this.find(cv);
                if (p != null) {
                    h.handle(p.dataType, p.offset + o, cv, p.error);
                } else {
                    h.handle(null, null, cv, null);
                }
            }
        }
    }

    abstract BSNode<S, D> reverse() throws BeanException;

    /**
     * Internal Format Node.
     */
    static final class BFNode<S extends Segment, D extends DataType<D>> extends BSNode<S, D> {
        private final String[] values;
        private final int index;

        BFNode(int i, Class<S> t, Validator v, BVContext c, Fragment f, List<BSProperty<?, ?, D>> ps) {
            super(i, t, v, c, f.nullable(), f.prototype(), ps);
            if (this.prototype) {
                this.index = BProcessor.min(this);
                this.values = this.defaultValues(index);
            } else {
                this.index = -1;
                this.values = EMPTY;
            }
        }

        BFNode(Class<S> type, Validator validator, BVContext context, List<BSProperty<?, ?, D>> ps) {
            super(-1, type, validator, context, false, false, ps);
            this.values = EMPTY;
            this.index = -1;
        }

        private BFNode(BPNode<S, D, ?> source) throws BeanException {
            super(source);
            if (this.prototype) {
                this.index = BProcessor.min(this);
                this.values = this.defaultValues(index);
            } else {
                this.index = -1;
                this.values = EMPTY;
            }
        }

        private String[] defaultValues(int min) {
            final int length = BProcessor.max(this) + 1;
            if (length != 0 && min >= 0) {
                final String[] result = new String[length];
                this.format(result, 0, null);
                return prototype(result, min);
            }
            return EMPTY;
        }

        void format(String[] result, int offset, S bean) {
            if (bean != null || values == null) {
                for (final BSProperty<?, ?, D> p : properties) {
                    if (p instanceof PProperty<?, ?, ?> pp) {
                        result[offset + p.offset] = pp.getFormatted(bean);
                    } else if (p instanceof FProperty<?, ?, ?> fp) {
                        final BFNode<Segment, D> node = (BFNode<Segment, D>) fp.node;
                        final Segment value = ((FProperty<Segment, ?, D>) fp).getUnwrapped(bean);
                        node.format(result, offset, value);
                    }
                }
            } else if (values != EMPTY) {
                System.arraycopy(values, 0, result, offset + index, values.length);
            }
        }

        void header(String[] header, int offset) {
            for (final BSProperty<?, ?, D> p : properties) {
                if (p instanceof FProperty<?, ?, ?> fp) {
                    final BFNode<Segment, D> node = (BFNode<Segment, D>) fp.node;
                    node.header(header, offset);
                } else if (p.dataType != null) {
                    header[offset + p.offset] = p.dataType.getName();
                }
            }
        }

        void validate(ComplianceHandler<D> handler, S bean, int offset) {
            if (bean != null && context.enabled) {
                for (final BSProperty<?, ?, D> property : properties) {
                    if (property instanceof FProperty<?, ?, ?> fp) {
                        final BFNode<Segment, D> node = (BFNode<Segment, D>) fp.node;
                        final Segment value = ((FProperty<Segment, ?, D>) fp).getUnwrapped(bean);
                        node.validate(handler, value, offset);
                    }
                }
                this.validate(bean, offset, handler);
            }
        }

        @Override
        BPNode<S, D, ?> reverse() throws BeanException {
            if (type.isRecord()) {
                return new BRNode<>(this);
            } else {
                return new BMNode<>(this);
            }
        }
    }

    /**
     * Internal Mapper Node.
     */
    static abstract sealed class BPNode<S extends Segment, D extends DataType<D>, C> extends BSNode<S, D> permits BMNode, BRNode {
        protected final Constructor<S> constructor;

        BPNode(int i, Class<S> t, Validator v, BVContext c, Fragment f, List<BSProperty<?, ?, D>> ps) throws BeanException {
            super(i, t, v, c, f.nullable(), f.prototype(), ps);
            this.constructor = getDefaultConstructor(t);
        }

        BPNode(Class<S> type, Validator validator, BVContext context, List<BSProperty<?, ?, D>> ps) throws BeanException {
            super(-1, type, validator, context, false, false, ps);
            this.constructor = getDefaultConstructor(type);
        }

        private BPNode(BFNode<S, D> source) throws BeanException {
            super(source);
            this.constructor = getDefaultConstructor(source.type);
        }

        protected boolean parse(List<Segment> stack, C args, EventHandler<D> hdl, int offset, String... record) {
            if (nullable && !(offset < record.length)) {
                return true;
            }
            var empty = true;
            for (final BSProperty<?, ?, D> property : properties) {
                if (property instanceof FProperty<?, ?, ?>) {
                    final FProperty<Segment, Object, D> fp = (FProperty<Segment, Object, D>) property;
                    final Segment value = ((BPNode<?, D, C>) fp.node).parse(stack, hdl, offset, record);
                    this.set(args, fp, fp.wrap(value));
                    if (value != null) {
                        fp.node.validate(value, offset, hdl);
                        empty = false;
                    }
                } else {
                    final PProperty<?, Object, D> pp = (PProperty<?, Object, D>) property;
                    final int index = property.offset;
                    final String data = (index < record.length) ? record[index] : null;
                    final Object value = pp.parse(data, offset, hdl);
                    if (value != null) {
                        this.set(args, pp, value);
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
        protected final BFNode<S, D> reverse() throws BeanException {
            return new BFNode<>(this);
        }

        protected final S parse(EventHandler<D> handler, int offset, String... record) {
            return this.parse(new Stack<>(), handler, offset, record);
        }

        protected abstract S parse(List<Segment> stack, EventHandler<D> handler, int offset, String... record);

        protected abstract <V> void set(C bean, BSProperty<?, V, D> property, V value);

        /**
         * Internal Mapper Node for java-beans.
         */
        static final class BMNode<S extends Segment, D extends DataType<D>> extends BPNode<S, D, S> {
            BMNode(int i, Class<S> t, Validator v, BVContext c, Fragment f, List<BSProperty<?, ?, D>> ps) throws BeanException {
                super(i, t, v, c, f, ps);
            }

            BMNode(Class<S> type, Validator validator, BVContext context, List<BSProperty<?, ?, D>> ps) throws BeanException {
                super(type, validator, context, ps);
            }

            BMNode(BFNode<S, D> source) throws BeanException {
                super(source);
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
         * Internal Mapper Node for java-records.
         */
        static final class BRNode<S extends Segment, D extends DataType<D>> extends BPNode<S, D, Object[]> {
            private final Map<BSProperty<?, ?, D>, Integer> indexes;
            private final Object[] prototype;

            BRNode(Class<S> type, Validator v, BVContext c, Fragment f, List<BSProperty<?, ?, D>> ps) throws BeanException {
                super(-1, type, v, c, f, ps);
                this.prototype = prototype(constructor);
                this.indexes = this.indexes(type);
            }

            BRNode(Class<S> type, Validator validator, BVContext context, List<BSProperty<?, ?, D>> ps) throws BeanException {
                super(type, validator, context, ps);
                this.prototype = prototype(constructor);
                this.indexes = this.indexes(type);
            }

            private BRNode(BFNode<S, D> source) throws BeanException {
                super(source);
                this.prototype = prototype(constructor);
                this.indexes = this.indexes(source.type);
            }

            private Map<BSProperty<?, ?, D>, Integer> indexes(Class<S> type) {
                final Map<BSProperty<?, ?, D>, Integer> indexes = new HashMap<>(properties.size());
                final Field[] fields = type.getDeclaredFields();
                final Iterator<BSProperty<?, ?, D>> it = properties.iterator();
                for (var i = 0; it.hasNext() && i < fields.length; ) {
                    final BSProperty<?, ?, D> property = it.next();
                    for (var field = fields[i]; !field.equals(property.getSource()) && ++i < fields.length; ) {
                        field = fields[i];
                    }
                    indexes.put(property, i);
                }
                return unmodifiableMap(indexes);
            }

            @Override
            protected S parse(List<Segment> stack, EventHandler<D> handler, int offset, String... record) {
                final Object[] arguments = new Object[prototype.length];
                System.arraycopy(prototype, 0, arguments, 0, prototype.length);
                stack.addLast(null);
                final boolean empty = this.parse(stack, arguments, handler, offset, record);
                stack.removeLast();
                return empty ? null : newInstance(constructor, arguments);
            }

            @Override
            protected <V> void set(Object[] arguments, BSProperty<?, V, D> property, V value) {
                final int index = indexes.get(property);
                arguments[index] = value;
            }
        }
    }

}
