package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSNode.Bean;
import io.github.up2jakarta.csv.core.BSNode.Flat;
import io.github.up2jakarta.csv.core.BSOperator.Factory;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.data.DataResolver;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.StringAdapter;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.core.BSBuilder.*;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.lov.core.Defaults.wrap;
import static io.github.up2jakarta.lov.core.Overrides.*;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;

/**
 * Internal business context.
 */
final class BSContext<D extends DataType<D>> {

    private final Map<TypeResolver<?, ?>, Class<?>> cache = new LinkedHashMap<>();
    private final Map<Path, PositionOverride> positions = new LinkedHashMap<>();
    private final Map<Path, FragmentOverride> fragments = new LinkedHashMap<>();
    private final Map<Path, ValidOverride> validations = new LinkedHashMap<>();
    private final List<Class<? extends Segment>> stack = new LinkedList<>();
    private final List<TypeExtension<?, Annotation>> extensions;
    private final LinkedList<Field> path = new LinkedList<>();
    private final Class<? extends Segment> type;
    private final Optional<AccessType> access;
    private final DataResolver<D> resolver;
    private final Factory<?> factory;
    private final Listener checker;
    private final VContext context;
    private final Type[] arguments;
    private final Mode mode;
    private final int offset;

    BSContext(Factory<?> f, Class<? extends Segment> t, Mode m, DataResolver<D> d) throws BeanException {
        this.context = (f.validator != null) ? VContext.from(t) : VContext.DISABLED;
        add(PositionOverride.class, t, this.positions::put, PositionOverride::path);
        add(FragmentOverride.class, t, this.fragments::put, FragmentOverride::path);
        add(ValidOverride.class, t, this.validations::put, ValidOverride::path);
        this.access = getAccessType(Optional.empty(), t);
        this.checker = Listener.of(t, f.context, m);
        this.extensions = extension(t, f.context);
        this.arguments = NO_TYPES;
        this.resolver = d;
        this.factory = f;
        this.offset = 0;
        this.mode = m;
        this.type = t;
    }

    private BSContext(BSContext<D> origin, int offset, Class<? extends Segment> type, Type... arguments) throws BeanException {
        add(PositionOverride.class, type, this.positions::put, PositionOverride::path);
        add(FragmentOverride.class, type, this.fragments::put, FragmentOverride::path);
        add(ValidOverride.class, type, this.validations::put, ValidOverride::path);
        this.access = getAccessType(origin.access, type);
        origin.path.forEach(this.path::addLast);
        origin.stack.forEach(this.stack::addLast);
        this.extensions = origin.extensions;
        this.resolver = origin.resolver;
        this.context = origin.context;
        this.checker = origin.checker;
        this.factory = origin.factory;
        this.arguments = arguments;
        this.type = origin.type;
        this.mode = origin.mode;
        this.offset = offset;
    }

    private static List<TypeExtension<?, Annotation>> extension(Class<? extends Segment> st, BeanContext bc) throws BeanException {
        final Extension[] extensions = getAnnotationsByType(Extension.class, st).toArray(Extension[]::new);
        final List<TypeExtension<?, Annotation>> result = new LinkedList<>();
        for (final Extension extension : extensions) {
            result.add(getBean(bc, extension.value(), extension.name()));
        }
        return Collections.unmodifiableList(result);
    }

    private static Up2Converter converter(Field field, Position config) {
        if (config.converter().value() != StringAdapter.class) {
            return config.converter();
        }
        return field.getAnnotation(Up2Converter.class);
    }

    private static BeanException translate(Class<?> type, Field field) {
        final Class<?> wrapper = wrap(type);
        final String msg = "should be annotated with @";
        if (Boolean.class.equals(wrapper)) {
            return new BeanException(field, msg + Up2Boolean.class.getSimpleName());
        }
        if (CodeList.class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2CodeList.class.getSimpleName());
        }
        if (Temporal.class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2Temporal.class.getSimpleName());
        }
        if (TemporalAmount.class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2TemporalAmount.class.getSimpleName());
        }
        if (Number.class.isAssignableFrom(wrapper)) {
            if (List.of(BigDecimal.class, Double.class, Float.class).contains(wrapper)) {
                return new BeanException(field, msg + Up2Decimal.class.getSimpleName());
            }
            return new BeanException(field, msg + Up2Number.class.getSimpleName());
        }
        if (byte[].class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2Base64.class.getSimpleName());
        }
        final String cn = Up2Converter.class.getSimpleName();
        return new BeanException(field, "must be annotated with @" + cn + " or one of those shortcuts");
    }

    @SuppressWarnings("unchecked")
    static <S extends Segment, D extends DataType<D>> Constructor<S> from(Class<S> t, List<BSProperty<?, ?, D>> ps) throws BeanException {
        final long fc = ps.stream().filter(BSProperty::isFinal).count();
        if (fc == 0) {
            return null;
        }
        if (fc == ps.size()) {
            final Constructor<S>[] dcs = (Constructor<S>[]) t.getDeclaredConstructors();
            final List<Constructor<S>> cs = stream(dcs).filter(c -> c.isAnnotationPresent(Creator.class)).toList();
            if (cs.size() == 1) {
                return setAccessible(cs.getFirst());
            }
            if (t.isRecord()) {
                return null;
            }
            if (dcs.length == 1) {
                return setAccessible(dcs[0]);
            }
            throw new BeanException(t, "one and only one constructor must be annotated with @Creator");
        }
        throw new BeanException(t, "mix final and writable properties is not allowed");
    }

    private <V> BSAccessor<V> accessor(Class<V> type, Field field) throws BeanException {
        final Class<? extends Segment> container = segmentType(stack);
        final AccessType access = getAccessType(field, this.access).orElse(AccessType.FIELD);
        return mode.of(access, container, field, type);
    }

    private D dataType(Field field) throws BeanException {
        if (resolver != null) {
            final List<Class<? extends Segment>> cs = cleanStack(stack);
            final D value = resolver.get(cs, path.toArray(Field[]::new), field).orElse(null);
            if (value != null) {
                resolver.check(value);
            }
            return value;
        }
        return null;
    }

    private void checkType(Field field, Class<?> type, Annotation config, TypeResolver<?, ?> tr) throws BeanException {
        final Class<?> support = cache.computeIfAbsent(tr,
                r -> getTypeArgument(r.getClass(), TypeResolver.class, 0, void.class)
        );
        if (!support.isAssignableFrom(type)) {
            throw new BeanException(field, "must not be annotated with @" + getTypeName(config.annotationType()));
        }
    }

    private <T> Class<?> resolve(Field fp, Class<T> type, Consumer<TypeAdapter<? extends T>> ta) throws BeanException {
        final Class<?> wrappedType = wrap(type);
        final List<Annotation> founds = new LinkedList<>();
        for (final Annotation config : fp.getAnnotations()) {
            final Resolver resolver = config.annotationType().getAnnotation(Resolver.class);
            if (resolver != null) {
                final TypeResolver<T, Annotation> cr = getBean(factory.context, resolver.value(), resolver.name());
                this.checkType(fp, wrappedType, config, cr);
                founds.add(config);
                ta.accept(cr.resolve(fp, type, config));
            }
        }
        if (founds.size() > 1) {
            final String cns = founds.stream().map(a -> getTypeName(a.annotationType())).collect(joining(", "));
            throw new BeanException(fp, "must be annotated with one and only one of shortcuts: " + cns);
        }
        return wrappedType;
    }

    @SuppressWarnings("unchecked")
    private <T> TypeAdapter<T> resolve(Field field, Class<T> type, Position position) throws BeanException {
        if (CharSequence.class == type || type == String.class) {
            return (TypeAdapter<T>) StringAdapter.INSTANCE;
        }
        final Up2Converter pc = converter(field, position);
        if (pc != null) {
            final TypeAdapter<T> adapter = getBean(factory.context, pc.value(), pc.name());
            if (!adapter.getType().isAssignableFrom(type)) {
                throw new BeanException(field, "@Position[converter] does not support " + type);
            }
            return adapter;
        }
        final Wrapper<TypeAdapter<? extends T>> result = new Wrapper<>();
        final Class<?> wrappedType = this.resolve(field, type, result);
        final Field[] path = this.path.toArray(Field[]::new);
        for (final TypeExtension<?, Annotation> extension : extensions) {
            final Class<? extends Segment> segmentType = segmentType(stack);
            final Optional<Annotation> config = extension.resolve(segmentType, field, type, path);
            if (config.isPresent()) {
                this.checkType(field, wrappedType, config.get(), extension);
                result.accept(() -> ((TypeExtension<T, Annotation>) extension).resolve(field, type, config.get()));
            }
        }
        return (TypeAdapter<T>) result.orThrow(() -> translate(type, field));
    }

    private <S extends Segment> BSNode<S, D> node(Field fp, Class<S> ft, VContext vc, Fragment fr, List<BSProperty<?, ?, D>> ps) throws BeanException {
        final int index;
        if (isInnerType(ft)) {
            final Class<?> ec = ft.getEnclosingClass();
            if (ec.isRecord()) {
                throw new BeanException(fp, "inner class is not allowed inside enclosing record: " + getTypeName(ec));
            }
            index = stack.lastIndexOf(ec);
        } else {
            index = -1;
        }
        if (mode == Mode.RO) {
            return new Flat<>(index, ft, factory.validator, vc, fr, ps);
        }
        final Constructor<S> cs = from(ft, ps);
        if (cs == null) {
            if (ft.isRecord()) {
                return new Bean.JR<>(ft, factory.validator, vc, fr, ps);
            }
            return new Bean.BM<>(index, ft, factory.validator, vc, fr, ps);
        }
        return new Bean.JB<>(index, ft, factory.validator, vc, fr, ps, cs);
    }

    List<BSProperty<?, ?, D>> build() throws BeanException {
        this.checker.beforeSegment(type);
        final List<BSProperty<?, ?, D>> ps = BSBuilder.build(type, this);
        this.checker.afterSegment(checker);
        return unmodifiableList(ps);
    }

    VContext context() {
        return context;
    }

    boolean push(Class<? extends Segment> beanType) {
        if (stack.contains(beanType)) {
            return true;
        }
        this.stack.addLast(beanType);
        return false;
    }

    void end() throws BeanException {
        checker.afterSuperSegment(stack.getLast());
    }

    BSContext<D> with(Field field, Fragment fragment, Class<? extends Segment> segmentType) throws BeanException {
        final int offset = this.offset + fragment.value();
        checker.beforeFragmentProperty(field, segmentType, offset);
        final Type[] arguments = getPropertyArguments(field, this.arguments);
        final BSContext<D> result = new BSContext<>(this, offset, segmentType, arguments);
        result.path.addLast(field);
        final String name = field.getName();
        this.positions.forEach((o, p) -> add(o, p, name, result.positions::put));
        this.fragments.forEach((o, p) -> add(o, p, name, result.fragments::put));
        this.validations.forEach((o, p) -> add(o, p, name, result.validations::put));
        add(PositionOverride.class, field, result.positions::putIfAbsent, PositionOverride::path);
        add(FragmentOverride.class, field, result.fragments::putIfAbsent, FragmentOverride::path);
        add(ValidOverride.class, field, result.validations::putIfAbsent, ValidOverride::path);
        return result;
    }

    BSContext<D> with(Class<? extends Segment> superType, Type genericType) throws BeanException {
        checker.beforeSuperSegment(superType);
        final Type[] arguments;
        if (genericType instanceof ParameterizedType pt) {
            arguments = resolveArguments(stack.getLast(), pt, this.arguments);
        } else {
            arguments = NO_TYPES;
        }
        final BSContext<D> result = new BSContext<>(this, this.offset, superType, arguments);
        result.positions.putAll(positions);
        result.fragments.putAll(fragments);
        result.validations.putAll(validations);
        return result;
    }

    void unknown(Field fp, Class<?> type) throws BeanException {
        checker.unknownProperty(fp, type);
    }

    Position position(Field field) {
        return get(Position.class, positions, field, PositionOverride::value, p -> p.value() >= 0);
    }

    Fragment fragment(Field field) {
        return get(Fragment.class, fragments, field, FragmentOverride::value, p -> p.value() >= 0);
    }

    <S extends Segment> PFragment<S, ?, D> node(Class<S> ft, Field ff, Fragment fr, List<BSProperty<?, ?, D>> ps) throws BeanException {
        checker.afterFragmentProperty(ff, ft);
        final ValidOverride override = get(ValidOverride.class, validations, ff, ValidOverride::path);
        final VContext vc = context.build(ff, ft, override);
        if (Optional.class.isAssignableFrom(ff.getType())) {
            final BSAccessor<Optional<S>> va = this.accessor(cast(ft), ff);
            return new PFragment.FO<>(this.node(ff, ft, vc, fr, ps), va, this.dataType(ff), offset, fr);
        }
        if (Wrapper.class.isAssignableFrom(ff.getType())) {
            final BSAccessor<Wrapper<S>> va = this.accessor(cast(ft), ff);
            return new PFragment.FW<>(this.node(ff, ft, vc, fr, ps), va, this.dataType(ff), offset, fr);
        }
        final BSNode<S, D> node = this.node(ff, ft, vc, fr, ps);
        return new PFragment.FS<>(node, this.accessor(ft, ff), this.dataType(ff), offset, fr);
    }

    <T> PPosition<T, ?, D> property(Class<T> pt, Field pf, Position pc) throws BeanException {
        checker.positionProperty(pf, pt, offset + pc.value());
        final Input<D> ps = BSBuilder.build(factory.context, pf, pc);
        final D dataType = this.dataType(pf);
        final TypeAdapter<T> adapter = this.resolve(pf, pt, pc);
        if (Optional.class.isAssignableFrom(pf.getType())) {
            final BSAccessor<Optional<T>> va = this.accessor(cast(pt), pf);
            return new PPosition.PO<>(va, dataType, offset, pc, ps, adapter);
        }
        if (Wrapper.class.isAssignableFrom(pf.getType())) {
            final BSAccessor<Wrapper<T>> va = this.accessor(cast(pt), pf);
            return new PPosition.PW<>(va, dataType, offset, pc, ps, adapter);
        }
        return new PPosition.PS<>(this.accessor(pt, pf), dataType, offset, pc, ps, adapter);
    }

    Type fieldType(Field field) {
        final Class<?> type = field.getType();
        if (Optional.class.isAssignableFrom(type) || Wrapper.class.isAssignableFrom(type)) {
            return getPropertyArguments(field, this.arguments)[0];
        }
        return getPropertyType(field, arguments);
    }

    /**
     * Internal Context for JSR-303 validation.
     */
    static class VContext {
        private static final VContext DISABLED = new VContext(false);
        private static final VContext DEFAULT = new VContext(true);

        final boolean enabled;
        final Class<?>[] groups;

        private VContext(boolean enabled, Class<?>... groups) {
            this.enabled = enabled;
            this.groups = groups;
        }

        private static Class<?>[] checkGroups(Class<?>[] groups, AnnotatedElement source) throws BeanException {
            if (source.isAnnotationPresent(Valid.class)) {
                throw BeanException.of(source, "must not be annotated with @Valid");
            }
            final Set<Class<?>> result = new HashSet<>(groups.length);
            for (final Class<?> group : groups) {
                if (!group.isInterface()) {
                    final String cn = group.getName();
                    throw BeanException.of(source, "@ValidOverride[value = " + cn + ".class must be an interface]");
                }
                result.add(group);
            }
            return result.toArray(Class<?>[]::new);
        }

        @SuppressWarnings("unchecked")
        private static VContext from(Class<? extends Segment> type) throws BeanException {
            while (type != Segment.class && Segment.class.isAssignableFrom(type)) {
                final ValidOverride override = get(ValidOverride.class, type, ValidOverride::path);
                if (override != null) {
                    if (override.disable()) {
                        return DISABLED;
                    } else if (override.groups().length == 0) {
                        return DEFAULT;
                    }
                    return new VContext(true, checkGroups(override.groups(), type));
                }
                if (type.isAnnotationPresent(Valid.class)) {
                    return DEFAULT;
                }
                type = (Class<? extends Segment>) type.getSuperclass();
            }
            return DISABLED;
        }

        private static AnnotatedType type(Field field) {
            if (field.getAnnotatedType() instanceof AnnotatedParameterizedType apt) {
                return apt.getAnnotatedActualTypeArguments()[0];
            }
            return field.getAnnotatedType();
        }

        private VContext build(Field f, Class<? extends Segment> ft, ValidOverride vo) throws BeanException {
            final AnnotatedType at = type(f);
            if (enabled) {
                final boolean hasValid = f.isAnnotationPresent(Valid.class) || at.isAnnotationPresent(Valid.class);
                final Set<Class<?>> groups = new HashSet<>(asList(this.groups));
                if (vo == null) {
                    final VContext ctx = from(ft);
                    if (hasValid && ctx.enabled && groups.containsAll(asList(ctx.groups))) {
                        return DISABLED;
                    }
                    return ctx;
                } else if (!vo.disable()) {
                    if (hasValid && groups.containsAll(asList(vo.groups()))) {
                        return DISABLED;
                    }
                    return new VContext(true, checkGroups(vo.groups(), f));
                }
            }
            return DISABLED;
        }
    }

}
