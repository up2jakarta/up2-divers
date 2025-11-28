package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.api.hdl.EventLevel;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSManager.Pod;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.hdl.EventHandler;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static io.github.up2jakarta.csv.api.IEvent.EC_PROCESSOR;
import static io.github.up2jakarta.csv.core.BSBuilder.Input.*;
import static io.github.up2jakarta.csv.core.BSOperator.BId;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.csv.prc.DefaultProcessor.undefined;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static java.util.Collections.unmodifiableList;

/**
 * Internal business builder.
 */
@SuppressWarnings("unchecked")
final class BSBuilder {

    private BSBuilder() {
    }

    private static Class<?> checkProperty(Field field, Type type, Position csv) throws BeanException {
        if (csv.value() < 0) {
            throw new BeanException(field, "@Position[value] must be positive");
        }
        if (field.isAnnotationPresent(Valid.class)) {
            throw new BeanException(field, "must not be annotated with @Valid");
        }
        if (field.getAnnotationsByType(ValidOverride.class).length != 0) {
            throw new BeanException(field, "must not be annotated with @ValidOverride");
        }
        if (field.getAnnotationsByType(PositionOverride.class).length != 0) {
            throw new BeanException(field, "must not be annotated with @PositionOverride");
        }
        if (field.getAnnotationsByType(FragmentOverride.class).length != 0) {
            throw new BeanException(field, "must not be annotated with @FragmentOverride");
        }
        return getPropertyClass(field, type);
    }

    private static Method getRepeatableValue(Class<? extends Annotation> annotationType) throws BeanException {
        final Repeatable repeatable = annotationType.getDeclaredAnnotation(Repeatable.class);
        if (repeatable != null) {
            return getMethod(repeatable.value(), "value", CLASS, "value()");
        }
        return null;
    }

    private static Class<? extends Segment> checkFragment(Field field, Type type, Fragment csv) throws BeanException {
        final Class<?> fType = getPropertyClass(field, type);
        if (csv.value() < 0) {
            throw new BeanException(field, "@Fragment[value] must be positive");
        }
        if (Segment.class.isAssignableFrom(fType)) {
            return (Class<? extends Segment>) fType;
        }
        throw new BeanException(field, "type must implements Segment");
    }

    static <D extends DataType<D>> WP<?, D> build(BeanContext ctx, Annotation ppa) throws BeanException {
        final Class<? extends Annotation> type = ppa.annotationType();
        final Processor processor = type.getAnnotation(Processor.class);
        final Class<? extends InputProcessor<?>> pType = processor.value();
        final Class<?> support = getTypeArgument(pType, InputProcessor.class, 0, void.class);
        if (type != support) {
            final CharSequence cn = getTypeName(type);
            throw new BeanException(type, "@Processor[value] must implements InputProcessor<" + cn + ">");
        }
        final InputProcessor<Annotation> delegate = getBean(ctx, pType, processor.name());
        return new WP<>(delegate, processor.skip(), ppa);
    }

    static <D extends DataType<D>> Input<D> build(BeanContext ctx, Field pf, Position pc) throws BeanException {
        final List<WP<?, D>> result = new LinkedList<>();
        if (!undefined(pc)) {
            result.addFirst(build(ctx, pc));
        }
        for (final Annotation ppa : pf.getAnnotations()) {
            final Class<? extends Annotation> type = ppa.annotationType();
            if (type.isAnnotationPresent(Processor.class) && type != Position.class) {
                result.addLast(build(ctx, ppa));
            }
        }
        if (result.isEmpty()) {
            return (Input<D>) NP.INSTANCE;
        }
        if (result.size() == 1) {
            return result.getFirst();
        }
        return new CP<>(result);
    }

    static Optional<AccessType> getAccessType(Optional<AccessType> first, Class<? extends Segment> type) {
        if (first.isEmpty()) {
            return getAccessType(type, first);
        }
        return first;
    }

    static Optional<AccessType> getAccessType(AnnotatedElement property, Optional<AccessType> other) {
        final Access jpa = property.getAnnotation(Access.class);
        if (jpa != null) {
            return Optional.of(jpa.value());
        }
        return other;
    }

    static <A extends Annotation> List<A> getAnnotationsByType(Class<A> type, AnnotatedElement element) throws BeanException {
        final Method repeatValue = getRepeatableValue(type);
        final List<A> result = new LinkedList<>();
        for (final Annotation annotation : element.getAnnotations()) {
            final Class<? extends Annotation> aType = annotation.annotationType();
            // direct
            if (type.equals(aType)) {
                result.add((A) annotation);
            }
            // indirect
            if (repeatValue != null && repeatValue.getDeclaringClass().equals(aType)) {
                try {
                    final A[] indirectArray = (A[]) repeatValue.invoke(annotation);
                    result.addAll(Arrays.asList(indirectArray));
                } catch (Exception t) {
                    throw new BeanException(repeatValue.getDeclaringClass(), t.getMessage());
                }
            }
            // shortcuts
            final A[] shortcutArray = aType.getAnnotationsByType(type);
            result.addAll(Arrays.asList(shortcutArray));
        }
        return result;
    }

    static <A extends Annotation> void id(BSNode<?, ?> pn, Class<A> ct, BIdPath bc, PFragment<Segment, ?, ?>... pp) throws BeanException {
        for (final BSProperty<?, ?, ?> p : pn.properties) {
            final A annotation = p.getAnnotation(ct);
            if (p instanceof PFragment<?, ?, ?> fp) {
                if (annotation != null) {
                    throw new BeanException(p.getSource(), "must not be annotated with @" + getTypeName(ct));
                }
                id(fp.node, ct, bc, concat(pp, (PFragment<Segment, ?, ?>) fp));
            } else if (annotation != null) {
                bc.accept(pp, (PPosition<Object, Object, ?>) p);
            }
        }
    }

    static <D extends DataType<D>> List<BSProperty<?, ?, D>> build(Class<? extends Segment> beanType, BSContext<D> context) throws BeanException {
        if (context.push(beanType)) {
            throw new BeanException(beanType, "cyclic fragment is not allowed");
        }
        final List<BSProperty<?, ?, D>> result = new LinkedList<>();
        final Class<?> superClass = beanType.getSuperclass();
        if (Segment.class.isAssignableFrom(superClass)) {
            final Class<? extends Segment> superType = (Class<? extends Segment>) superClass;
            final BSContext<D> superContext = context.with(superType, beanType.getGenericSuperclass());
            final List<BSProperty<?, ?, D>> superProperties = build(superType, superContext);
            superContext.end();
            result.addAll(superProperties);
        }
        final Field[] fields = beanType.getDeclaredFields();
        for (final Field field : fields) {
            final Type fieldType = context.fieldType(field);
            final Fragment fragment = context.fragment(field);
            final Position position = context.position(field);
            if (fragment != null) {
                final Class<? extends Segment> fType = checkFragment(field, fieldType, fragment);
                final List<BSProperty<?, ?, D>> fps = build(fType, context.with(field, fragment, fType));
                if (!(fragment.nullable() && fps.isEmpty())) {
                    try {
                        result.add(context.node(fType, field, fragment, fps));
                    } catch (AccessException ex) {
                        throw new BeanException(field, ex.getMessage());
                    }
                }
            } else if (position != null) {
                final Class<?> fieldClass = checkProperty(field, fieldType, position);
                final BSProperty<?, ?, D> property = context.property(fieldClass, field, position);
                result.add(property);
            } else {
                final Class<?> type = getPropertyClass(field, fieldType);
                context.unknown(field, type);
            }
        }
        return result;
    }

    interface BIdPath {
        void accept(PFragment<Segment, ?, ?>[] path, PPosition<Object, Object, ?> property) throws BeanException;
    }

    /**
     * Class Marker for Stack Trace.
     */
    sealed interface MST permits BSProperty, Input, BSAccessor, BSNode {
    }

    /**
     * Class Marker for Entry Point.
     */
    sealed interface MEP permits Pod, BId, BeanLinker {
    }

    /**
     * Internal Property processor.
     */
    static abstract sealed class Input<D extends DataType<D>> implements MST permits WP, CP, NP {

        final <T, V> T parse(PPosition<T, V, D> p, TypeAdapter<T> adapter) throws BeanException {
            try {
                final String value = this.process(null, 0, p, null);
                if (value != null) {
                    return adapter.parse(value);
                }
                return null;
            } catch (Exception cause) {
                throw new BeanException(p.getSource(), "@Position[defaultValue] cannot be parsed");
            }
        }

        final <T> String format(Member source, T value, TypeAdapter<T> adapter) throws BeanException {
            try {
                if (value != null) {
                    return adapter.format(value);
                }
                return null;
            } catch (Exception cause) {
                throw new BeanException(source, "@Position[defaultValue] cannot be formatted");
            }
        }

        abstract String process(String value, int offset, PPosition<?, ?, D> property, EventHandler<D> handler);

        /**
         * Internal composite processor.
         */
        static final class CP<D extends DataType<D>> extends Input<D> {
            private final List<WP<?, D>> processors;

            private CP(List<WP<?, D>> processors) {
                this.processors = unmodifiableList(processors);
            }

            @Override
            public String process(String value, int offset, PPosition<?, ?, D> property, EventHandler<D> handler) {
                for (final WP<?, D> processor : processors) {
                    value = processor.process(value, offset, property, handler);
                }
                return value;
            }
        }

        /**
         * Internal processor wrapper.
         */
        static final class WP<A extends Annotation, D extends DataType<D>> extends Input<D> {
            private final A config;
            private final InputProcessor<A> delegate;
            private final Class<? extends RuntimeException> skip;

            private WP(InputProcessor<A> delegate, Class<? extends RuntimeException> skip, A config) {
                this.delegate = delegate;
                this.config = config;
                this.skip = skip;
            }

            @Override
            public String process(String value, int offset, PPosition<?, ?, D> property, EventHandler<D> handler) {
                try {
                    return delegate.process(value, config);
                } catch (RuntimeException cause) {
                    offset += property.offset;
                    if (property.error != null) {
                        handler.handle(property.dataType, offset, cause, property.error);
                    } else {
                        final EventLevel level = () -> skip.isInstance(cause) ? WARNING : ERROR;
                        handler.handle(level, () -> EC_PROCESSOR, property.dataType, offset, cause);
                    }
                    return value;
                }
            }
        }

        /**
         * Internal Null wrapper.
         */
        static final class NP<D extends DataType<D>> extends Input<D> {
            private static final NP<?> INSTANCE = new NP<>();

            private NP() {
            }

            @Override
            public String process(String value, int offset, PPosition<?, ?, D> property, EventHandler<D> handler) {
                return value;
            }
        }
    }

    /**
     * Internal composite checker.
     */
    static final class Listener implements TypeContext, TypeListener {

        private final TypeListener[] listeners;
        private final TypeContext[] contexts;

        private Listener(TypeListener[] listeners, TypeContext[] contexts) {
            this.listeners = listeners;
            this.contexts = contexts;
        }

        static Listener of(Class<? extends Segment> type, BeanContext context, Mode mode) throws BeanException {
            if (type.getTypeParameters().length != 0) {
                throw new BeanException(type, "generic class is not allowed");
            }
            final Checker[] checkers = getAnnotationsByType(Checker.class, type).toArray(Checker[]::new);
            final List<TypeListener> result = new LinkedList<>();
            result.add(BeanChecker.of(mode));
            for (final Checker checker : checkers) {
                final TypeListener bean = getBean(context, checker.value(), checker.name());
                if (bean.isActivated(type)) {
                    result.add(bean);
                }
            }
            final TypeListener[] listeners = result.toArray(TypeListener[]::new);
            final TypeContext[] contexts = new TypeContext[listeners.length];
            return new Listener(listeners, contexts);
        }

        @Override
        public Listener beforeSegment(Class<? extends Segment> type) throws BeanException {
            for (var i = 0; i < listeners.length; i++) {
                contexts[i] = listeners[i].beforeSegment(type);
            }
            return this;
        }

        @Override
        public void afterSegment(TypeContext context) throws BeanException {
            for (var i = 0; i < this.listeners.length; i++) {
                this.listeners[i].afterSegment(contexts[i]);
            }
            context.close();
        }

        @Override
        public void beforeSuperSegment(Class<? extends Segment> type) throws BeanException {
            for (final TypeContext context : contexts) {
                context.beforeSuperSegment(type);
            }
        }

        @Override
        public void afterSuperSegment(Class<? extends Segment> type) throws BeanException {
            for (final TypeContext context : contexts) {
                context.afterSuperSegment(type);
            }
        }

        @Override
        public void beforeFragmentProperty(Field fragment, Class<? extends Segment> type, int offset) throws BeanException {
            for (final TypeContext context : contexts) {
                context.beforeFragmentProperty(fragment, type, offset);
            }
        }

        @Override
        public void afterFragmentProperty(Field fragment, Class<? extends Segment> type) throws BeanException {
            for (final TypeContext context : contexts) {
                context.afterFragmentProperty(fragment, type);
            }
        }

        @Override
        public void positionProperty(Field property, Class<?> type, int offset) throws BeanException {
            for (final TypeContext context : contexts) {
                context.positionProperty(property, type, offset);
            }
        }

        @Override
        public void unknownProperty(Field property, Class<?> type) throws BeanException {
            for (final TypeContext context : contexts) {
                context.unknownProperty(property, type);
            }
        }

    }
}
