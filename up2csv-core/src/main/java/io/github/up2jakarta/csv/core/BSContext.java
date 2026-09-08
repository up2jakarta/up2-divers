package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.TermResolver;
import io.github.up2jakarta.csv.api.ext.TypeExtension;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.api.ext.TypeResolver;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSManager.Factory;
import io.github.up2jakarta.csv.core.BSOperator.PId;
import io.github.up2jakarta.csv.core.BSOperator.SId;
import io.github.up2jakarta.csv.core.BSProperty.IProcessor;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.BeanAccessor.ICreator;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.StringAdapter;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.api.Container.ENABLE_CHECK;
import static io.github.up2jakarta.csv.api.Container.from;
import static io.github.up2jakarta.csv.core.BSBuilder.*;
import static io.github.up2jakarta.csv.core.BSNode.Bean;
import static io.github.up2jakarta.csv.ext.Beans.*;
import static io.github.up2jakarta.lov.core.Defaults.wrap;
import static io.github.up2jakarta.lov.core.Overrides.*;
import static java.util.stream.Collectors.joining;

/**
 * Internal business context.
 */
@PositionOverride(path = {})
final class BSContext<D extends ITerm<D>> {

    private static final boolean EAC = getProperty(ENABLE_CHECK, TypeListener.class.desiredAssertionStatus());
    private static final Position NAN = BSContext.class.getAnnotation(PositionOverride.class).value();

    private final Map<TypeResolver<?, ?>, Class<?>> cache = new LinkedHashMap<>();
    private final Map<Path, PositionOverride> positions = new LinkedHashMap<>();
    private final Map<Path, FragmentOverride> fragments = new LinkedHashMap<>();
    private final Map<Path, ValidOverride> validations = new LinkedHashMap<>();
    private final List<Class<? extends Segment>> stack = new LinkedList<>();
    private final List<TypeExtension<?, Annotation>> extensions;
    private final LinkedList<Field> path = new LinkedList<>();
    private final Class<? extends Segment> type;
    private final Optional<AccessType> access;
    private final Factory<D> factory;
    private final List<Field> links;
    private final List<SId<D>> ids;
    private final Listener checker;
    private final VContext context;
    private final Type[] arguments;
    private final int offset;
    private final Mode mode;

    BSContext(Factory<D> factory, Class<? extends Segment> type, Mode mode) throws BeanException {
        add(PositionOverride.class, type, this.positions::put, PositionOverride::path);
        add(FragmentOverride.class, type, this.fragments::put, FragmentOverride::path);
        add(ValidOverride.class, type, this.validations::put, ValidOverride::path);
        this.checker = Listener.of(type, factory.context, mode, EAC);
        this.access = getAccessType(Optional.empty(), type);
        this.extensions = extension(type, factory.context);
        this.context = VContext.from(type);
        this.links = new LinkedList<>();
        this.ids = new LinkedList<>();
        this.arguments = NO_TYPES;
        this.factory = factory;
        this.mode = mode;
        this.type = type;
        this.offset = 0;
    }

    private BSContext(BSContext<D> origin, int offset, Class<? extends Segment> type, Type... arguments) {
        this.access = getAccessType(origin.access, type);
        origin.path.forEach(this.path::addLast);
        origin.stack.forEach(this.stack::addLast);
        this.extensions = origin.extensions;
        this.context = origin.context;
        this.checker = origin.checker;
        this.factory = origin.factory;
        this.arguments = arguments;
        this.links = origin.links;
        this.type = origin.type;
        this.mode = origin.mode;
        this.ids = origin.ids;
        this.offset = offset;
    }

    private static List<TypeExtension<?, Annotation>> extension(Class<? extends Segment> st, Container bc) throws BeanException {
        final Extension[] extensions = getAnnotationsByType(Extension.class, st).toArray(Extension[]::new);
        final List<TypeExtension<?, Annotation>> result = new LinkedList<>();
        for (final Extension extension : extensions) {
            result.add(from(bc, extension.value(), extension.name()));
        }
        return List.copyOf(result);
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
        if (Character.class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2Character.class.getSimpleName());
        }
        if (OptionalInt.class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2OptionalInt.class.getSimpleName());
        }
        if (OptionalLong.class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2OptionalLong.class.getSimpleName());
        }
        if (OptionalDouble.class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2OptionalDouble.class.getSimpleName());
        }
        if (Date.class.isAssignableFrom(wrapper)) {
            return new BeanException(field, msg + Up2Date.class.getSimpleName());
        }
        final String cn = Up2Converter.class.getSimpleName();
        return new BeanException(field, "must be annotated with @" + cn + " or one of those shortcuts");
    }

    private <V> BSAccessor<V> accessor(Class<V> type, Field field) throws BeanException {
        final Class<? extends Segment> container = segmentType(stack);
        final AccessType access = getAccessType(field, this.access).orElse(AccessType.FIELD);
        if (Optional.class.isAssignableFrom(field.getType())) {
            //noinspection unchecked
            return Mode.wrap(mode, container, mode.of(access, container, field, (Class<Optional<V>>) type));
        }
        return mode.of(access, container, field, type);
    }

    private D dataType(Field field) throws BeanException {
        final TermResolver<D> resolver = factory.resolver;
        final List<Class<? extends Segment>> cs = cleanStack(stack);
        final D value = resolver.get(cs, path.toArray(Field[]::new), field).orElse(null);
        if (value != null && !resolver.getType().isAssignableFrom(value.getClass())) {
            throw new BeanException(value.getClass(), "invalid business term type");
        }
        return value;
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
                final TypeResolver<T, Annotation> cr = from(factory.context, resolver.value(), resolver.name());
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
            final TypeAdapter<T> adapter = from(factory.context, pc.value(), pc.name());
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

    private int innerIndex(Field fp, Class<? extends Segment> ft) throws BeanException {
        if (isInnerType(ft)) {
            final Class<?> ec = ft.getEnclosingClass();
            if (ec.isRecord()) {
                throw new BeanException(fp, "inner class is not allowed inside enclosing record: " + getTypeName(ec));
            }
            return stack.lastIndexOf(ec);
        }
        return -1;
    }

    private <S extends Segment> BSNode<S, D> node(Field fp, Class<S> ft, VContext vc, Fragment fr, List<BSProperty<?, D>> ps) throws BeanException {
        final int index = this.innerIndex(fp, ft);
        if (mode == Mode.RO) {
            return new BSNode.Flat<>(index, ft, this, vc, fr, ps);
        }
        return new BCR<>(ft, ps) {
            @Override
            Bean.CN<S, D> cn(ICreator<S> cs) throws BeanException {
                return new Bean.CN<>(index, ft, BSContext.this, vc, fr, ps, cs);
            }

            @Override
            Bean.JB<S, D> jb(ICreator<S> cs) throws BeanException {
                return new Bean.JB<>(index, ft, BSContext.this, vc, fr, ps, cs);
            }

            @Override
            Bean.JR<S, D> jr(ICreator<S> cs) throws BeanException {
                return new Bean.JR<>(ft, BSContext.this, vc, fr, ps, cs);
            }
        }.build();
    }

    private <T> PPosition<T, D> position(Class<T> pt, Field pf, Position pc) throws BeanException {
        final IProcessor<D> ps = IProcessor.build(factory.context, pf, pc);
        final D dataType = this.dataType(pf);
        final TypeAdapter<T> adapter = this.resolve(pf, pt, pc);
        final BSAccessor<T> va = this.accessor(pt, pf);
        return new PPosition<>(va, dataType, offset, pc, ps, adapter);
    }

    List<BSProperty<?, D>> build() throws BeanException {
        this.checker.beforeSegment(type);
        final List<BSProperty<?, D>> ps = BSBuilder.build(type, this);
        this.checker.afterSegment(checker);
        this.checkOverrides(Filter.ALL);
        return List.copyOf(ps);
    }

    List<Field> links() {
        return List.copyOf(links);
    }

    List<PId<D>> businessIds(List<BSProperty<?, D>> ps) throws BeanException {
        final List<PId<D>> list = new ArrayList<>(ids.size());
        for (final SId<D> id : ids) {
            list.add(id.build(ps, mode));
        }
        return List.copyOf(list);
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

    void afterSuperSegment() throws BeanException {
        final Class<? extends Segment> st = stack.getLast();
        this.checkOverrides((e, s) -> e == st);
        checker.afterSuperSegment(st);
    }

    void checkOverrides(Filter filter) throws BeanException {
        if (EAC) {
            final Filter valid = filter.and((e, s) -> s != 0);
            final List<BeanException> causes = new LinkedList<>();
            causes.addAll(check(validations.keySet(), ValidOverride.class, valid));
            causes.addAll(check(positions.keySet(), PositionOverride.class, filter));
            causes.addAll(check(fragments.keySet(), FragmentOverride.class, filter));
            rethrow(causes);
        }
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
        add(PositionOverride.class, segmentType, result.positions::putIfAbsent, PositionOverride::path);
        add(FragmentOverride.class, segmentType, result.fragments::putIfAbsent, FragmentOverride::path);
        add(ValidOverride.class, segmentType, result.validations::putIfAbsent, ValidOverride::path);
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
        result.validations.putAll(this.validations);
        result.positions.putAll(this.positions);
        result.fragments.putAll(this.fragments);
        add(PositionOverride.class, superType, result.positions::putIfAbsent, PositionOverride::path);
        add(FragmentOverride.class, superType, result.fragments::putIfAbsent, FragmentOverride::path);
        add(ValidOverride.class, superType, result.validations::putIfAbsent, ValidOverride::path);
        return result;
    }

    void unknown(Field fp, Class<?> type) throws BeanException {
        checker.unknownProperty(fp, type);
        if (fp.isAnnotationPresent(BusinessId.class)) {
            final PPosition<?, D> bid = this.position(type, fp, NAN);
            this.ids.add(new SId<>(List.copyOf(path), bid));
        }
        if (fp.getAnnotationsByType(BusinessLink.class).length != 0) {
            links.add(fp);
        }
    }

    Position position(Field field) {
        return get(Position.class, positions, field, PositionOverride::value, p -> p.value() >= 0);
    }

    Fragment fragment(Field field) {
        return get(Fragment.class, fragments, field, FragmentOverride::value, p -> p.value() >= 0);
    }

    <S extends Segment> PFragment<S, D> node(Class<S> ft, Field ff, Fragment fr, List<BSProperty<?, D>> ps) throws BeanException {
        checker.afterFragmentProperty(ff, ft);
        final ValidOverride override = get(ValidOverride.class, validations, ff, ValidOverride::path);
        final VContext vc = context.build(ff, ft, override);
        final BSAccessor<S> va = this.accessor(ft, ff);
        final BSNode<S, D> node = this.node(ff, ft, vc, fr, ps);
        return new PFragment<>(node, va, this.dataType(ff), offset, fr);
    }

    <T> PPosition<T, D> property(Class<T> pt, Field fp, Position pc) throws BeanException {
        checker.positionProperty(fp, pt, offset + pc.value());
        final PPosition<T, D> result = this.position(pt, fp, pc);
        if (fp.isAnnotationPresent(BusinessId.class)) {
            this.ids.add(new SId<>(List.copyOf(path), result));
        }
        return result;
    }

    Type fieldType(Field field) throws BeanException {
        return unwrapType(field, arguments);
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

        private VContext build(Field f, Class<? extends Segment> ft, ValidOverride vo) throws BeanException {
            if (enabled) {
                final boolean hasValid = isAnnotationPresent(f, Valid.class);
                final Set<Class<?>> groups = new HashSet<>(List.of(this.groups));
                if (vo == null) {
                    final VContext ctx = from(ft);
                    if (hasValid && ctx.enabled && groups.containsAll(List.of(ctx.groups))) {
                        return DISABLED;
                    }
                    return ctx;
                } else if (!vo.disable()) {
                    if (hasValid && groups.containsAll(List.of(vo.groups()))) {
                        return DISABLED;
                    }
                    return new VContext(true, checkGroups(vo.groups(), f));
                }
            }
            return DISABLED;
        }

        boolean enabled(Validator validator) {
            return enabled && validator != null;
        }
    }
}
