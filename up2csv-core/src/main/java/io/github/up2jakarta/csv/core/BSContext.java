package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.BSNode.BFNode;
import io.github.up2jakarta.csv.core.BSNode.BPNode;
import io.github.up2jakarta.csv.core.BSNode.BPNode.BMNode;
import io.github.up2jakarta.csv.core.BSNode.BPNode.BRNode;
import io.github.up2jakarta.csv.core.BSOperator.Factory;
import io.github.up2jakarta.csv.core.BSProperty.FProperty;
import io.github.up2jakarta.csv.core.BSProperty.FProperty.FOProperty;
import io.github.up2jakarta.csv.core.BSProperty.FProperty.FWProperty;
import io.github.up2jakarta.csv.core.BSProperty.PAccessor;
import io.github.up2jakarta.csv.core.BSProperty.PProcessor;
import io.github.up2jakarta.csv.core.BSProperty.PProperty;
import io.github.up2jakarta.csv.core.BSProperty.PProperty.POProperty;
import io.github.up2jakarta.csv.core.BSProperty.PProperty.PWProperty;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.core.ext.PPath;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.TypeConverter;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;

import static io.github.up2jakarta.csv.core.AccessMode.RO;
import static io.github.up2jakarta.csv.core.AccessMode.WO;
import static io.github.up2jakarta.csv.core.BeanException.of;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.csv.core.ext.PPath.addOverride;
import static io.github.up2jakarta.csv.core.ext.PPath.getOverride;
import static jakarta.persistence.AccessType.PROPERTY;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * Internal business context.
 */
final class BSContext<D extends DataType<D>> {

    private final Map<PPath, PositionOverride> positions = new LinkedHashMap<>();
    private final Map<PPath, FragmentOverride> fragments = new LinkedHashMap<>();
    private final Map<PPath, ValidOverride> validations = new LinkedHashMap<>();
    private final Stack<Class<? extends Segment>> stack = new Stack<>();
    private final ConversionExtension<?, Annotation>[] extensions;
    private final LinkedList<Field> path = new LinkedList<>();
    private final Class<? extends Segment> type;
    private final DataTypeResolver<D> resolver;
    private final Optional<AccessType> access;
    private final BSBuilder.WChecker checker;
    private final Factory factory;
    private final BVContext context;
    private final Type[] arguments;
    private final AccessMode mode;
    private final int offset;

    private BSContext(Factory f, Class<? extends Segment> t, AccessMode m, DataTypeResolver<D> d) throws BeanException {
        addOverride(PositionOverride.class, t, this.positions::put, PositionOverride::path);
        addOverride(FragmentOverride.class, t, this.fragments::put, FragmentOverride::path);
        addOverride(ValidOverride.class, t, this.validations::put, ValidOverride::path);
        this.access = BSBuilder.getAccessType(Optional.empty(), t);
        this.checker = BSBuilder.WChecker.of(t, f.context);
        this.extensions = ext(t, f.context);
        this.context = BVContext.from(t);
        this.arguments = NO_TYPES;
        this.resolver = d;
        this.factory = f;
        this.offset = 0;
        this.mode = m;
        this.type = t;
    }

    private BSContext(BSContext<D> origin, int offset, Class<? extends Segment> type, Type... arguments) throws BeanException {
        addOverride(PositionOverride.class, type, this.positions::put, PositionOverride::path);
        addOverride(FragmentOverride.class, type, this.fragments::put, FragmentOverride::path);
        addOverride(ValidOverride.class, type, this.validations::put, ValidOverride::path);
        this.access = BSBuilder.getAccessType(origin.access, type);
        origin.path.forEach(this.path::addLast);
        origin.stack.forEach(this.stack::push);
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

    @SuppressWarnings("unchecked")
    private static ConversionExtension<?, Annotation>[] ext(Class<? extends Segment> st, BeanContext bc) throws BeanException {
        final Extension[] extensions = BSBuilder.getAnnotationsByType(Extension.class, st).toArray(Extension[]::new);
        final List<ConversionExtension<?, ? extends Annotation>> result = new LinkedList<>();
        for (final Extension extension : extensions) {
            final ConversionExtension<?, ?> bean = getBean(bc, extension.value());
            if (bean.isActivated(st)) {
                result.add(bean);
            }
        }
        return (ConversionExtension<?, Annotation>[]) result.toArray(ConversionExtension<?, ?>[]::new);
    }

    static <D extends DataType<D>, S extends Segment> BPNode<S, D, ?> build(Class<S> t, Factory f, DataTypeResolver<D> r) throws BeanException {
        final BSContext<D> mc = new BSContext<>(f, t, WO, r);
        if (t.isRecord()) {
            return new BRNode<>(t, f.validator, mc.context, mc.build());
        }
        return new BMNode<>(t, f.validator, mc.context, mc.build());
    }

    static <D extends DataType<D>, S extends Segment> BFNode<S, D> format(Class<S> t, Factory f, DataTypeResolver<D> r) throws BeanException {
        final BSContext<D> mc = new BSContext<>(f, t, RO, r);
        return new BFNode<>(t, f.validator, mc.context, mc.build());
    }

    private <V> PAccessor<Field, V> accessor(Class<V> type, Field field) throws BeanException {
        final Class<? extends Segment> container = getSegmentType(stack);
        final AccessType access = BSBuilder.getAccessType(field, this.access).orElse(PROPERTY);
        return mode.of(access, container, field, type);
    }

    private List<BSProperty<?, ?, D>> build() throws BeanException {
        this.checker.beforeSegment(mode, type);
        final List<BSProperty<?, ?, D>> ps = BSBuilder.build(type, this);
        this.checker.afterSegment(checker);
        return unmodifiableList(ps);
    }

    private D dataType(Field field) throws BeanException {
        if (resolver != null) {
            final Stack<Class<? extends Segment>> cs = cleanStack(stack);
            final D value = resolver.get(cs, path.toArray(Field[]::new), field).orElse(null);
            if (value != null) {
                resolver.check(value);
            }
            return value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> Conversion<T> conversion(Field field, Class<T> type, Position position) throws BeanException {
        if (CharSequence.class == type || type == String.class) {
            return (Conversion<T>) Conversion.NAN;
        }
        final Error error = field.getAnnotation(Error.class);
        if (position.converter() != Position.NaN.class) {
            final TypeConverter<T> tConverter = getBean(factory.context, position.converter());
            if (!tConverter.getSupportedType().isAssignableFrom(type)) {
                throw new BeanException(field, "@Position[converter] does not support " + type);
            }
            return Conversion.of(tConverter, error);
        }
        for (final Annotation config : field.getAnnotations()) {
            final Resolver resolver = config.annotationType().getAnnotation(Resolver.class);
            if (resolver != null) {
                final ConversionResolver<Annotation> cr = getBean(factory.context, resolver.value());
                final PropertyFormatter<T> f = (PropertyFormatter<T>) cr.forFormatting(config, field, type);
                final PropertyConverter<T> p = (PropertyConverter<T>) cr.forParsing(config, field, type);
                return new Conversion<>(type, p, f, error);
            }
        }
        final Field[] fieldPath = path.toArray(Field[]::new);
        for (final ConversionExtension<?, Annotation> extension : extensions) {
            final Class<? extends Segment> segmentType = getSegmentType(stack);
            final Optional<Annotation> config = extension.get(segmentType, field, type, fieldPath);
            if (config.isPresent()) {
                return extension.resolve(field, type, config.get());
            }
        }
        throw new BeanException(field, "@Position[converter] must not be undefined");
    }

    private <S extends Segment> BSNode<S, D> node(Field fp, Class<S> ft, BVContext vc, Fragment fr, List<BSProperty<?, ?, D>> ps) throws BeanException {
        final int index;
        if (isInnerType(ft)) {
            index = stack.lastIndexOf(ft.getEnclosingClass());
            if (index == -1) {
                var cn = stack.stream().filter(Predicate.not(Class::isRecord)).map(Beans::getTypeName).toList();
                throw new BeanException(fp, "inner class is not allowed outside enclosing segments: " + join(", ", cn));
            }
            if (stack.get(index).isRecord()) {
                throw new BeanException(fp, "inner class is not allowed inside enclosing records");
            }
        } else {
            index = -1;
        }
        if (mode == RO) {
            return new BFNode<>(index, ft, factory.validator, vc, fr, ps);
        } else if (ft.isRecord()) {
            return new BRNode<>(ft, factory.validator, vc, fr, ps);
        }
        return new BMNode<>(index, ft, factory.validator, vc, fr, ps);
    }

    boolean push(Class<? extends Segment> beanType) {
        if (stack.contains(beanType)) {
            return true;
        }
        this.stack.push(beanType);
        return false;
    }

    void end() throws BeanException {
        checker.afterSuperSegment(stack.peek());
    }

    BSContext<D> with(Field field, Fragment fragment, Class<? extends Segment> segmentType) throws BeanException {
        checker.beforeFragmentProperty(mode, field, segmentType);
        final int offset = this.offset + fragment.value();
        final Type[] arguments = getPropertyArguments(field, this.arguments);
        final BSContext<D> result = new BSContext<>(this, offset, segmentType, arguments);
        result.path.addLast(field);
        final String name = field.getName();
        this.positions.forEach((o, p) -> addOverride(o, p, name, result.positions::put));
        this.fragments.forEach((o, p) -> addOverride(o, p, name, result.fragments::put));
        this.validations.forEach((o, p) -> addOverride(o, p, name, result.validations::put));
        addOverride(PositionOverride.class, field, result.positions::putIfAbsent, PositionOverride::path);
        addOverride(FragmentOverride.class, field, result.fragments::putIfAbsent, FragmentOverride::path);
        addOverride(ValidOverride.class, field, result.validations::putIfAbsent, ValidOverride::path);
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
        return getOverride(Position.class, positions, field, PositionOverride::value, p -> p.value() >= 0);
    }

    Fragment fragment(Field field) {
        return getOverride(Fragment.class, fragments, field, FragmentOverride::value, p -> p.value() >= 0);
    }

    <S extends Segment> FProperty<S, ?, D> node(Class<S> ft, Field ff, Fragment fr, List<BSProperty<?, ?, D>> ps) throws BeanException {
        checker.afterFragmentProperty(ff, ft);
        final ValidOverride override = getOverride(ValidOverride.class, validations, ff, ValidOverride::path);
        if (Optional.class.isAssignableFrom(ff.getType())) {
            final AnnotatedType at = ((AnnotatedParameterizedType) ff.getAnnotatedType()).getAnnotatedActualTypeArguments()[0];
            final BSNode<S, D> node = this.node(ff, ft, context.build(ff, ft, at, override), fr, ps);
            final PAccessor<?, Optional<S>> va = this.accessor(cast(ft), ff);
            return new FWProperty<>(node, va, this.dataType(ff), offset, fr);
        }
        final BSNode<S, D> node = this.node(ff, ft, context.build(ff, ft, ff.getAnnotatedType(), override), fr, ps);
        return new FOProperty<>(node, this.accessor(ft, ff), this.dataType(ff), offset, fr);
    }

    <T> PProperty<T, ?, D> property(Class<T> pt, Field pf, Position pc) throws BeanException {
        checker.positionProperty(pf, pt, offset + pc.value());
        final PProcessor<D> ps = BSBuilder.build(factory.context, pf, pc);
        final D dataType = this.dataType(pf);
        final Conversion<T> cv = this.conversion(pf, pt, pc);
        if (Optional.class.isAssignableFrom(pf.getType())) {
            final PAccessor<?, Optional<T>> va = this.accessor(cast(pt), pf);
            return new PWProperty<>(va, dataType, offset, pc, ps, cv);
        }
        return new POProperty<>(this.accessor(pt, pf), dataType, offset, pc, ps, cv);
    }

    Type fieldType(Field field) {
        if (Optional.class.isAssignableFrom(field.getType())) {
            return getPropertyArguments(field, this.arguments)[0];
        }
        return getPropertyType(field, arguments);
    }

    /**
     * Internal Context for JSR-303 validation.
     */
    static class BVContext {
        private static final BVContext DISABLED = new BVContext(false);
        private static final BVContext DEFAULT = new BVContext(true);

        final boolean enabled;
        final Class<?>[] groups;

        private BVContext(boolean enabled, Class<?>... groups) {
            this.enabled = enabled;
            this.groups = groups;
        }

        private static Class<?>[] checkGroups(Class<?>[] groups, AnnotatedElement source) throws BeanException {
            if (source.isAnnotationPresent(Valid.class)) {
                throw of(source, "must not be annotated by @Valid");
            }
            final Set<Class<?>> result = new HashSet<>(groups.length);
            for (final Class<?> group : groups) {
                if (!group.isInterface()) {
                    throw of(source, "@ValidOverride[value = " + group.getName() + ".class must be an interface]");
                }
                result.add(group);
            }
            return result.toArray(Class<?>[]::new);
        }

        @SuppressWarnings("unchecked")
        private static BVContext from(Class<? extends Segment> type) throws BeanException {
            while (type != Segment.class && Segment.class.isAssignableFrom(type)) {
                final ValidOverride override = getOverride(ValidOverride.class, type, ValidOverride::path);
                if (override != null) {
                    if (override.disable()) {
                        return DISABLED;
                    } else if (override.groups().length == 0) {
                        return DEFAULT;
                    }
                    return new BVContext(true, checkGroups(override.groups(), type));
                }
                if (type.isAnnotationPresent(Valid.class)) {
                    return DEFAULT;
                }
                type = (Class<? extends Segment>) type.getSuperclass();
            }
            return DISABLED;
        }

        private BVContext build(Field f, Class<? extends Segment> ft, AnnotatedType at, ValidOverride vo) throws BeanException {
            if (enabled) {
                final boolean hasValid = f.isAnnotationPresent(Valid.class) || at.isAnnotationPresent(Valid.class);
                final Set<Class<?>> groups = new HashSet<>(asList(this.groups));
                if (vo == null) {
                    final BVContext ctx = from(ft);
                    if (hasValid && ctx.enabled && groups.containsAll(asList(ctx.groups))) {
                        return DISABLED;
                    }
                    return ctx;
                } else if (!vo.disable()) {
                    if (hasValid && groups.containsAll(asList(vo.groups()))) {
                        return DISABLED;
                    }
                    return new BVContext(true, checkGroups(vo.groups(), f));
                }
            }
            return DISABLED;
        }
    }

}
