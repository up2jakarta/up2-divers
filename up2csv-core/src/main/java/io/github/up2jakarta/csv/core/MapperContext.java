package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Extension;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static io.github.up2jakarta.csv.core.BeanSupport.getAnnotationsByType;
import static io.github.up2jakarta.csv.core.Beans.getBean;
import static io.github.up2jakarta.csv.core.Beans.getSegmentType;

final class MapperContext<D extends DataType<D>> {

    private final Stack<Class<? extends Segment>> stack = new Stack<>();
    private final ConversionExtension<?, Annotation>[] extensions;
    private final LinkedList<Field> path = new LinkedList<>();
    private final Class<? extends Segment> type;
    private final DataTypeResolver<D> resolver;
    private final CompositeChecker checker;
    private final BeanContext context;
    private final Type[] arguments;
    private final int offset;

    public MapperContext(BeanContext context, Class<? extends Segment> type, DataTypeResolver<D> resolver) throws BeanException {
        this.checker = CompositeChecker.of(type, context);
        this.extensions = getExtensions(type, context);
        this.arguments = Beans.NO_TYPES;
        this.resolver = resolver;
        this.context = context;
        this.type = type;
        this.offset = 0;
    }

    private MapperContext(MapperContext<D> origin, int offset, Type... arguments) {
        this.extensions = origin.extensions;
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

    MapperContext<D> with(Field field, int offset) {
        final Type[] arguments = Beans.getTypeArguments(field.getGenericType());
        final MapperContext<D> result = new MapperContext<>(this, offset, arguments);
        result.path.addLast(field);
        return result;
    }

    MapperContext<D> with(Type... arguments) {
        return new MapperContext<>(this, this.offset, arguments);
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
            final Field[] fieldPath = path.toArray(Field[]::new);
            final Class<? extends Segment> segmentType = getSegmentType(stack);
            final D value = resolver.get(segmentType, field, fieldPath).orElse(null);
            if (value != null) {
                resolver.check(value);
            }
            return value;
        }
        return null;
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
