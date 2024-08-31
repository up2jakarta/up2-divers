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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static io.github.up2jakarta.csv.core.BeanSupport.getAnnotationsByType;
import static io.github.up2jakarta.csv.core.Beans.getBean;

final class MapperContext {

    private final ConversionExtension<?, Annotation>[] extensions;
    private final Stack<Class<? extends Segment>> stack;
    private final Class<? extends Segment> type;
    private final BeanContext context;
    private final Type[] arguments;
    private final int offset;

    public MapperContext(BeanContext context, Class<? extends Segment> type) throws BeanException {
        this.extensions = getExtensions(type, context);
        this.arguments = Beans.NO_TYPES;
        this.stack = new Stack<>();
        this.context = context;
        this.type = type;
        this.offset = 0;
    }

    private MapperContext(MapperContext origin, int offset, Type... arguments) {
        this.extensions = origin.extensions;
        this.type = origin.type;
        this.stack = origin.newStack();
        this.context = origin.context;
        this.offset = offset;
        this.arguments = arguments;
    }

    @SuppressWarnings("unchecked")
    private static ConversionExtension<?, Annotation>[] getExtensions(Class<? extends Segment> type, BeanContext context) throws BeanException {
        final Extension[] extensions = getAnnotationsByType(Extension.class, type).toArray(Extension[]::new);
        final List<ConversionExtension<?, ? extends Annotation>> result = new LinkedList<>();
        for (final Extension extension : extensions) {
            final ConversionExtension<?, ?> bean = getBean(context, extension.value());
            if (bean.isActivated(type)) {
                result.add(bean);
            }
        }
        return (ConversionExtension<?, Annotation>[]) result.toArray(ConversionExtension<?, ?>[]::new);
    }

    private Stack<Class<? extends Segment>> newStack() {
        final Stack<Class<? extends Segment>> copy = new Stack<>();
        copy.addAll(this.stack);
        return copy;
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

    boolean push(Class<? extends Segment> beanType) {
        if (stack.contains(beanType)) {
            return true;
        }
        this.stack.push(beanType);
        return false;
    }

    MapperContext with(int offset) {
        return new MapperContext(this, offset, this.arguments);
    }

    MapperContext with(Type... arguments) {
        return new MapperContext(this, this.offset, arguments);
    }

    Conversion<?> getConversion(Field field) throws BeanException {
        for (final ConversionExtension<?, Annotation> extension : extensions) {
            final Optional<Annotation> config = extension.get(type, field);
            if (config.isPresent()) {
                return extension.resolve(field, config.get());
            }
        }
        throw new BeanException(field, "must be annotated with @Converter or one of its shortcuts");
    }

}
