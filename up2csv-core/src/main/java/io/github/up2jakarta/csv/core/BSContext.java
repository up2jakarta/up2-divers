package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.BSNode.*;
import io.github.up2jakarta.csv.core.ext.Path;
import io.github.up2jakarta.csv.core.hdl.*;
import io.github.up2jakarta.csv.core.hdl.PProperty.POProperty;
import io.github.up2jakarta.csv.core.hdl.PProperty.PSProperty;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.TypeConverter;
import jakarta.persistence.AccessType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.csv.core.ext.Path.addOverride;
import static io.github.up2jakarta.csv.core.ext.Path.getOverride;
import static io.github.up2jakarta.csv.core.hdl.PAMode.RO;
import static io.github.up2jakarta.csv.core.hdl.PAMode.WO;
import static io.github.up2jakarta.csv.prc.DefaultProcessor.undefined;
import static jakarta.persistence.AccessType.PROPERTY;
import static java.lang.reflect.Modifier.isFinal;
import static java.util.Collections.unmodifiableList;

/**
 * Internal business context.
 */
final class BSContext<D extends DataType<D>> {

    private final Map<Path, PositionOverride> positions = new LinkedHashMap<>();
    private final Map<Path, FragmentOverride> fragments = new LinkedHashMap<>();
    private final Map<Path, ValidOverride> validations = new LinkedHashMap<>();
    private final Stack<Class<? extends Segment>> stack = new Stack<>();
    private final ConversionExtension<?, Annotation>[] extensions;
    private final LinkedList<Field> path = new LinkedList<>();
    private final Class<? extends Segment> type;
    private final DataTypeResolver<D> resolver;
    private final Optional<AccessType> access;
    private final BSBuilder.WChecker checker;
    private final Up2Factory<?> factory;
    private final VContext context;
    private final Type[] arguments;
    private final PAMode mode;
    private final int offset;

    private BSContext(Up2Factory<?> f, Class<? extends Segment> t, PAMode m, DataTypeResolver<D> d) throws BeanException {
        addOverride(PositionOverride.class, t, this.positions::put, PositionOverride::path);
        addOverride(FragmentOverride.class, t, this.fragments::put, FragmentOverride::path);
        addOverride(ValidOverride.class, t, this.validations::put, ValidOverride::path);
        this.access = BSBuilder.getAccessType(Optional.empty(), t);
        this.checker = BSBuilder.WChecker.of(t, f.context);
        this.extensions = ext(t, f.context);
        this.context = VContext.from(t);
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

    private static ConversionExtension<?, Annotation>[] ext(Class<? extends Segment> st, BeanContext bc) throws BeanException {
        final Extension[] extensions = BSBuilder.getAnnotationsByType(Extension.class, st).toArray(Extension[]::new);
        final List<ConversionExtension<?, ? extends Annotation>> result = new LinkedList<>();
        for (final Extension extension : extensions) {
            final ConversionExtension<?, ?> bean = getBean(bc, extension.value());
            if (bean.isActivated(st)) {
                result.add(bean);
            }
        }
        //noinspection unchecked
        return (ConversionExtension<?, Annotation>[]) result.toArray(ConversionExtension<?, ?>[]::new);
    }

    static <D extends DataType<D>, S extends Segment> BPNode<S, D, ?> build(Class<S> t, Up2Factory<?> f, DataTypeResolver<D> r) throws BeanException {
        final BSContext<D> mc = new BSContext<>(f, t, WO, r);
        if (t.isRecord()) {
            return new BRNode<>(t, f.validator, mc.context, mc.build());
        }
        return new BMNode<>(t, f.validator, mc.context, mc.build());
    }

    static <D extends DataType<D>, S extends Segment> BFNode<S, D> format(Class<S> t, Up2Factory<?> f, DataTypeResolver<D> r) throws BeanException {
        final BSContext<D> mc = new BSContext<>(f, t, RO, r);
        return new BFNode<>(f.validator, mc.context, mc.build());
    }

    private List<Property<?, D>> build() throws BeanException {
        this.checker.beforeSegment(type);
        final List<Property<?, D>> ps = BSBuilder.build(type, this);
        this.checker.afterSegment();
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
    private Conversion<?> conversion(Field field, Class<?> type, Position position) throws BeanException {
        final Up2Converter converter = field.getAnnotation(Up2Converter.class);
        final Error error = field.getAnnotation(Error.class);
        if (converter != null) {
            final TypeConverter<Object> tConverter = getBean(factory.context, converter.value());
            if (!tConverter.getSupportedType().isAssignableFrom(field.getType())) {
                throw new BeanException(field, "@Converter[value] does not support " + field.getType().getSimpleName());
            }
            return Conversion.of(tConverter, error);
        }
        for (final Annotation config : field.getAnnotations()) {
            final Resolver resolver = config.annotationType().getAnnotation(Resolver.class);
            if (resolver != null) {
                final ConversionResolver<Annotation> cr = getBean(factory.context, resolver.value());
                final PropertyFormatter<Object> f = (PropertyFormatter<Object>) cr.forFormatting(config, field, type);
                if (mode == RO && isFinal(field.getModifiers()) && undefined(position)) {
                    return new Conversion<>(null, f, error);
                }
                final PropertyConverter<Object> p = (PropertyConverter<Object>) cr.forParsing(config, field, type);
                return new Conversion<>(p, f, error);
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
        throw new BeanException(field, "must be annotated with @Up2Converter or one of its shortcuts");
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
        checker.beforeFragmentProperty(field, segmentType);
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

    BSContext<D> with(Class<? extends Segment> superType, Type... arguments) throws BeanException {
        checker.beforeSuperSegment(superType);
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

    <S extends Segment> PFProperty<S, D> node(Class<S> ft, Field ff, Fragment fr, List<Property<?, D>> ps) throws BeanException {
        checker.afterFragmentProperty(ff, ft);
        final ValidOverride override = getOverride(ValidOverride.class, validations, ff, ValidOverride::path);
        final VContext vc = context.build(ff, override);
        final PAccessor<?, S> va = this.accessor(ft, ff);
        if (mode == RO) {
            final BFNode<S, D> node = new BFNode<>(ft, factory.validator, vc, fr, ps);
            return new PFProperty<>(node, va, this.dataType(ff), offset, fr);
        } else if (ft.isRecord()) {
            final BRNode<S, D> node = new BRNode<>(ft, factory.validator, vc, fr, ps);
            return new PFProperty<>(node, va, this.dataType(ff), offset, fr);
        }
        final BPNode<S, D, ?> node = new BMNode<>(ft, factory.validator, vc, fr, ps);
        return new PFProperty<>(node, va, this.dataType(ff), offset, fr);
    }

    <V> PProperty<?, D> property(Class<V> pt, Field pf, Position position) throws BeanException {
        checker.positionProperty(pf, pt, offset + position.value());
        final PProcessor<D> ps = BSBuilder.build(factory.context, pf, position);
        final D dataType = this.dataType(pf);
        if (CharSequence.class == pt || pt == String.class) {
            return new PSProperty<>(this.accessor(String.class, pf), dataType, offset, position, ps);
        } else {
            final Conversion<?> cv = this.conversion(pf, pt, position);
            return new POProperty<>(this.accessor(pt, pf), dataType, offset, position, ps, cv);
        }
    }

    <V> PAccessor<Field, V> accessor(Class<V> type, Field field) throws BeanException {
        final Class<? extends Segment> container = getSegmentType(stack);
        final AccessType access = BSBuilder.getAccessType(field, this.access).orElse(PROPERTY);
        return mode.of(access, container, field, type);
    }

    Type fieldType(Field field) {
        return getPropertyType(field, arguments);
    }

}
