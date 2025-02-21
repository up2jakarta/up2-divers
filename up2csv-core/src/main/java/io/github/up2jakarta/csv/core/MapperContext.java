package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Extension;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.Conversion;
import io.github.up2jakarta.csv.extension.ConversionExtension;
import io.github.up2jakarta.csv.extension.Segment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

import static io.github.up2jakarta.csv.core.BeanSupport.getAnnotationsByType;
import static io.github.up2jakarta.csv.core.Beans.getBean;

final class MapperContext {

    private final Stack<Class<? extends Segment>> stack = new Stack<>();
    private final ConversionExtension<?, Annotation>[] extensions;
    private final LinkedList<Field> path = new LinkedList<>();
    private final Class<? extends Segment> type;
    private final CompositeChecker checker;
    private final BeanContext context;
    private final Type[] arguments;
    private final int offset;

    public MapperContext(BeanContext context, Class<? extends Segment> type) throws BeanException {
        this.checker = CompositeChecker.of(type, context);
        this.extensions = getExtensions(type, context);
        this.arguments = Beans.NO_TYPES;
        this.context = context;
        this.type = type;
        this.offset = 0;
    }

    private MapperContext(MapperContext origin, int offset, Type... arguments) {
        this.extensions = origin.extensions;
        this.checker = origin.checker;
        this.type = origin.type;
        this.context = origin.context;
        this.offset = offset;
        this.arguments = arguments;
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

    MapperContext with(Field field, int offset) {
        final Type[] arguments = Beans.getTypeArguments(field.getGenericType());
        final MapperContext result = new MapperContext(this, offset, arguments);
        result.path.addLast(field);
        return result;
    }

    MapperContext with(Type... arguments) {
        return new MapperContext(this, this.offset, arguments);
    }

    Conversion<?> getConversion(Field field, Class<?> type) throws BeanException {
        final Field[] fieldPath = path.toArray(Field[]::new);
        for (final ConversionExtension<?, Annotation> extension : extensions) {
            final Iterator<Class<? extends Segment>> it = stack.iterator();
            Class<? extends Segment> segmentType = stack.peek();
            while (it.hasNext()) {
                final Class<? extends Segment> superType = it.next();
                if (segmentType.isAssignableFrom(superType)) {
                    segmentType = superType;
                    break;
                }
            }
            final Optional<Annotation> config = extension.get(segmentType, field, type, fieldPath);
            if (config.isPresent()) {
                return extension.resolve(field, type, config.get());
            }
        }
        throw new BeanException(field, "must be annotated with @Up2Converter or one of its shortcuts");
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
