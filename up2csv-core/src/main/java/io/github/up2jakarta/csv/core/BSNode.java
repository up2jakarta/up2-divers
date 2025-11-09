package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.core.hdl.*;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

import static io.github.up2jakarta.csv.core.BSOperator.Processor;
import static io.github.up2jakarta.csv.core.BeanException.of;
import static io.github.up2jakarta.csv.core.ext.Beans.getDefaultConstructor;
import static io.github.up2jakarta.csv.core.ext.Beans.newInstance;
import static io.github.up2jakarta.csv.core.ext.Defaults.EMPTY;
import static io.github.up2jakarta.csv.core.ext.Defaults.prototype;
import static io.github.up2jakarta.csv.core.ext.PPath.getOverride;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

/**
 * Internal business node.
 */
@SuppressWarnings("unchecked")
public abstract sealed class BSNode<S extends Segment, D extends DataType<D>> permits BSNode.BFNode, BSNode.BPNode {

    final List<Property<?, ?, D>> properties;
    final Validator validator;
    final boolean prototype;
    final boolean nullable;
    final BVContext context;
    final Class<S> type;

    BSNode(Class<S> type, Validator validator, BVContext context, boolean nullable, boolean prototype, List<Property<?, ?, D>> properties) {
        this.properties = properties;
        this.validator = validator;
        this.prototype = prototype;
        this.nullable = nullable;
        this.context = context;
        this.type = type;
    }

    BSNode(BSNode<S, D> source) throws BeanException {
        this.type = source.type;
        this.context = source.context;
        this.nullable = source.nullable;
        this.prototype = source.prototype;
        this.validator = source.validator;
        this.properties = BSBuilder.reverse(source.properties);
    }

    public static String[] exclusions() {
        return new String[]{
                Up2Mapper.class.getName(),
                Up2Format.class.getName(),
                BSNode.class.getName(),
                BSBuilder.class.getName(),
                BSOperator.class.getName(),
                BSOperator.class.getName() + "$" + BSOperator.Entry.class.getSimpleName(),
                BSOperator.class.getName() + "$" + BSOperator.Processor.class.getSimpleName(),
                BSOperator.class.getName() + "$" + BSOperator.Getter.class.getSimpleName(),
                BSOperator.class.getName() + "$" + BSOperator.BRGetter.class.getSimpleName(),
                BSOperator.class.getName() + "$" + BSOperator.BUGetter.class.getSimpleName(),
                BSNode.class.getName() + "$" + BSNode.BPNode.class.getSimpleName(),
                BSNode.class.getName() + "$" + BSNode.BFNode.class.getSimpleName(),
                BSNode.class.getName() + "$" + BSNode.BRNode.class.getSimpleName(),
                BSNode.class.getName() + "$" + BSNode.BMNode.class.getSimpleName(),
                BSNode.class.getName() + "$" + BSNode.NProperty.class.getSimpleName(),
                BSNode.class.getName() + "$" + BSNode.PWalker.class.getSimpleName(),
                BSNode.class.getName() + "$" + BSNode.ACWalker.class.getSimpleName(),
                BSNode.class.getName() + "$" + BSNode.BCWalker.class.getSimpleName(),
                BSBuilder.class.getName() + "$" + BSBuilder.PWrapper.class.getSimpleName(),
                BSBuilder.class.getName() + "$" + BSBuilder.LWrapper.class.getSimpleName(),
        };
    }

    private Property<?, ?, D> find(final ConstraintViolation<?> violation) {
        final String path = violation.getPropertyPath().toString();
        final String[] fieldNames = path.split("\\.");
        Property<?, ?, D> property = null;
        List<Property<?, ?, D>> properties = this.properties;
        for (final String fieldName : fieldNames) {
            property = properties.stream().filter(p -> fieldName.equals(p.getName())).findFirst().orElse(null);
            if (property instanceof FProperty<?, ?, ?> fp) {
                //noinspection ALL
                properties = ((FProperty<?, ?, D>) fp).node.properties;
            } else {
                return property;
            }
        }
        return property;
    }

    <E extends IEvent<D>> void validate(S b, int o, EventHandler<?, D, E> h) {
        if (context.enabled) {
            final Set<ConstraintViolation<Object>> violations = validator.validate(b, context.groups);
            for (final ConstraintViolation<?> cv : violations) {
                final Property<?, ?, D> p = this.find(cv);
                if (p != null) {
                    h.handle(p.dataType, p.offset + o, cv, p.error);
                } else {
                    h.handle(null, -1, cv, null);
                }
            }
        }
    }

    public final List<Property<?, ?, D>> toList() {
        return this.properties;
    }

    /**
     * Internal Format Node.
     */
    static final class BFNode<S extends Segment, D extends DataType<D>> extends BSNode<S, D> {
        private final String[] values;
        private final int index;

        BFNode(Class<S> type, Validator validator, BVContext context, Fragment fragment, List<Property<?, ?, D>> ps) throws BeanException {
            super(type, validator, context, fragment.nullable(), fragment.defaultValues(), ps);
            if (nullable) {
                this.index = -1;
                this.values = EMPTY;
            } else {
                this.index = Processor.min(this);
                this.values = this.defaultValues(prototype(type), index);
            }
        }

        BFNode(Class<S> type, Validator validator, BVContext context, List<Property<?, ?, D>> ps) {
            super(type, validator, context, false, false, ps);
            this.values = EMPTY;
            this.index = -1;
        }

        BFNode(BPNode<S, D, ?> source) throws BeanException {
            super(source);
            if (!nullable) {
                this.index = Processor.min(this);
                this.values = this.defaultValues(source.defaultValue(), index);
            } else {
                this.index = -1;
                this.values = EMPTY;
            }
        }

        private String[] defaultValues(S bean, int min) throws BeanException {
            final int length = Processor.max(this) + 1;
            if (length != 0 && min >= 0) {
                final String[] result = new String[length];
                this.format(result, 0, bean, PWalker.getInstance(prototype));
                return prototype(result, min);
            }
            return EMPTY;
        }

        private void format(String[] result, int offset, S bean, PWalker config) throws BeanException {
            if (bean == null && config.test(result, offset, nullable, index, values)) {
                return;
            }
            for (final Property<?, ?, D> p : properties) {
                if (p instanceof PProperty<?, ?, ?> pp) {
                    result[offset + p.offset] = config.format(bean, pp);
                } else if (p instanceof FProperty<?, ?, ?> fp) {
                    final BFNode<Segment, D> node = (BFNode<Segment, D>) fp.node;
                    final Segment value = ((NProperty<Segment, ?, D>) fp).get(bean);
                    node.format(result, offset, value, config);
                }
            }
        }

        void header(String[] header, int offset) {
            for (final Property<?, ?, D> p : properties) {
                if (p instanceof FProperty<?, ?, ?> fp) {
                    final BFNode<Segment, D> node = (BFNode<Segment, D>) fp.node;
                    node.header(header, offset);
                } else if (p.dataType != null) {
                    header[offset + p.offset] = p.dataType.getName();
                }
            }
        }

        void format(String[] result, int offset, S bean) throws BeanException {
            this.format(result, offset, bean, ACWalker.INSTANCE);
        }

        void validate(EventHandler<?, D, ? extends IEvent<D>> handler, S bean, int offset) throws BeanException {
            if (bean != null && context.enabled) {
                for (final Property<?, ?, D> property : properties) {
                    if (property instanceof FProperty<?, ?, ?> fp) {
                        final BFNode<Segment, D> node = (BFNode<Segment, D>) fp.node;
                        final Segment value = ((NProperty<Segment, ?, D>) fp).get(bean);
                        node.validate(handler, value, offset);
                    }
                }
                this.validate(bean, offset, handler);
            }
        }
    }

    /**
     * Internal Mapper Node.
     */
    static abstract sealed class BPNode<S extends Segment, D extends DataType<D>, C> extends BSNode<S, D> permits BMNode, BRNode {
        protected final Constructor<S> constructor;

        BPNode(Class<S> type, Validator validator, BVContext context, Fragment fragment, List<Property<?, ?, D>> ps) throws BeanException {
            super(type, validator, context, fragment.nullable(), fragment.defaultValues(), ps);
            this.constructor = getDefaultConstructor(type);
        }

        BPNode(Class<S> type, Validator validator, BVContext context, List<Property<?, ?, D>> ps) throws BeanException {
            super(type, validator, context, false, false, ps);
            this.constructor = getDefaultConstructor(type);
        }

        BPNode(BFNode<S, D> source) throws BeanException {
            super(source);
            this.constructor = getDefaultConstructor(source.type);
        }

        private S parse(EventHandler<?, D, ? extends IEvent<D>> handler, int offset, String... record) throws BeanException {
            if (nullable && !(offset < record.length)) {
                return null;
            }
            final C argument = this.get();
            var empty = true;
            for (final Property<?, ?, D> property : properties) {
                if (property instanceof FProperty<?, ?, ?>) {
                    final FProperty<Segment, Object, D> fp = (FProperty<Segment, Object, D>) property;
                    final Segment value = ((BPNode<?, D, C>) fp.node).parse(handler, offset, record);
                    if (value != null) {
                        fp.node.validate(value, offset, handler);
                        this.set(argument, fp, fp.wrap(value));
                        empty = false;
                    }
                } else {
                    final PProperty<?, Object, D> pp = (PProperty<?, Object, D>) property;
                    final int index = property.offset;
                    final String data = (index < record.length) ? record[index] : null;
                    final Object value = ((NProperty<?, Object, D>) pp).parse(data, offset, handler);
                    if (value != null) {
                        this.set(argument, pp, value);
                        empty = false;
                    } else if (nullable && pp.required) {
                        return null;
                    }
                }
            }
            return (nullable && empty) ? null : this.get(argument);
        }

        final S map(EventHandler<?, D, ? extends IEvent<D>> handler, int offset, String... record) throws BeanException {
            if (record == null) {
                return null;
            }
            requireNonNull(handler, "handler is required");
            final S bean = this.parse(handler, offset, record);
            this.validate(bean, offset, handler);
            return bean;
        }

        final <R extends IRecord<?>, E extends IEvent<D>> S map(R r, int o, boolean v, EventHandler<R, D, E> h) throws BeanException {
            if (r == null || r.getColumns() == null) {
                return null;
            }
            requireNonNull(h, "handler is required");
            if (!(h instanceof FastHandler<?>) && h.row != r) {
                throw new BeanException(EventHandler.class, "source", "does not match with row argument");
            }
            final S bean = this.parse(h, o, r.getColumns());
            if (bean instanceof Recordable<?> wrapper) {
                try {
                    ((Recordable<R>) wrapper).setRecord(r);
                } catch (RuntimeException ex) {
                    throw new BeanException(bean.getClass(), "record", ex.getMessage());
                }
            }
            if (v) {
                this.validate(bean, o, h);
            }
            return bean;
        }

        final S defaultValue() throws BeanException {
            return this.get(this.get());
        }

        abstract C get() throws BeanException;

        abstract S get(C bean) throws BeanException;

        abstract <V> void set(C bean, Property<?, V, D> property, V value) throws BeanException;
    }

    /**
     * Internal Mapper Node for java-beans.
     */
    static final class BMNode<S extends Segment, D extends DataType<D>> extends BPNode<S, D, S> {

        BMNode(Class<S> type, Validator validator, BVContext context, Fragment fragment, List<Property<?, ?, D>> ps) throws BeanException {
            super(type, validator, context, fragment, ps);
        }

        BMNode(Class<S> type, Validator validator, BVContext context, List<Property<?, ?, D>> ps) throws BeanException {
            super(type, validator, context, ps);
        }

        BMNode(BFNode<S, D> source) throws BeanException {
            super(source);
        }

        @Override
        S get() throws BeanException {
            return newInstance(constructor);
        }

        @Override
        S get(S bean) throws BeanException {
            return bean;
        }

        @Override
        <V> void set(S bean, Property<?, V, D> property, V value) throws BeanException {
            ((NProperty<?, V, D>) property).value(bean, value);
        }
    }

    /**
     * Internal Mapper Node for java-records.
     */
    static final class BRNode<S extends Segment, D extends DataType<D>> extends BPNode<S, D, Object[]> {
        private final Map<Property<?, ?, D>, Integer> indexes;
        private final Object[] prototype;

        BRNode(Class<S> type, Validator validator, BVContext context, Fragment fragment, List<Property<?, ?, D>> ps) throws BeanException {
            super(type, validator, context, fragment, ps);
            this.prototype = prototype(constructor);
            this.indexes = this.indexes(type);
        }

        BRNode(Class<S> type, Validator validator, BVContext context, List<Property<?, ?, D>> ps) throws BeanException {
            super(type, validator, context, ps);
            this.prototype = prototype(constructor);
            this.indexes = this.indexes(type);
        }

        BRNode(BFNode<S, D> source) throws BeanException {
            super(source);
            this.prototype = prototype(constructor);
            this.indexes = this.indexes(source.type);
        }

        private Map<Property<?, ?, D>, Integer> indexes(Class<S> type) {
            final Map<Property<?, ?, D>, Integer> indexes = new HashMap<>(properties.size());
            final Field[] fields = type.getDeclaredFields();
            final Iterator<Property<?, ?, D>> it = properties.iterator();
            for (var i = 0; it.hasNext() && i < fields.length; ) {
                final Property<?, ?, D> property = it.next();
                for (var field = fields[i]; !field.equals(property.getSource()) && ++i < fields.length; ) {
                    field = fields[i];
                }
                indexes.put(property, i);
            }
            return unmodifiableMap(indexes);
        }

        @Override
        Object[] get() {
            final Object[] arguments = new Object[prototype.length];
            System.arraycopy(prototype, 0, arguments, 0, prototype.length);
            return arguments;
        }

        @Override
        S get(Object[] arguments) throws BeanException {
            return newInstance(constructor, arguments);
        }

        @Override
        <V> void set(Object[] arguments, Property<?, V, D> property, V value) {
            final int index = indexes.get(property);
            arguments[index] = value;
        }
    }

    /**
     * Internal Context for JSR-303 validation.
     */
    static class BVContext {

        public static final BVContext DISABLED = new BVContext(false);
        public static final BVContext DEFAULT = new BVContext(true);

        final boolean enabled;
        final Class<?>[] groups;

        private BVContext(boolean enabled, Class<?>... groups) {
            this.enabled = enabled;
            this.groups = groups;
        }

        private static void checkGroups(ValidOverride valid, AnnotatedElement source) throws BeanException {
            if (source.isAnnotationPresent(Valid.class)) {
                throw of(source, "must not be annotated by @Valid");
            }
            for (final Class<?> group : valid.groups()) {
                if (!group.isInterface()) {
                    throw of(source, "@ValidOverride[value = " + group.getName() + ".class must be an interface]");
                }
            }
        }

        static BVContext from(Class<? extends Segment> type) throws BeanException {
            while (type != Segment.class && Segment.class.isAssignableFrom(type)) {
                final ValidOverride override = getOverride(ValidOverride.class, type, ValidOverride::path);
                if (override != null) {
                    if (override.disable()) {
                        return DISABLED;
                    }
                    if (override.groups().length == 0) {
                        return DEFAULT;
                    }
                    checkGroups(override, type);
                    return new BVContext(true, override.groups());
                }
                if (type.isAnnotationPresent(Valid.class)) {
                    return DEFAULT;
                }
                //noinspection unchecked
                type = (Class<? extends Segment>) type.getSuperclass();
            }
            return DISABLED;
        }

        BVContext build(Field field, ValidOverride override) throws BeanException {
            if (enabled && override != null && !override.disable()) {
                checkGroups(override, field);
                return new BVContext(true, override.groups());
            }
            return DISABLED;
        }

    }

    /**
     * Internal property representation.
     */
    public static abstract class NProperty<T, V, D extends DataType<D>> {

        protected abstract V value(Object bean, V value) throws BeanException;

        protected abstract V value(Segment bean) throws BeanException;

        protected abstract String format(V value) throws BeanException;

        protected abstract V parse(String value, int offset, EventHandler<?, D, ?> handler) throws BeanException;

        protected abstract V wrap(T value);

        protected abstract T from(V value);

        final T get(Segment bean) throws BeanException {
            return this.from(this.value(bean));
        }
    }

    /**
     * Internal Format Walker.
     */
    private abstract sealed static class PWalker permits ACWalker, BCWalker {
        static PWalker getInstance(boolean prototype) {
            if (prototype) {
                return BCWalker.ENABLED;
            }
            return BCWalker.DISABLED;
        }

        abstract boolean test(String[] result, int offset, boolean nullable, int index, String[] values);

        abstract <T, V> String format(Segment bean, PProperty<T, V, ?> property) throws BeanException;
    }

    /**
     * After construction walker
     */
    private static final class ACWalker extends PWalker {
        private static final ACWalker INSTANCE = new ACWalker();

        private ACWalker() {
        }

        @Override
        boolean test(String[] result, int offset, boolean nullable, int index, String[] values) {
            if (!(nullable || values.length == 0)) {
                System.arraycopy(values, 0, result, offset + index, values.length);
            }
            return true;
        }

        @Override
        <T, V> String format(Segment bean, PProperty<T, V, ?> pp) throws BeanException {
            final V value = ((NProperty<T, V, ?>) pp).value(bean);
            return ((NProperty<T, V, ?>) pp).format(value);
        }
    }

    /**
     * Before construction walker
     */
    private static final class BCWalker extends PWalker {
        private static final BCWalker ENABLED = new BCWalker(true);
        private static final BCWalker DISABLED = new BCWalker(false);

        private final boolean enabled;

        private BCWalker(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        boolean test(String[] result, int offset, boolean nullable, int index, String[] values) {
            if (nullable || values == null || values.length == 0) {
                return !enabled;
            }
            System.arraycopy(values, 0, result, offset + index, values.length);
            return true;
        }

        @Override
        <T, V> String format(Segment bean, PProperty<T, V, ?> pp) {
            try {
                final V dv = pp.defaultValue;
                if (enabled && pp.from(dv) != null) {
                    return ((NProperty<T, V, ?>) pp).format(dv);
                }
                final V value = ((NProperty<T, V, ?>) pp).value(bean);
                return ((NProperty<T, V, ?>) pp).format(value);
            } catch (Exception ignore) {
                return null;
            }
        }
    }
}
