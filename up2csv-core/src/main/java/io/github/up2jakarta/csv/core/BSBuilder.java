package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.api.ext.CheckerContext;
import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.api.ext.SegmentListener;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.BSNode.BFNode.BSWalker;
import io.github.up2jakarta.csv.core.BSNode.BFNode.BSWalker.ACWalker;
import io.github.up2jakarta.csv.core.BSNode.BFNode.BSWalker.BCWalker;
import io.github.up2jakarta.csv.core.BSNode.BPNode.BMNode;
import io.github.up2jakarta.csv.core.BSNode.BPNode.BRNode;
import io.github.up2jakarta.csv.core.BSOperator.Computer.BSFormat;
import io.github.up2jakarta.csv.core.BSOperator.Computer.BSMapper;
import io.github.up2jakarta.csv.core.BSProperty.FProperty;
import io.github.up2jakarta.csv.core.BSProperty.FProperty.FOProperty;
import io.github.up2jakarta.csv.core.BSProperty.FProperty.FWProperty;
import io.github.up2jakarta.csv.core.BSProperty.PProcessor;
import io.github.up2jakarta.csv.core.BSProperty.PProperty;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.core.hdl.*;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_PROCESSOR;
import static io.github.up2jakarta.csv.core.AccessMode.WO;
import static io.github.up2jakarta.csv.core.BSNode.BFNode;
import static io.github.up2jakarta.csv.core.BSNode.BPNode;
import static io.github.up2jakarta.csv.core.BSOperator.BAccessor;
import static io.github.up2jakarta.csv.core.BSOperator.BAccessor.*;
import static io.github.up2jakarta.csv.core.BSOperator.BAccessor.BPAccessor.PMProperty;
import static io.github.up2jakarta.csv.core.BSOperator.BAccessor.BPAccessor.PSProperty;
import static io.github.up2jakarta.csv.core.BSOperator.Computer;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor.PFAccessor.FRAccessor;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor.PFAccessor.FWAccessor;
import static io.github.up2jakarta.csv.core.BSProperty.PAccessor.PPAccessor.*;
import static io.github.up2jakarta.csv.core.BSProperty.PProperty.POProperty;
import static io.github.up2jakarta.csv.core.BSProperty.PProperty.PWProperty;
import static io.github.up2jakarta.csv.core.BusinessImporter.Entry;
import static io.github.up2jakarta.csv.core.ext.Beans.*;
import static io.github.up2jakarta.csv.prc.DefaultProcessor.undefined;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static io.github.up2jakarta.xml.api.SeverityType.WARNING;
import static java.util.Collections.unmodifiableList;

@SuppressWarnings("unchecked")
final class BSBuilder {

    private static final List<String> EXCLUSIONS = Arrays.stream(excludes()).map(Beans::getClassName).toList();
    private static final List<String> CN_ENTRIES = List.of(Up2Mapper.class.getName(), BSMapper.class.getName());

    private BSBuilder() {
    }

    private static Class<?>[] excludes() {
        return new Class<?>[]{
                BAccessor.class, BOAccessor.class, BUAccessor.class, BRAccessor.class,
                BPAccessor.class, PSProperty.class, PMProperty.class,
                PAccessor.class, FRAccessor.class, FWAccessor.class,
                PRAccessor.class, PWAccessor.class, PNAccessor.class,
                EventHandler.class, FastHandler.class, BusinessHandler.class, ComplianceHandler.class,
                BusinessCollector.class, PropertyFailureCollector.class, PropertyCollector.class,
                SelfPropertyCollector.class, SelfBusinessCollector.class,
                BSProperty.class, PProperty.class, POProperty.class, PWProperty.class,
                FProperty.class, FOProperty.class, FWProperty.class,
                PProcessor.class, PWrapper.class, LWrapper.class,
                BSOperator.Processor.class, Up2Mapper.class, Up2Format.class,
                Computer.class, BSFormat.class, BSMapper.class,
                BSBuilder.class, BSOperator.class, Entry.class,
                BSNode.class, BPNode.class, BFNode.class, BRNode.class, BMNode.class,
                BSWalker.class, ACWalker.class, BCWalker.class,
        };
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

    static void stackTrace(Throwable error, PrintWriter printer) {
        printer.println(error);
        final StackTraceElement[] traces = error.getStackTrace();
        for (final StackTraceElement element : traces) {
            final String cn = element.getClassName();
            if (CN_ENTRIES.contains(cn)) {
                break;
            } else if (!EXCLUSIONS.contains(cn)) {
                printer.println("\t" + element);
            }
        }
        final Throwable cause = error.getCause();
        if (cause != null) {
            printer.println(cause);
        }
    }

    static <D extends DataType<D>> PWrapper<?, D> build(BeanContext ctx, Annotation ppa) throws BeanException {
        final Class<? extends Annotation> type = ppa.annotationType();
        final Processor prc = type.getAnnotation(Processor.class);
        final Class<? extends InputProcessor<?>> pType = prc.value();
        final Type[] types = Beans.getTypeArguments(pType, InputProcessor.class);
        if (types.length == 0 || type != types[0]) {
            final CharSequence cn = getTypeName(type);
            throw new BeanException(type, "@Processor[value] must implements InputProcessor<" + cn + ">");
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
            final A[] shortcutArray = aType.getAnnotationsByType(annotationType);
            result.addAll(Arrays.asList(shortcutArray));
        }
        return result;
    }

    static <A extends Annotation> void id(AccessMode mode, BSNode<?, ?> pn, Class<A> ct, BIdPath bc, FProperty<Segment, ?, ?>... pp) throws BeanException {
        for (final BSProperty<?, ?, ?> p : pn.properties) {
            final AnnotatedElement field = p.getSource();
            final A annotation = field.getAnnotation(ct);
            if (p instanceof FProperty<?, ?, ?> fp) {
                if (annotation != null) {
                    throw BeanException.of(field, "must not be annotated with @" + getTypeName(ct));
                }
                id(mode, fp.node, ct, bc, concat(pp, (FProperty<Segment, ?, ?>) fp));
            } else if (annotation != null) {
                if (mode == WO) {
                    final List<FProperty<Segment, ?, ?>> path = new ArrayList<>(pp.length);
                    for (final FProperty<Segment, ?, ?> fp : pp) {
                        path.add(fp.reverse());
                    }
                    final BSProperty<Object, Object, ?> pr = ((BSProperty<Object, Object, ?>) p).reverse();
                    bc.accept(unmodifiableList(path), (PProperty<Object, Object, ?>) pr);
                } else {
                    bc.accept(Arrays.asList(pp), (PProperty<Object, Object, ?>) p);
                }
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
        void accept(List<FProperty<Segment, ?, ?>> path, PProperty<Object, Object, ?> property) throws BeanException;
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
        public String process(String value, int offset, PProperty<?, ?, D> property, EventHandler<D> handler) {
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
        public String process(String value, int offset, PProperty<?, ?, D> pp, EventHandler<D> handler) {
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
    static final class WChecker implements CheckerContext, SegmentListener {

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

        @Override
        public WChecker beforeSegment(AccessMode mode, Class<? extends Segment> type) throws BeanException {
            for (var i = 0; i < listeners.length; i++) {
                contexts[i] = listeners[i].beforeSegment(mode, type);
            }
            return this;
        }

        @Override
        public void afterSegment(CheckerContext context) throws BeanException {
            for (var i = 0; i < this.listeners.length; i++) {
                this.listeners[i].afterSegment(contexts[i]);
            }
            context.close();
        }

        @Override
        public void beforeSuperSegment(Class<? extends Segment> type) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.beforeSuperSegment(type);
            }
        }

        @Override
        public void afterSuperSegment(Class<? extends Segment> type) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.afterSuperSegment(type);
            }
        }

        @Override
        public void beforeFragmentProperty(AccessMode mode, Field fragment, Class<? extends Segment> type) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.beforeFragmentProperty(mode, fragment, type);
            }
        }

        @Override
        public void afterFragmentProperty(Field fragment, Class<? extends Segment> type) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.afterFragmentProperty(fragment, type);
            }
        }

        @Override
        public void positionProperty(Field property, Class<?> type, int offset) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.positionProperty(property, type, offset);
            }
        }

        @Override
        public void unknownProperty(Field property, Class<?> type) throws BeanException {
            for (final CheckerContext context : contexts) {
                context.unknownProperty(property, type);
            }
        }

    }
}
