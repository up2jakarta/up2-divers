package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.Recordable;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.core.BSAccessor.Input;
import io.github.up2jakarta.csv.core.BSBuilder.BCR;
import io.github.up2jakarta.csv.core.BSBuilder.MST;
import io.github.up2jakarta.csv.core.BSContext.VContext;
import io.github.up2jakarta.csv.core.BSManager.Pod;
import io.github.up2jakarta.csv.core.BSOperator.PId;
import io.github.up2jakarta.csv.core.BSProperty.IParameter;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.BeanAccessor.ICreator;
import io.github.up2jakarta.csv.hdl.ComplianceHandler;
import io.github.up2jakarta.csv.hdl.EventHandler;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Consumer;

import static io.github.up2jakarta.lov.core.Defaults.EMPTY;
import static io.github.up2jakarta.lov.core.Defaults.prototype;

/**
 * Internal Business Node
 */
abstract sealed class BSNode<S extends Segment, D extends ITerm<D>> implements MST permits BSNode.Flat, BSNode.Bean {
    final List<BSProperty<?, D>> properties;
    final List<Field> bsLinks;
    final List<PId<D>> bsIds;
    final boolean prototype;
    final boolean nullable;
    final VContext context;
    final Class<S> type;
    final int ici;

    BSNode(int i, Class<S> type, BSContext<D> bc, VContext vc, boolean n, boolean p, List<BSProperty<?, D>> ps) throws BeanException {
        this.bsIds = bc.businessIds(ps);
        this.bsLinks = bc.links();
        this.properties = ps;
        this.prototype = p;
        this.nullable = n;
        this.context = vc;
        this.type = type;
        this.ici = i;
    }

    BSNode(BSNode<S, D> source) throws BeanException {
        this.type = source.type;
        this.bsIds = source.bsIds;
        this.ici = source.ici;
        this.bsLinks = source.bsLinks;
        this.context = source.context;
        this.nullable = source.nullable;
        this.prototype = source.prototype;
        this.properties = this.reverse(source.properties);
    }

    private List<BSProperty<?, D>> reverse(List<BSProperty<?, D>> source) throws BeanException {
        final List<BSProperty<?, D>> ps = new ArrayList<>(source.size());
        var same = true;
        for (final BSProperty<?, D> p : source) {
            final BSProperty<?, D> r = p.reverse();
            ps.add(r);
            if (p != r) {
                same = false;
            }
        }
        return (same) ? source : List.copyOf(ps);
    }

    private BSProperty<?, D> find(final ConstraintViolation<?> violation) {
        final String[] path = violation.getPropertyPath().toString().split("\\.");
        for (final PId<D> id : bsIds) {
            if (id.isVirtual() && id.isYours(path)) {
                return id.property;
            }
        }
        var ps = this.properties;
        for (final String fn : path) {
            var cp = ps.stream().filter(p -> fn.equals(p.getName())).findAny().orElse(null);
            if (cp.getClass() == PFragment.class) {
                //noinspection unchecked
                ps = ((PFragment<?, D>) cp).node.properties;
            } else {
                return cp;
            }
        }
        return null;
    }

    final void validate(Validator validator, S bean, int offset, ComplianceHandler<D> handler) {
        final Set<ConstraintViolation<Object>> violations = validator.validate(bean, context.groups);
        for (final ConstraintViolation<?> cv : violations) {
            final BSProperty<?, D> p = this.find(cv);
            if (p != null) {
                final Integer ro = (p.offset < 0) ? null : p.offset + offset;
                handler.handle(p.dataType, ro, cv, p.error);
            } else {
                handler.handle(null, null, cv, null);
            }
        }
    }

    abstract BSNode<S, D> reverse() throws BeanException;

    @Override
    public String toString() {
        return type.getSimpleName();
    }

    /**
     * Internal Flat Node
     */
    static final class Flat<S extends Segment, D extends ITerm<D>> extends BSNode<S, D> {
        private final String[] cache;
        private final int index;

        Flat(int i, Class<S> t, BSContext<D> bc, VContext vc, Fragment f, List<BSProperty<?, D>> ps) throws BeanException {
            super(i, t, bc, vc, f.nullable(), f.prototype(), ps);
            if (this.prototype) {
                this.index = Pod.min(this);
                this.cache = this.defaultValues(index);
            } else {
                this.index = -1;
                this.cache = EMPTY;
            }
        }

        Flat(Class<S> type, BSContext<D> bc, VContext vc, List<BSProperty<?, D>> ps) throws BeanException {
            super(-1, type, bc, vc, false, false, ps);
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
                for (final BSProperty<?, D> p : properties) {
                    if (p.getClass() == PFragment.class) {
                        //noinspection unchecked
                        final Flat<Segment, D> node = (Flat<Segment, D>) ((PFragment<?, D>) p).node;
                        final String[] values = node.cache;
                        if (values != EMPTY) {
                            System.arraycopy(values, 0, result, node.index, values.length);
                        }
                    } else {
                        result[p.offset] = ((PPosition<?, D>) p).format(null);
                    }
                }
                return prototype(result, min);
            }
            return EMPTY;
        }

        void defaultValues(String[] result, int offset) {
            if (cache != EMPTY) {
                System.arraycopy(cache, 0, result, offset + index, cache.length);
            }
        }

        void format(String[] result, int offset, S bean) {
            if (bean == null) {
                this.defaultValues(result, offset);
            } else {
                for (final BSProperty<?, D> p : properties) {
                    if (p.getClass() == PFragment.class) {
                        //noinspection unchecked
                        final PFragment<Segment, D> fp = (PFragment<Segment, D>) p;
                        ((Flat<Segment, D>) fp.node).format(result, offset, fp.value(bean));
                    } else {
                        //noinspection unchecked
                        final PPosition<Object, D> pp = (PPosition<Object, D>) p;
                        result[offset + p.offset] = pp.format(pp.value(bean));
                    }
                }
            }
        }

        void visit(Consumer<PPosition<?, D>> handler) {
            for (final BSProperty<?, D> p : properties) {
                if (p.getClass() == PFragment.class) {
                    //noinspection unchecked
                    final Flat<Segment, D> node = (Flat<Segment, D>) ((PFragment<?, D>) p).node;
                    node.visit(handler);
                } else {
                    handler.accept((PPosition<?, D>) p);
                }
            }
        }

        void validate(Validator validator, ComplianceHandler<D> handler, S bean, int offset) {
            for (final BSProperty<?, D> p : properties) {
                if (p.getClass() == PFragment.class) {
                    //noinspection unchecked
                    final PFragment<Segment, D> fp = (PFragment<Segment, D>) p;
                    final Segment value = fp.value(bean);
                    if (value != null) {
                        ((Flat<Segment, D>) fp.node).validate(validator, handler, value, offset);
                    }
                }
            }
            if (context.enabled) {
                this.validate(validator, bean, offset, handler);
            }
        }

        @Override
        Bean<S, D, ?> reverse() throws BeanException {
            return new BCR<>(type, properties) {
                @Override
                Bean.CN<S, D> cn(ICreator<S> cs) throws BeanException {
                    return new Bean.CN<>(Flat.this, cs);
                }

                @Override
                Bean.JB<S, D> jb(ICreator<S> cs) throws BeanException {
                    return new Bean.JB<>(Flat.this, cs);
                }

                @Override
                Bean.JR<S, D> jr(ICreator<S> cs) throws BeanException {
                    return new Bean.JR<>(Flat.this, cs);
                }
            }.build();
        }
    }

    /**
     * Internal Mapper Node
     */
    abstract static sealed class Bean<S extends Segment, D extends ITerm<D>, C> extends BSNode<S, D> permits Bean.CN, Bean.CS {
        protected final ICreator<S> creator;
        protected final boolean recordable;
        protected final Object[] prototype;

        Bean(int i, Class<S> t, BSContext<D> bc, VContext vc, Fragment f, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
            super(i, t, bc, vc, f.nullable(), f.prototype(), ps);
            this.recordable = Recordable.class.isAssignableFrom(t);
            this.prototype = cs.getPrototype();
            this.creator = cs;
        }

        Bean(Class<S> type, BSContext<D> bc, VContext vc, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
            super(-1, type, bc, vc, false, false, ps);
            this.recordable = Recordable.class.isAssignableFrom(type);
            this.prototype = cs.getPrototype();
            this.creator = cs;
        }

        private Bean(Flat<S, D> source, ICreator<S> cs) throws BeanException {
            super(source);
            this.creator = cs;
            this.prototype = creator.getPrototype();
            this.recordable = Recordable.class.isAssignableFrom(type);
        }

        protected boolean parse(LinkedList<Segment> stack, Input in, EventHandler<D> handler, C args) {
            var empty = true;
            for (final BSProperty<?, D> p : properties) {
                if (p.getClass() == PFragment.class) {
                    //noinspection unchecked
                    final PFragment<Segment, D> fp = (PFragment<Segment, D>) p;
                    if (fp.node.nullable && in.no(fp.offset)) {
                        continue;
                    }
                    final Segment value = ((Bean<?, D, C>) fp.node).parse(stack, in, handler);
                    if (value != null) {
                        this.set(args, fp, value);
                        if (fp.node.context.enabled(in.validator)) {
                            fp.node.validate(in.validator, value, in.offset, handler);
                        }
                        empty = false;
                    }
                } else {
                    //noinspection unchecked
                    final PPosition<Object, D> pp = (PPosition<Object, D>) p;
                    final Object value = pp.parse(in.at(p.offset), in.offset, handler);
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

        @Override
        protected final Flat<S, D> reverse() throws BeanException {
            return new Flat<>(this);
        }

        protected final S parse(Input in, EventHandler<D> handler) {
            return this.parse(new LinkedList<>(), in, handler);
        }

        protected abstract S parse(LinkedList<Segment> stack, Input in, EventHandler<D> handler);

        protected abstract <V> void set(C bean, BSProperty<V, D> property, V value);

        /**
         * Internal Mapper Node for java-beans without constructor setters.
         */
        static final class CN<S extends Segment, D extends ITerm<D>> extends Bean<S, D, S> {
            CN(int i, Class<S> t, BSContext<D> bc, VContext vc, Fragment f, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
                super(i, t, bc, vc, f, ps, cs);
            }

            CN(Class<S> type, BSContext<D> bc, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
                super(type, bc, bc.context(), ps, cs);
            }

            CN(Flat<S, D> source, ICreator<S> cs) throws BeanException {
                super(source, cs);
            }

            private S bean(LinkedList<Segment> stack) {
                if (ici != -1) {
                    final Object[] args = new Object[prototype.length];
                    System.arraycopy(prototype, 0, args, 0, prototype.length);
                    args[0] = stack.get(ici);
                    return creator.newInstance(args);
                }
                return creator.newInstance(prototype);
            }

            @Override
            protected S parse(LinkedList<Segment> stack, Input in, EventHandler<D> handler) {
                final S bean = this.bean(stack);
                stack.addLast(bean);
                final boolean empty = this.parse(stack, in, handler, bean);
                stack.removeLast();
                return empty ? null : bean;
            }

            @Override
            protected <V> void set(S bean, BSProperty<V, D> property, V value) {
                property.value(bean, value);
            }
        }

        /**
         * Internal Mapper Node for java-beans within constructor setters.
         */
        abstract static sealed class CS<S extends Segment, D extends ITerm<D>> extends Bean<S, D, Object[]> permits JR, JB {
            private final Map<BSProperty<?, D>, IParameter> indexes;

            CS(int i, Class<S> type, BSContext<D> bc, VContext vc, Fragment f, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
                super(i, type, bc, vc, f, ps, cs);
                this.indexes = this.indexes();
            }

            CS(Class<S> type, BSContext<D> bc, VContext vc, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
                super(type, bc, vc, ps, cs);
                this.indexes = this.indexes();
            }

            CS(Flat<S, D> source, ICreator<S> cs) throws BeanException {
                super(source, cs);
                this.indexes = this.indexes();
            }

            abstract Map<BSProperty<?, D>, IParameter> indexes() throws BeanException;

            @Override
            protected final S parse(LinkedList<Segment> stack, Input in, EventHandler<D> handler) {
                final Object[] args = new Object[prototype.length];
                System.arraycopy(prototype, 0, args, 0, prototype.length);
                if (this.ici != -1) {
                    args[0] = stack.get(ici);
                }
                stack.addLast(null);
                final boolean empty = this.parse(stack, in, handler, args);
                stack.removeLast();
                return empty ? null : creator.newInstance(args);
            }

            @Override
            protected final <V> void set(Object[] args, BSProperty<V, D> property, V value) {
                final IParameter parameter = indexes.get(property);
                args[parameter.index] = parameter.wrap(value);
            }
        }

        /**
         * Internal {@link CS} for java-records
         */
        static final class JR<S extends Segment, D extends ITerm<D>> extends CS<S, D> {

            JR(Class<S> type, BSContext<D> bc, VContext vc, Fragment f, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
                super(-1, type, bc, vc, f, ps, cs);
            }

            JR(Class<S> type, BSContext<D> bc, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
                super(type, bc, bc.context(), ps, cs);
            }

            private JR(Flat<S, D> source, ICreator<S> cs) throws BeanException {
                super(source, cs);
            }

            @Override
            protected Map<BSProperty<?, D>, IParameter> indexes() {
                final Map<BSProperty<?, D>, IParameter> indexes = new HashMap<>(this.properties.size());
                final Parameter[] parameters = this.creator.getParameters();
                final Iterator<BSProperty<?, D>> it = this.properties.iterator();
                for (var i = 0; it.hasNext() && i < parameters.length; ) {
                    final BSProperty<?, D> p = it.next();
                    var pm = parameters[i];
                    while (!pm.getName().equals(p.getName()) && ++i < parameters.length) {
                        pm = parameters[i];
                    }
                    indexes.put(p, IParameter.of(pm.getType(), i));
                }
                return Map.copyOf(indexes);
            }
        }

        /**
         * Internal {@link CS} for immutable java-beans
         */
        static final class JB<S extends Segment, D extends ITerm<D>> extends CS<S, D> {

            JB(int i, Class<S> type, BSContext<D> bc, VContext vc, Fragment f, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
                super(i, type, bc, vc, f, ps, cs);
            }

            JB(Class<S> type, BSContext<D> bc, List<BSProperty<?, D>> ps, ICreator<S> cs) throws BeanException {
                super(type, bc, bc.context(), ps, cs);
            }

            private JB(Flat<S, D> source, ICreator<S> cs) throws BeanException {
                super(source, cs);
            }

            private IParameter index(Parameter[] ps, BSProperty<?, D> p) throws BeanException {
                for (var i = 0; i < ps.length; i++) {
                    final Parameter pm = ps[i];
                    if (pm.getName().equals(p.getName())) {
                        return p.toParameter(this.type, pm.getParameterizedType(), i);
                    }
                }
                throw new BeanException(p.getSource(), "must be in constructor arguments");
            }

            @Override
            protected Map<BSProperty<?, D>, IParameter> indexes() throws BeanException {
                final Map<BSProperty<?, D>, IParameter> indexes = new HashMap<>(this.properties.size());
                final Parameter[] parameters = this.creator.getParameters();
                for (BSProperty<?, D> p : this.properties) {
                    indexes.put(p, index(parameters, p));
                }
                return Map.copyOf(indexes);
            }
        }
    }
}
