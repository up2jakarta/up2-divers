package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.PProperty.WProcessor;
import io.github.up2jakarta.csv.core.Property.Accessor;
import io.github.up2jakarta.csv.core.ext.Path;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.TypeConverter;
import jakarta.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

import static io.github.up2jakarta.csv.core.BeanScanner.getAnnotationsByType;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.csv.core.ext.Path.addOverride;
import static io.github.up2jakarta.csv.core.ext.Path.getOverride;

final class BSContext<D extends DataType<D>> {

    final BeanChecker checker;
    final io.github.up2jakarta.csv.api.ext.BeanContext context;
    final Type[] arguments;
    final int offset;

    private final Map<Path, PositionOverride> positions = new LinkedHashMap<>();
    private final Map<Path, FragmentOverride> fragments = new LinkedHashMap<>();
    private final Map<Path, ValidOverride> validations = new LinkedHashMap<>();
    private final Stack<Class<? extends Segment>> stack = new Stack<>();
    private final ConversionExtension<?, Annotation>[] extensions;
    private final LinkedList<Field> path = new LinkedList<>();
    private final Class<? extends Segment> type;
    private final DataTypeResolver<D> resolver;
    private final BeanContext validation;
    private final Validator validator;
    private final boolean readOnly;
    private final BSContext<D> p;

    public BSContext(io.github.up2jakarta.csv.api.ext.BeanContext c, Class<? extends Segment> t, boolean r, DataTypeResolver<D> d, BeanContext v, Validator l) throws BeanException {
        addOverride(PositionOverride.class, t, this.positions::put, PositionOverride::path);
        addOverride(FragmentOverride.class, t, this.fragments::put, FragmentOverride::path);
        addOverride(ValidOverride.class, t, this.validations::put, ValidOverride::path);
        this.extensions = getExtensions(t, c);
        this.checker = BeanChecker.of(t, c);
        this.checker.afterSegment();
        this.arguments = NO_TYPES;
        this.validation = v;
        this.validator = l;
        this.resolver = d;
        this.readOnly = r;
        this.context = c;
        this.offset = 0;
        this.type = t;
        this.p = null;
    }

    private BSContext(BSContext<D> origin, int offset, Class<?> type, Type... arguments) throws BeanException {
        addOverride(PositionOverride.class, type, this.positions::put, PositionOverride::path);
        addOverride(FragmentOverride.class, type, this.fragments::put, FragmentOverride::path);
        addOverride(ValidOverride.class, type, this.validations::put, ValidOverride::path);
        origin.stack.forEach(this.stack::push);
        origin.path.forEach(this.path::addLast);
        this.extensions = origin.extensions;
        this.validation = origin.validation;
        this.validator = origin.validator;
        this.resolver = origin.resolver;
        this.readOnly = origin.readOnly;
        this.checker = origin.checker;
        this.context = origin.context;
        this.arguments = arguments;
        this.type = origin.type;
        this.offset = offset;
        this.p = origin;
    }

    private static ConversionExtension<?, Annotation>[] getExtensions(Class<? extends Segment> type, io.github.up2jakarta.csv.api.ext.BeanContext context) throws BeanException {
        final Extension[] extensions = getAnnotationsByType(Extension.class, type).toArray(Extension[]::new);
        final List<ConversionExtension<?, ? extends Annotation>> result = new LinkedList<>();
        for (final Extension extension : extensions) {
            final ConversionExtension<?, ?> bean = getBean(context, extension.value());
            if (bean.isActivated(type)) {
                result.add(bean);
            }
        }
        //noinspection unchecked
        return (ConversionExtension<?, Annotation>[]) result.toArray(ConversionExtension<?, ?>[]::new);
    }

    private Class<?> fieldType(Field field, TypeVariable<?> tv) {
        final Type[] parameters = field.getDeclaringClass().getTypeParameters();
        for (var i = 0; i < parameters.length; i++) {
            if (parameters[i] == tv) {
                if (i < arguments.length) {
                    if (arguments[i] instanceof Class<?> ft) {
                        return ft;
                    }
                    if (arguments[i] instanceof TypeVariable<?> av) {
                        final Field sf = path.getLast();
                        return p.fieldType(sf, av);
                    }
                }
            }
        }
        return field.getClass();
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
    private Conversion<?> conversion(Field field, Class<?> type) throws BeanException {
        final Up2Converter converter = field.getAnnotation(Up2Converter.class);
        final Error error = field.getAnnotation(Error.class);
        if (converter != null) {
            final TypeConverter<Object> tConverter = (TypeConverter<Object>) getBean(context, converter.value());
            if (!tConverter.getSupportedType().isAssignableFrom(field.getType())) {
                throw new BeanException(field, "@Converter[value] does not support " + field.getType().getSimpleName());
            }
            if (error == null) {
                var p = PropertyConverter.of(tConverter::parse, tConverter.getErrorSeverity(), tConverter.getErrorCode());
                var f = PropertyFormatter.of(tConverter::format, tConverter.getErrorSeverity(), tConverter.getErrorCode());
                return new Conversion<>(p, f);
            }
            return new Conversion<>(tConverter::parse, tConverter::format, error);
        }
        for (final Annotation annotation : field.getAnnotations()) {
            final Resolver resolver = annotation.annotationType().getAnnotation(Resolver.class);
            if (resolver != null) {
                var cResolver = (ConversionResolver<Annotation>) getBean(context, resolver.value());
                final PropertyFormatter<Object> f = (PropertyFormatter<Object>) cResolver.forFormatting(annotation, field);
                if (readOnly && !field.isAnnotationPresent(Up2Default.class)) {
                    return new Conversion<>(null, f, error);
                }
                final PropertyConverter<Object> p = (PropertyConverter<Object>) cResolver.forParsing(annotation, field, type);
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

    BSContext<D> with(Field field, int offset, Class<? extends Segment> segmentType) throws BeanException {
        final Type[] arguments = getTypeArguments(field.getGenericType());
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
        final BSContext<D> result = new BSContext<>(this, this.offset, superType, arguments);
        result.positions.putAll(positions);
        result.fragments.putAll(fragments);
        result.validations.putAll(validations);
        return result;
    }

    Position position(Field field) {
        return getOverride(Position.class, positions, field, PositionOverride::value, p -> p.value() >= 0);
    }

    Fragment fragment(Field field) {
        return getOverride(Fragment.class, fragments, field, FragmentOverride::value, p -> p.value() >= 0);
    }

    <S extends Segment> PFProperty<S, D> node(Class<S> ft, Field ff, int fo, boolean nl, List<Property<?, D>> ps) throws BeanException {
        final ValidOverride override = getOverride(ValidOverride.class, validations, ff, ValidOverride::path);
        final BeanContext vc = validation.build(ff, override);
        final Accessor<S> va = Accessor.of(readOnly, ft, ff);
        if (readOnly) {
            final Up2Format.Node<S, D> node = new Up2Format.Node<>(validator, vc, nl, ps);
            return new PFProperty<>(node, va, this.dataType(ff), fo);
        }
        final Up2Mapper.Node<S, D> node = new Up2Mapper.Node<>(ft, validator, vc, nl, ps);
        return new PFProperty<>(node, va, this.dataType(ff), fo);
    }

    <V> PProperty<?, D> leaf(Class<V> pt, Field pf, int po, boolean pr) throws BeanException {
        final List<WProcessor<?, D>> processors = BeanScanner.getProcessors(context, pf);
        final D dataType = this.dataType(pf);
        if (CharSequence.class == pt || pt == String.class) {
            return new PProperty.PSProperty<>(Accessor.of(readOnly, String.class, pf), dataType, po, pr, processors);
        } else {
            final Conversion<?> conversion = this.conversion(pf, pt);
            return new PProperty.POProperty<>(Accessor.of(readOnly, pt, pf), dataType, po, pr, processors, conversion);
        }
    }

    Class<?> fieldType(Field field) {
        final Type type = field.getGenericType();
        if (type instanceof TypeVariable<?> tv) {
            return this.fieldType(field, tv);
        }
        if (type instanceof ParameterizedType pt) {
            return (Class<?>) pt.getRawType();
        }
        return field.getType();
    }

}
