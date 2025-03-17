package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.CheckerContext;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.extension.SegmentListener;
import io.github.up2jakarta.csv.misc.Prefix;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * JPA {@link jakarta.persistence.Table} checker implementation.
 */
@Named
@Singleton
public final class JpaTableChecker implements SegmentListener {

    private static void throwBeanException(AnnotatedElement source, String message) throws BeanException {
        if (source instanceof Package p) {
            throw new BeanException(p, message);
        }
        if (source instanceof Field f) {
            throw new BeanException(f, message);
        }
        throw new BeanException((Class<?>) source, message);
    }

    static Class<?>[] checkAndGetArguments(Field field, Class<?> fieldType, int length) throws BeanException {
        final TypeVariable<?>[] parameters = fieldType.getTypeParameters();
        final Type[] arguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        if (parameters.length != length) {
            throw new BeanException(field, "must be checked");
        }
        final List<Class<?>> result = new ArrayList<>(length);
        for (var i = 0; i < parameters.length; i++) {
            final TypeVariable<?> parameter = parameters[i];
            final Type argument = arguments[i];
            if (parameter.getBounds().length == 0) {
                throw new BeanException(field, "must be checked");
            }
            if (argument instanceof Class<?> type) {
                result.add(type);
            } else if (parameter.getBounds()[0] instanceof ParameterizedType pt && pt.getActualTypeArguments().length == 1) {
                result.add((Class<?>) pt.getActualTypeArguments()[0]);
            } else {
                throw new BeanException(field, "must be checked");
            }
        }
        return result.toArray(Class[]::new);
    }

    static String checkAndGetPrefix(AnnotatedElement source) throws BeanException {
        final Prefix config = source.getAnnotation(Prefix.class);
        if (config != null && !config.value().isBlank()) {
            final String prefix = config.value();
            if (!prefix.equals(prefix.toUpperCase())) {
                throwBeanException(source, "@Prefix[value] must be uppercase");
            }
            if (!prefix.endsWith("_")) {
                throwBeanException(source, "@Prefix[value] must ends with underscore (_)");
            }
            checkName(source, prefix, "", "@Prefix[value]");
            return prefix;
        }
        return "";
    }

    static void checkName(AnnotatedElement source, String name, String prefix, String desc) throws BeanException {
        if (name.isBlank()) {
            throwBeanException(source, desc + " must not be empty");
        }
        if (!name.equals(name.toUpperCase())) {
            throwBeanException(source, desc + " must be uppercase");
        }
        if (!Character.isAlphabetic(name.charAt(0))) {
            throwBeanException(source, desc + " must starts with alphabetic");
        }
        if (!name.matches("^[A-Z0-9_]+$")) {
            throwBeanException(source, desc + " must contains only alphanumeric or underscore");
        }
        if (!name.startsWith(prefix)) {
            throwBeanException(source, desc + " must starts with \"" + prefix + "\"");
        }
    }

    static Class<?> checkEntity(Class<?> entityType) throws BeanException {
        final Table table = entityType.getAnnotation(Table.class);
        if (table == null && entityType.isAnnotationPresent(DiscriminatorValue.class)) {
            return checkEntity(entityType.getSuperclass());
        }
        if (table == null) {
            throw new BeanException(entityType, "must be annotated with @Table");
        }
        final String prefix = checkAndGetPrefix(entityType.getPackage());
        checkName(entityType, table.name(), prefix, "@Table[name]");
        return entityType;
    }

    @Override
    public boolean isActivated(Class<? extends Segment> segmentType) {
        return segmentType.isAnnotationPresent(Entity.class) && segmentType.getPackage().isAnnotationPresent(Prefix.class);
    }

    @Override
    public CheckerContext beforeSegment(Class<? extends Segment> segmentType) throws BeanException {
        checkEntity(segmentType);
        return new JpaTableContext(segmentType);
    }

    private static class JpaTableContext implements CheckerContext {

        private final Stack<Class<?>> stack = new Stack<>();

        private JpaTableContext(Class<? extends Segment> segmentType) {
            stack.push(segmentType);
        }

        private static Class<?> checkFragment(Field field, Class<? extends Segment> fieldType) throws BeanException {
            final OneToOne o2o = field.getAnnotation(OneToOne.class);
            if (o2o != null) {
                final Class<?> entityType = (o2o.targetEntity() != void.class) ? o2o.targetEntity() : fieldType;
                if (entityType.getAnnotation(Entity.class) == null) {
                    throw new BeanException(fieldType, "must be annotated by @Entity");
                }
                return checkEntity(entityType);
            }
            final ManyToOne m2o = field.getAnnotation(ManyToOne.class);
            if (m2o != null) {
                final Class<?> entityType = (m2o.targetEntity() != void.class) ? m2o.targetEntity() : fieldType;
                if (entityType.getAnnotation(Entity.class) == null) {
                    throw new BeanException(fieldType, "must be annotated by @Entity");
                }
                return checkEntity(entityType);
            }
            if (fieldType.getAnnotation(Entity.class) != null) {
                return checkEntity(fieldType);
            }
            return fieldType;
        }

        @Override
        public void beforeFragmentProperty(Field field, Class<? extends Segment> fieldType) throws BeanException {
            stack.push(checkFragment(field, fieldType));
            this.unknownProperty(field, fieldType);
        }

        @Override
        public void afterFragmentProperty(Field field, Class<? extends Segment> fieldType) {
            stack.pop();
        }

        @Override
        public void unknownProperty(Field field, Class<?> fieldType) throws BeanException {
            final SecondaryTable[] st = field.getAnnotationsByType(SecondaryTable.class);
            for (final SecondaryTable t : st) {
                final String prefix = checkAndGetPrefix(stack.peek().getPackage());
                checkName(field.getDeclaringClass(), t.name(), prefix, "@SecondaryTable[name]");
            }
            final JoinTable jt = field.getAnnotation(JoinTable.class);
            if (jt != null) {
                final String prefix = checkAndGetPrefix(stack.peek().getPackage());
                checkName(field.getDeclaringClass(), jt.name(), prefix, "@JoinTable[name]");
            }
            final OneToOne o2o = field.getAnnotation(OneToOne.class);
            if (o2o != null) {
                final Class<?> entityType = (o2o.targetEntity() != void.class) ? o2o.targetEntity() : fieldType;
                if (entityType.getAnnotation(Entity.class) == null) {
                    throw new BeanException(fieldType, "must be annotated by @Entity");
                }
                checkEntity(entityType);
            }
            final ManyToOne m2o = field.getAnnotation(ManyToOne.class);
            if (m2o != null) {
                final Class<?> entityType = (m2o.targetEntity() != void.class) ? m2o.targetEntity() : fieldType;
                if (entityType.getAnnotation(Entity.class) == null) {
                    throw new BeanException(fieldType, "must be annotated by @Entity");
                }
                checkEntity(entityType);
            }
            final OneToMany o2m = field.getAnnotation(OneToMany.class);
            final ManyToMany m2m = field.getAnnotation(ManyToMany.class);
            if (o2m != null || m2m != null) {
                if (Collection.class.isAssignableFrom(fieldType)) {
                    final Class<?>[] types = checkAndGetArguments(field, fieldType, 1);
                    if (types[0].getAnnotation(Entity.class) == null) {
                        throw new BeanException(field, "type must be annotated by @Entity");
                    }
                    checkEntity(types[0]);
                } else if (Map.class.isAssignableFrom(fieldType)) {
                    final Class<?>[] types = checkAndGetArguments(field, fieldType, 2);
                    if (types[0].getAnnotation(Entity.class) != null) {
                        checkEntity(types[0]);
                    }
                    if (types[1].getAnnotation(Entity.class) == null) {
                        throw new BeanException(field, "type must be annotated by @Entity");
                    }
                    checkEntity(types[1]);
                } else {
                    throw new BeanException(field, "must be collection");
                }
            }
        }
    }

}
