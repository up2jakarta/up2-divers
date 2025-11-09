package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.api.ext.CheckerContext;
import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.api.ext.SegmentListener;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.BSNode.BFNode;
import io.github.up2jakarta.csv.core.BSNode.BPNode;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.core.hdl.*;
import io.github.up2jakarta.csv.core.hdl.FProperty.FOProperty;
import io.github.up2jakarta.csv.core.hdl.FProperty.FWProperty;
import io.github.up2jakarta.csv.core.hdl.PProperty.POProperty;
import io.github.up2jakarta.csv.core.hdl.PProperty.PWProperty;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_PROCESSOR;
import static io.github.up2jakarta.csv.core.ext.Beans.getBean;
import static io.github.up2jakarta.csv.core.ext.Beans.getPropertyClass;
import static io.github.up2jakarta.csv.prc.DefaultProcessor.undefined;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static java.util.Collections.unmodifiableList;

final class BSBuilder {

    private BSBuilder() {
    }

    static <D extends DataType<D>> PWrapper<?, D> build(BeanContext ctx, Annotation ppa) throws BeanException {
        final Class<? extends Annotation> type = ppa.annotationType();
        final Processor prc = type.getAnnotation(Processor.class);
        final Class<? extends InputProcessor<?>> pType = prc.value();
        final Type[] types = Beans.getTypeArguments(pType, InputProcessor.class);
        if (types.length == 0 || type != types[0]) {
            final String aName = type.getSimpleName();
            throw new BeanException(type, "@Processor[value] must implements InputProcessor<" + aName + ">");
        }
        final InputProcessor<Annotation> delegate = getBean(ctx, pType);
        return new PWrapper<>(delegate, prc.skip(), ppa);
    }

    static <D extends DataType<D>> PProcessor<D> build(BeanContext ctx, Field pf, Position pc) throws BeanException {
        final List<PWrapper<?, D>> result = new LinkedList<>();
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
            return (v, o, p, h) -> v;
        }
        if (result.size() == 1) {
            return result.getFirst();
        }
        return new LWrapper<>(result);
    }

    private static Class<? extends Segment> checkFragment(Field field, Type type, Fragment csv) throws BeanException {
        final Class<?> fType = getPropertyClass(field, type);
        if (csv.value() < 0) {
            throw new BeanException(field, "@Fragment[value] must be positive");
        }
        if (Segment.class.isAssignableFrom(fType)) {
            //noinspection unchecked
            return (Class<? extends Segment>) fType;

        }
        throw new BeanException(field, "type must implements Segment");
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
            return Beans.getMethod(repeatable.value(), "value", "class", "value()");
        }
        return null;
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

    static <A extends Annotation> List<A> getAnnotationsByType(Class<A> annotationType, AnnotatedElement element) throws BeanException {
        final Method repeatValue = getRepeatableValue(annotationType);
        final List<A> result = new LinkedList<>();
        for (final Annotation annotation : element.getAnnotations()) {
            final Class<? extends Annotation> aType = annotation.annotationType();
            // direct
            if (annotationType.equals(aType)) {
                //noinspection unchecked
                result.add((A) annotation);
            }
            // indirect
            if (repeatValue != null && repeatValue.getDeclaringClass().equals(aType)) {
                try {
                    //noinspection unchecked
                    final A[] indirectArray = (A[]) repeatValue.invoke(annotation);
                    result.addAll(Arrays.asList(indirectArray));
                } catch (Exception t) {
                    throw new BeanException(repeatValue.getDeclaringClass(), t.getMessage());
                }
            }
            // shortcuts
            final A[] shortcutArray = aType.getAnnotationsByType(annotationType);
            result.addAll(Arrays.asList(shortcutArray));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static <S extends Segment, B extends DataType<B>> FProperty<S, ?, B> reverse(FProperty<S, ?, B> source) throws BeanException {
        final BSNode<S, B> node;
        if (source.node instanceof BFNode<S, B> fn) {
            node = reverse(fn);
        } else {
            node = new BFNode<>((BPNode<S, B, ?>) source.node);
        }
        if (source instanceof FWProperty<?, ?> pw) {
            return new FWProperty<>(node, (FWProperty<S, B>) pw);
        }
        return new FOProperty<>(node, (FProperty<S, S, B>) source);
    }

    public static <T extends Segment, D extends DataType<D>> BPNode<T, D, ?> reverse(BFNode<T, D> source) throws BeanException {
        final Class<T> type = source.type;
        if (type.isRecord()) {
            return new BSNode.BRNode<>(source);
        } else {
            return new BSNode.BMNode<>(source);
        }
    }

    @SuppressWarnings("unchecked")
    static <D extends DataType<D>> List<Property<?, ?, D>> reverse(final List<Property<?, ?, D>> source) throws BeanException {
        final List<Property<?, ?, D>> ps = new ArrayList<>(source.size());
        for (final Property<?, ?, D> p : source) {
            final Property<?, ?, D> copy = switch (p) {
                case FProperty<?, ?, ?> fp -> reverse((FProperty<?, ?, D>) fp);
                case POProperty<?, ?> po -> new POProperty<>((POProperty<?, D>) po);
                case PProperty.PWProperty<?, ?> wp -> new PWProperty<>((PWProperty<?, D>) wp);
            };
            ps.add(copy);
        }
        return unmodifiableList(ps);
    }

    static <D extends DataType<D>> List<Property<?, ?, D>> build(Class<? extends Segment> beanType, BSContext<D> context) throws BeanException {
        if (context.push(beanType)) {
            throw new BeanException(beanType, "cyclic fragment is not allowed");
        }
        final List<Property<?, ?, D>> result = new LinkedList<>();
        final Class<?> superClass = beanType.getSuperclass();
        if (Segment.class.isAssignableFrom(superClass)) {
            //noinspection unchecked
            final Class<? extends Segment> superType = (Class<? extends Segment>) superClass;
            final BSContext<D> superContext = context.with(superType, beanType.getGenericSuperclass());
            final List<Property<?, ?, D>> superProperties = build(superType, superContext);
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
                final List<Property<?, ?, D>> fps = build(fType, context.with(field, fragment, fType));
                result.add(context.node(fType, field, fragment, fps));
            } else if (position != null) {
                final Class<?> fieldClass = checkProperty(field, fieldType, position);
                final Property<?, ?, D> property = context.property(fieldClass, field, position);
                result.add(property);
            } else {
                final Class<?> type = getPropertyClass(field, fieldType);
                context.unknown(field, type);
            }
        }
        return result;
    }

    /**
     * Internal composite processor.
     */
    static final class LWrapper<D extends DataType<D>> implements PProcessor<D> {
        private final List<PWrapper<?, D>> processors;

        private LWrapper(List<PWrapper<?, D>> processors) {
            this.processors = unmodifiableList(processors);
        }

        @Override
        public String process(String value, int offset, PProperty<?, ?, D> property, EventHandler<?, D, ?> handler) {
            for (final PWrapper<?, D> processor : processors) {
                value = processor.process(value, offset, property, handler);
            }
            return value;
        }
    }

    /**
     * Internal processor wrapper.
     */
    static final class PWrapper<A extends Annotation, D extends DataType<D>> implements PProcessor<D> {
        private final A config;
        private final InputProcessor<A> delegate;
        private final Class<? extends RuntimeException> skip;

        private PWrapper(InputProcessor<A> delegate, Class<? extends RuntimeException> skip, A config) {
            this.delegate = delegate;
            this.config = config;
            this.skip = skip;
        }

        @Override
        public String process(String value, int offset, PProperty<?, ?, D> pp, EventHandler<?, D, ?> handler) {
            try {
                return delegate.process(value, config);
            } catch (RuntimeException cause) {
                offset += pp.offset;
                if (pp.error != null) {
                    handler.handle(pp.dataType, offset, cause, pp.error);
                } else {
                    final SeverityType level = skip.isInstance(cause) ? WARNING : ERROR;
                    handler.handle(pp.dataType, offset, level, ERROR_PROCESSOR, cause);
                }
                return value;
            }
        }
    }

    /**
     * Internal composite checker.
     */
    static final class WChecker implements CheckerContext {

        private final SegmentListener[] listeners;
        private final CheckerContext[] contexts;

        private WChecker(SegmentListener[] listeners, CheckerContext[] contexts) {
            this.listeners = listeners;
            this.contexts = contexts;
        }

        static WChecker of(Class<? extends Segment> type, BeanContext context) throws BeanException {
            if (type.getTypeParameters().length != 0) {
                throw new BeanException(type, "generic class is not allowed");
            }
            final Checker[] checkers = getAnnotationsByType(Checker.class, type).toArray(Checker[]::new);
            final List<SegmentListener> result = new LinkedList<>();
            result.add(BeanChecker.INSTANCE);
            for (final Checker checker : checkers) {
                final SegmentListener bean = getBean(context, checker.value());
                if (bean.isActivated(type)) {
                    result.add(bean);
                }
            }
            final SegmentListener[] listeners = result.toArray(SegmentListener[]::new);
            final CheckerContext[] contexts = new CheckerContext[listeners.length];
            return new WChecker(listeners, contexts);
        }

        void beforeSegment(Class<? extends Segment> type) throws BeanException {
            for (var i = 0; i < listeners.length; i++) {
                contexts[i] = listeners[i].beforeSegment(type);
            }
        }

        void afterSegment() throws BeanException {
            for (var i = 0; i < this.listeners.length; i++) {
                this.listeners[i].afterSegment(contexts[i]);
            }
        }

        @Override
        public void beforeSuperSegment(Class<? extends Segment> superType) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.beforeSuperSegment(superType);
            }
        }

        @Override
        public void afterSuperSegment(Class<? extends Segment> superType) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.afterSuperSegment(superType);
            }
        }

        @Override
        public void beforeFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.beforeFragmentProperty(fragment, fragmentType);
            }
        }

        @Override
        public void afterFragmentProperty(Field fragment, Class<? extends Segment> fragmentType) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.afterFragmentProperty(fragment, fragmentType);
            }
        }

        @Override
        public void positionProperty(Field property, Class<?> propertyType, int offset) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.positionProperty(property, propertyType, offset);
            }
        }

        @Override
        public void unknownProperty(Field property, Class<?> propertyType) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.unknownProperty(property, propertyType);
            }
        }

    }
}
