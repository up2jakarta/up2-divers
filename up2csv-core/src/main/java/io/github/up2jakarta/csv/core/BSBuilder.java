package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.ReferenceId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.api.Overlink;
import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.core.BSAccessor.Mode;
import io.github.up2jakarta.csv.core.BSLink.RId;
import io.github.up2jakarta.csv.core.BSLink.VId;
import io.github.up2jakarta.csv.core.BSManager.Pod;
import io.github.up2jakarta.csv.core.BSNode.Bean;
import io.github.up2jakarta.csv.core.BSOperator.BId;
import io.github.up2jakarta.csv.core.BSProperty.IProcessor;
import io.github.up2jakarta.csv.core.BSProperty.PFragment;
import io.github.up2jakarta.csv.core.BSProperty.PPosition;
import io.github.up2jakarta.csv.core.BeanAccessor.ICreator;
import io.github.up2jakarta.csv.core.BeanAccessor.IGetter;
import io.github.up2jakarta.csv.core.BeanAccessor.ISetter;
import io.github.up2jakarta.csv.core.MessImporter.Item;
import io.github.up2jakarta.csv.ext.Beans;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.core.BeanAccessor.getInstance;
import static io.github.up2jakarta.csv.ext.Beans.*;
import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static io.github.up2jakarta.lov.core.Localizable.CREATOR;
import static io.github.up2jakarta.lov.core.Overrides.Filter.ALL;
import static io.github.up2jakarta.lov.core.Overrides.get;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.*;

/**
 * Internal Business Builder.
 */
@SuppressWarnings("unchecked")
final class BSBuilder {

    private BSBuilder() {
    }

    private static Class<?> getClass(Field field, Type type, Position csv) throws BeanException {
        BeanChecker.check(field, csv);
        return getPropertyClass(field, type);
    }

    private static Class<? extends Segment> getClass(Field field, Type type, Fragment csv) throws BeanException {
        final Class<?> fType = getPropertyClass(field, type);
        BeanChecker.check(field, fType, csv);
        return (Class<? extends Segment>) fType;
    }

    private static Method getRepeatableValue(Class<? extends Annotation> annotationType) throws BeanException {
        final Repeatable repeatable = annotationType.getDeclaredAnnotation(Repeatable.class);
        if (repeatable != null) {
            return findMethod(repeatable.value(), "value", CLASS, "value()");
        }
        return null;
    }

    private static AnnotatedType unwrapType(Field field) {
        final Class<?> ft = field.getType();
        if (Optional.class == ft || Wrapper.class == ft) {
            if (field.getAnnotatedType() instanceof AnnotatedParameterizedType apt) {
                return apt.getAnnotatedActualTypeArguments()[0];
            }
        }
        return null;
    }

    static boolean getProperty(String key, boolean def) {
        final String value = System.getProperty(key);
        return (value != null) ? Boolean.parseBoolean(value) : def;
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
                    result.addAll(asList(indirectArray));
                } catch (Exception t) {
                    throw new BeanException(repeatValue.getDeclaringClass(), t.getMessage());
                }
            }
            // shortcuts
            final A[] shortcutArray = aType.getAnnotationsByType(type);
            result.addAll(asList(shortcutArray));
        }
        return result;
    }

    static <B extends ITerm<B>> void findRId(BSNode<?, B> n, RIdPath<B> r, PFragment<Segment, B>... fs) throws BeanException {
        for (final BSProperty<?, B> p : n.properties) {
            final ReferenceId config = p.getReferenceId();
            if (p.getClass() == PFragment.class) {
                if (config != null) {
                    throw new BeanException(p.getSource(), "must not be annotated with @ReferenceId");
                }
                final PFragment<Segment, B> fn = (PFragment<Segment, B>) p;
                findRId(fn.node, r, concat(fs, fn));
            } else if (config != null) {
                final PPosition<Object, B> property = (PPosition<Object, B>) p;
                BeanChecker.check(property, ReferenceId.class);
                r.accept(fs, property, config);
            }
        }
    }

    static boolean isAnnotationPresent(Field fp, Class<? extends Annotation>... types) {
        if (stream(types).anyMatch(fp::isAnnotationPresent)) {
            return true;
        }
        final AnnotatedType at = unwrapType(fp);
        return at != null && stream(types).anyMatch(at::isAnnotationPresent);
    }

    static <D extends ITerm<D>> List<BSProperty<?, D>> build(Class<? extends Segment> st, BSContext<D> cx) throws BeanException {
        if (cx.push(st)) {
            throw new BeanException(st, "cyclic fragment is not allowed");
        }
        final List<BSProperty<?, D>> result = new LinkedList<>();
        final Class<?> superClass = st.getSuperclass();
        if (Segment.class.isAssignableFrom(superClass)) {
            final Class<? extends Segment> superType = (Class<? extends Segment>) superClass;
            final BSContext<D> sx = cx.with(superType, st.getGenericSuperclass());
            final List<BSProperty<?, D>> superProperties = build(superType, sx);
            sx.afterSuperSegment();
            result.addAll(superProperties);
        }
        final Field[] fields = st.getDeclaredFields();
        for (final Field field : fields) {
            final Type fieldType = cx.fieldType(field);
            final Fragment fragment = cx.fragment(field);
            final Position position = cx.position(field);
            if (fragment != null) {
                final Class<? extends Segment> fType = getClass(field, fieldType, fragment);
                final BSContext<D> fx = cx.with(field, fragment, fType);
                final List<BSProperty<?, D>> fps = build(fType, fx);
                fx.checkOverrides(ALL);
                if (!(fragment.nullable() && fps.isEmpty())) {
                    try {
                        result.add(cx.node(fType, field, fragment, fps));
                    } catch (AccessException ex) {
                        throw new BeanException(field, ex.getMessage());
                    }
                }
            } else if (position != null) {
                final Class<?> fieldClass = getClass(field, fieldType, position);
                final BSProperty<?, D> property = cx.property(fieldClass, field, position);
                result.add(property);
            } else if (!field.isSynthetic() && !isStatic(field.getModifiers())) {
                final Class<?> type = getPropertyClass(field, fieldType);
                cx.unknown(field, type);
            }
        }
        return result;
    }

    static <D extends ITerm<D>> void path(List<BSProperty<?, D>> ps, List<Field> fs, int index, Consumer<PFragment<?, D>> fc) {
        if (index < fs.size()) {
            final Field current = fs.get(index);
            for (BSProperty<?, D> p : ps) {
                if (p.getClass() == PFragment.class) {
                    final PFragment<?, D> fr = (PFragment<?, D>) p;
                    if (current == fr.getSource()) {
                        fc.accept(fr);
                        path(fr.node.properties, fs, index + 1, fc);
                        break;
                    }
                }
            }
        }
    }

    static Error getError(Class<Segment> type) throws BeanException {
        final Error tc = get(type, Segment.class, Error.class);
        if (tc == null) {
            throw new BeanException(type, "must be annotated with @Error");
        }
        return tc;
    }

    static Error getError(Field field, Class<Segment> type) throws BeanException {
        final Error fc = field.getAnnotation(Error.class);
        if (fc == null) {
            final Error tc = get(type, Segment.class, Error.class);
            if (tc == null) {
                throw new BeanException(field, "must be annotated with @Error");
            }
            return tc;
        }
        return fc;
    }

    static String name(BusinessLink bl) {
        return "@BusinessLink(\"" + bl.value() + "\")";
    }

    static String name(Overlink bl) {
        return "@Overlink(@BusinessLink(\"" + bl.value() + "\"))";
    }

    static String name(ReferenceId id) {
        return "@ReferenceId(\"" + id.value() + "\")";
    }

    static Linker getLinker(BusinessLink override, BusinessLink source) {
        if (override != null && override.bean().value() != BusinessLink.Void.class) {
            return override.bean();
        }
        return source.bean();
    }

    static Linker getLinker(Overlink override, BusinessLink source) {
        if (override != null) {
            return getLinker(override.value(), source);
        }
        return source.bean();
    }

    static Class<? extends Segment> getTarget(Overlink override, BusinessLink source, Linker linker) {
        if (override != null && (override.value().bean() == linker || override.value().target() != Segment.class)) {
            return override.value().target();
        }
        return source.target();
    }

    static Class<Segment> linkerType(Class<? extends Segment> pc, Field fp, int max, BusinessLink link) throws BeanException {
        final Type type = Beans.unwrapType(fp, NO_TYPES);
        final Class<?> ut = getPropertyClass(fp, type);
        final Class<?> fc = fp.getDeclaringClass();
        if (max == 1 && fc.isAssignableFrom(pc) && Segment.class.isAssignableFrom(ut)) {
            return cast(ut);
        }
        throw new BeanException(fp, name(link) + " must provide @Linker");
    }

    @FunctionalInterface
    interface RIdPath<B extends ITerm<B>> {
        void accept(PFragment<Segment, B>[] ps, PPosition<Object, B> pp, ReferenceId rid) throws BeanException;
    }

    /**
     * Class Marker for Stack Trace
     */
    sealed interface MST permits BSAccessor, IProcessor, BSNode, BSProperty {
    }

    /**
     * Class Marker for Entry Point
     */
    sealed interface MEP permits BSLink, RId, VId, Pod, BId, IGetter, ISetter, Item, Up2Adapter {
    }

    /**
     * Internal Bean Creator
     */
    abstract static class BCR<S extends Segment, D extends ITerm<D>> {
        private final List<BSProperty<?, D>> ps;
        private final Class<S> type;

        BCR(Class<S> type, List<BSProperty<?, D>> ps) {
            this.ps = ps;
            this.type = type;
        }

        private static <T extends Segment> ICreator<T> jrCreator(Class<T> type) throws BeanException {
            try {
                final Class<?>[] ts = stream(type.getDeclaredFields()).map(Field::getType).toArray(Class<?>[]::new);
                return getInstance().toCreator(type.getDeclaredConstructor(ts));
            } catch (Exception cause) {
                throw new BeanException(type, CREATOR, cause.getMessage());
            }
        }

        static <T extends Segment> ICreator<T> anyCreator(Class<T> type) throws BeanException {
            //noinspection unchecked
            final Constructor<T>[] dcs = (Constructor<T>[]) type.getDeclaredConstructors();
            if (dcs.length > 1) {
                sort(dcs, Comparator.comparingInt(Constructor::getParameterCount));
            }
            return getInstance().toCreator(dcs[0]);
        }

        abstract Bean.CN<S, D> cn(ICreator<S> cs) throws BeanException;

        abstract Bean.JB<S, D> jb(ICreator<S> cs) throws BeanException;

        abstract Bean.JR<S, D> jr(ICreator<S> cs) throws BeanException;

        final Bean<S, D, ?> build() throws BeanException {
            final long fc = ps.stream().filter(BSProperty::isFinal).count();
            if (fc == 0) {
                return this.cn(anyCreator(type));
            } else if (fc == ps.size()) {
                //noinspection unchecked
                final Constructor<S>[] dcs = (Constructor<S>[]) type.getDeclaredConstructors();
                final List<Constructor<S>> cs = stream(dcs).filter(c -> c.isAnnotationPresent(Creator.class)).toList();
                if (cs.size() == 1) {
                    return this.jb(getInstance().toCreator(cs.getFirst()));
                } else if (type.isRecord()) {
                    return this.jr(jrCreator(type));
                } else if (dcs.length == 1) {
                    return this.jb(getInstance().toCreator(dcs[0]));
                }
                throw new BeanException(type, "one and only one constructor must be annotated with @Creator");
            } else if (BeanAccessor.isTrusted()) {
                return this.cn(anyCreator(type));
            }
            throw new BeanException(type, "mix final and writable properties is not allowed");
        }
    }

    /**
     * Internal Composite Checker
     */
    static final class Listener implements TypeContext, TypeListener {
        private static final Listener RO = new Listener(Mode.RO);
        private static final Listener WO = new Listener(Mode.WO);

        private final TypeListener[] listeners;
        private final TypeContext[] contexts;

        private Listener(Mode mode) {
            this.listeners = new TypeListener[]{BeanChecker.of(mode)};
            this.contexts = new TypeContext[1];
        }

        private Listener(TypeListener[] listeners, TypeContext[] contexts) {
            this.listeners = listeners;
            this.contexts = contexts;
        }

        static Listener of(Class<? extends Segment> type, Container context, Mode mode, boolean ea) throws BeanException {
            BeanChecker.check(type);
            if (!ea) {
                return (mode == Mode.RO) ? RO : WO;
            }
            final Checker[] checkers = getAnnotationsByType(Checker.class, type).toArray(Checker[]::new);
            final List<TypeListener> result = new LinkedList<>();
            result.add(BeanChecker.of(mode));
            for (final Checker checker : checkers) {
                final TypeListener bean = Container.from(context, checker.value(), checker.name());
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
