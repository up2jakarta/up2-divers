package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.api.ext.PropertyConverter;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.csv.core.PProperty.WProcessor;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;

import static io.github.up2jakarta.csv.core.ext.Beans.concat;
import static io.github.up2jakarta.csv.core.ext.Beans.getBean;
import static java.util.Collections.unmodifiableList;

final class BeanScanner<D extends DataType<D>> {

    private final BSContext<D> context;

    BeanScanner(BSContext<D> context) {
        this.context = context;
    }

    private static Fragment checkFragment(Field field, Fragment csv) throws BeanException {
        if (csv != null) {
            if (csv.value() < 0) {
                throw new BeanException(field, "@Fragment[value] must be positive");
            }
            if (!Segment.class.isAssignableFrom(field.getType())) {
                throw new BeanException(field, "type must implements Segment");
            }
        }
        return csv;
    }

    private static Position checkProperty(Field field, Position csv) throws BeanException {
        if (csv != null) {
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
        }
        return csv;
    }

    private static Method getRepeatableValue(Class<? extends Annotation> annotationType) throws BeanException {
        final Repeatable repeatable = annotationType.getDeclaredAnnotation(Repeatable.class);
        if (repeatable != null) {
            return Beans.getMethod(repeatable.value(), "value", "class", "value()");
        }
        return null;
    }

    private static <A extends Annotation> void walkPath(
            List<? extends Property<?, ?>> properties, Class<A> annotationType,
            BiConsumer<A, Property<?, ?>[]> collector, Property<?, ?>... stack
    ) throws BeanException {
        for (final Property<?, ?> property : properties) {
            final Field field = property.getField();
            final A annotation = field.getAnnotation(annotationType);
            if (property instanceof PFProperty<?, ?> fp) {
                if (annotation != null) {
                    throw new BeanException(field, "must not be annotated with @" + annotationType.getSimpleName());
                }
                walkPath(fp.toList(), annotationType, collector, concat(stack, property));
            } else if (annotation != null) {
                collector.accept(annotation, concat(stack, property));
            }
        }
    }

    static <A extends Annotation> Property<?, ?>[] uniquePath(
            Class<? extends Segment> sType, Class<A> aType, List<? extends Property<?, ?>> properties
    ) throws BeanException {
        final Map<A, Property<?, ?>[]> found = new LinkedHashMap<>();
        walkPath(properties, aType, found::put);
        if (found.isEmpty()) {
            return null;
        }
        if (found.size() == 1) {
            return found.entrySet().iterator().next().getValue();
        }
        throw new BeanException(sType, "multiple @" + aType.getSimpleName() + " are found");
    }

    static <T> T getDefault(Field field, PropertyConverter<T> conversion) throws BeanException {
        final Up2Default defaultValue = field.getAnnotation(Up2Default.class);
        if (defaultValue != null) {
            try {
                return conversion.apply(defaultValue.value());
            } catch (Exception ex) {
                throw new BeanException(field, "@Up2Default[value] cannot be converted");
            }
        }
        return null;
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

    static <D extends DataType<D>> List<WProcessor<?, D>> getProcessors(io.github.up2jakarta.csv.api.ext.BeanContext context, Field field) throws BeanException {
        final List<WProcessor<?, D>> result = new LinkedList<>();
        for (final Annotation annotation : field.getAnnotations()) {
            final Class<? extends Annotation> aType = annotation.annotationType();
            final Processor processor = aType.getAnnotation(Processor.class);
            if (processor != null) {
                final Class<? extends InputProcessor<?>> pType = processor.value();
                final Type[] types = Beans.getTypeArguments(pType, InputProcessor.class);
                if (types.length == 0 || aType != types[0]) {
                    final String aName = aType.getSimpleName();
                    throw new BeanException(aType, "@Processor[value] must implements InputProcessor<" + aName + ">");
                }
                final InputProcessor<? extends Annotation> delegate = getBean(context, pType);
                //noinspection rawtypes,unchecked
                result.add(new WProcessor(delegate, processor.skip(), annotation));
            }
        }
        return unmodifiableList(result);
    }

    @SuppressWarnings("unchecked")
    static <D extends DataType<D>> List<Property<?, D>> reverse(final List<Property<?, D>> source) throws BeanException {
        final List<Property<?, D>> ps = new ArrayList<>(source.size());
        for (final Property<?, D> p : source) {
            if (p instanceof PFProperty<?, ?> fp) {
                ps.add(new PFProperty<>((PFProperty<?, D>) fp));
            } else if (p instanceof PProperty.PSProperty<?> sp) {
                ps.add(new PProperty.PSProperty<>((PProperty.PSProperty<D>) sp));
            } else {
                ps.add(new PProperty.POProperty<>((PProperty.POProperty<?, D>) p));
            }
        }
        return Collections.unmodifiableList(ps);
    }

    private List<Property<?, D>> build(Class<? extends Segment> beanType, BSContext<D> context) throws BeanException {
        if (context.push(beanType)) {
            throw new BeanException(beanType, "cyclic fragment is not allowed");
        }
        final List<Property<?, D>> result = new LinkedList<>();
        final Class<?> superClass = beanType.getSuperclass();
        if (Segment.class.isAssignableFrom(superClass)) {
            final Type[] arguments = Beans.getTypeArguments(beanType.getGenericSuperclass());
            //noinspection unchecked
            final Class<? extends Segment> superType = (Class<? extends Segment>) superClass;
            context.checker.beforeSuperSegment(superType);
            final List<Property<?, D>> superProperties = build(superType, context.with(superType, arguments));
            context.checker.afterSuperSegment(superType);
            result.addAll(superProperties);
        }
        final Field[] fields = beanType.getDeclaredFields();
        final int offset = context.offset;
        for (final Field field : fields) {
            final Class<?> fieldType = context.fieldType(field);
            final Fragment fragment = checkFragment(field, context.fragment(field));
            final Position position = checkProperty(field, context.position(field));
            if (fragment != null) {
                //noinspection unchecked
                final Class<? extends Segment> fType = (Class<? extends Segment>) fieldType;
                final int index = offset + fragment.value();
                context.checker.beforeFragmentProperty(field, fType);
                final List<Property<?, D>> fProps = build(fType, context.with(field, index, fType));
                context.checker.afterFragmentProperty(field, fType);
                result.add(context.node(fType, field, index, fragment.nullable(), fProps));
            } else if (position != null) {
                final int index = offset + position.value();
                context.checker.beforePositionProperty(field, fieldType, index);
                result.add(context.leaf(fieldType, field, index, position.required()));
                context.checker.afterPositionProperty(field, fieldType, index);
            } else {
                context.checker.unknownProperty(field, fieldType);
            }
        }
        return result;
    }

    List<Property<?, D>> build(Class<? extends Segment> beanType) throws BeanException {
        final List<Property<?, D>> result = build(beanType, context);
        return unmodifiableList(result);
    }

}
