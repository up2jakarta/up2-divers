package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.*;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionExtension;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.misc.Path;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

import static io.github.up2jakarta.csv.core.BeanSupport.getAnnotationsByType;
import static io.github.up2jakarta.csv.misc.Beans.*;
import static io.github.up2jakarta.csv.misc.Path.addOverride;
import static io.github.up2jakarta.csv.misc.Path.getOverride;

final class MapperContext<D extends DataType<D>> {
    private final Map<Path, PositionOverride> positions = new LinkedHashMap<>();
    private final Map<Path, FragmentOverride> fragments = new LinkedHashMap<>();
    private final Map<Path, ValidOverride> validations = new LinkedHashMap<>();
    private final Stack<Class<? extends Segment>> stack = new Stack<>();
    private final ConversionExtension<?, Annotation>[] extensions;
    private final LinkedList<Field> path = new LinkedList<>();
    private final Class<? extends Segment> type;
    private final DataTypeResolver<D> resolver;
    private final ValidationContext validator;
    private final CompositeChecker checker;
    private final BeanContext context;
    private final Type[] arguments;
    private final int offset;

    public MapperContext(BeanContext context, Class<? extends Segment> type, DataTypeResolver<D> resolver, ValidationContext v) throws BeanException {
        addOverride(PositionOverride.class, type, this.positions::put, PositionOverride::path);
        addOverride(FragmentOverride.class, type, this.fragments::put, FragmentOverride::path);
        addOverride(ValidOverride.class, type, this.validations::put, ValidOverride::path);
        this.checker = CompositeChecker.of(type, context);
        this.extensions = getExtensions(type, context);
        this.arguments = NO_TYPES;
        this.resolver = resolver;
        this.context = context;
        this.validator = v;
        this.type = type;
        this.offset = 0;
    }

    private MapperContext(MapperContext<D> origin, int offset, Class<?> type, Type... arguments) throws BeanException {
        addOverride(PositionOverride.class, type, this.positions::put, PositionOverride::path);
        addOverride(FragmentOverride.class, type, this.fragments::put, FragmentOverride::path);
        addOverride(ValidOverride.class, type, this.validations::put, ValidOverride::path);
        this.extensions = origin.extensions;
        this.validator = origin.validator;
        this.resolver = origin.resolver;
        this.checker = origin.checker;
        this.context = origin.context;
        this.arguments = arguments;
        this.type = origin.type;
        this.offset = offset;
        origin.stack.forEach(this.stack::push);
        origin.path.forEach(this.path::addLast);
    }

    private static ConversionExtension<?, Annotation>[] getExtensions(Class<? extends Segment> type, BeanContext context) throws BeanException {
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

    boolean push(Class<? extends Segment> beanType) {
        if (stack.contains(beanType)) {
            return true;
        }
        this.stack.push(beanType);
        return false;
    }

    MapperContext<D> with(Field field, int offset, Class<? extends Segment> segmentType) throws BeanException {
        final Type[] arguments = getTypeArguments(field.getGenericType());
        final MapperContext<D> result = new MapperContext<>(this, offset, segmentType, arguments);
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

    MapperContext<D> with(Class<? extends Segment> superType, Type... arguments) throws BeanException {
        final MapperContext<D> result = new MapperContext<>(this, this.offset, superType, arguments);
        result.positions.putAll(positions);
        result.fragments.putAll(fragments);
        result.validations.putAll(validations);
        return result;
    }

    Conversion<?> getConversion(Field field, Class<?> type) throws BeanException {
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

    D getDataType(Field field) throws BeanException {
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

    Position getPosition(Field field) {
        return getOverride(Position.class, positions, field, PositionOverride::value, p -> p.value() >= 0);
    }

    Fragment getFragment(Field field) {
        return getOverride(Fragment.class, fragments, field, FragmentOverride::value, p -> p.value() >= 0);
    }

    ValidationContext getValidation(Field field) throws BeanException {
        final ValidOverride override = getOverride(ValidOverride.class, validations, field, ValidOverride::path);
        return validator.build(field, override);
    }

    CompositeChecker getChecker() {
        return checker;
    }

    BeanContext getContext() {
        return context;
    }

    int getOffset() {
        return offset;
    }

    Type[] getArguments() {
        return arguments;
    }

}
