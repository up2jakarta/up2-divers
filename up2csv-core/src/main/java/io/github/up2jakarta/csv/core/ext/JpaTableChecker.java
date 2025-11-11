package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.api.ext.CheckerContext;
import io.github.up2jakarta.csv.api.ext.SegmentListener;
import io.github.up2jakarta.csv.core.AccessMode;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.Segment;
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

    static Class<?>[] checkAndGetArguments(Field field, Class<?> type, int length) throws BeanException {
        final TypeVariable<?>[] parameters = type.getTypeParameters();
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
            if (argument instanceof Class<?> c) {
                result.add(c);
            } else if (parameter.getBounds()[0] instanceof ParameterizedType pt && pt.getActualTypeArguments().length == 1) {
                result.add((Class<?>) pt.getActualTypeArguments()[0]);
            } else if ((argument instanceof ParameterizedType pt) && pt.getRawType() instanceof Class<?> rc) {
                result.add(rc);
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

    static Class<?> checkEntity(Class<?> type) throws BeanException {
        final Table table = type.getAnnotation(Table.class);
        if (table == null && type.isAnnotationPresent(DiscriminatorValue.class)) {
            return checkEntity(type.getSuperclass());
        }
        if (table == null) {
            throw new BeanException(type, "must be annotated with @Table");
        }
        final String prefix = checkAndGetPrefix(type.getPackage());
        checkName(type, table.name(), prefix, "@Table[name]");
        return type;
    }

    @Override
    public boolean isActivated(Class<? extends Segment> type) {
        return type.isAnnotationPresent(Entity.class) && type.getPackage().isAnnotationPresent(Prefix.class);
    }

    @Override
    public CheckerContext beforeSegment(AccessMode mode, Class<? extends Segment> type) throws BeanException {
        checkEntity(type);
        return new JpaTableContext(type);
    }

    private static class JpaTableContext implements CheckerContext {

        private final Stack<Class<?>> stack = new Stack<>();

        private JpaTableContext(Class<? extends Segment> type) {
            stack.push(type);
        }

        private static Class<?> checkFragment(Field fragment, Class<? extends Segment> type) throws BeanException {
            final OneToOne o2o = fragment.getAnnotation(OneToOne.class);
            if (o2o != null) {
                final Class<?> entityType = (o2o.targetEntity() != void.class) ? o2o.targetEntity() : type;
                if (entityType.getAnnotation(Entity.class) == null) {
                    throw new BeanException(type, "must be annotated by @Entity");
                }
                return checkEntity(entityType);
            }
            final ManyToOne m2o = fragment.getAnnotation(ManyToOne.class);
            if (m2o != null) {
                final Class<?> entityType = (m2o.targetEntity() != void.class) ? m2o.targetEntity() : type;
                if (entityType.getAnnotation(Entity.class) == null) {
                    throw new BeanException(type, "must be annotated by @Entity");
                }
                return checkEntity(entityType);
            }
            if (type.getAnnotation(Entity.class) != null) {
                return checkEntity(type);
            }
            return type;
        }

        @Override
        public void beforeFragmentProperty(AccessMode mode, Field fragment, Class<? extends Segment> type) throws BeanException {
            stack.push(checkFragment(fragment, type));
            this.unknownProperty(fragment, type);
        }

        @Override
        public void afterFragmentProperty(Field fragment, Class<? extends Segment> type) {
            stack.pop();
        }

        @Override
        public void unknownProperty(Field property, Class<?> type) throws BeanException {
            final SecondaryTable[] st = property.getAnnotationsByType(SecondaryTable.class);
            for (final SecondaryTable t : st) {
                final String prefix = checkAndGetPrefix(stack.peek().getPackage());
                checkName(property.getDeclaringClass(), t.name(), prefix, "@SecondaryTable[name]");
            }
            final JoinTable jt = property.getAnnotation(JoinTable.class);
            if (jt != null) {
                final String prefix = checkAndGetPrefix(stack.peek().getPackage());
                checkName(property.getDeclaringClass(), jt.name(), prefix, "@JoinTable[name]");
            }
            final OneToOne o2o = property.getAnnotation(OneToOne.class);
            if (o2o != null) {
                final Class<?> entityType = (o2o.targetEntity() != void.class) ? o2o.targetEntity() : type;
                if (entityType.getAnnotation(Entity.class) == null) {
                    throw new BeanException(type, "must be annotated by @Entity");
                }
                checkEntity(entityType);
            }
            final ManyToOne m2o = property.getAnnotation(ManyToOne.class);
            if (m2o != null) {
                final Class<?> entityType = (m2o.targetEntity() != void.class) ? m2o.targetEntity() : type;
                if (entityType.getAnnotation(Entity.class) == null) {
                    throw new BeanException(type, "must be annotated by @Entity");
                }
                checkEntity(entityType);
            }
            final OneToMany o2m = property.getAnnotation(OneToMany.class);
            final ManyToMany m2m = property.getAnnotation(ManyToMany.class);
            if (o2m != null || m2m != null) {
                if (Collection.class.isAssignableFrom(type)) {
                    final Class<?>[] types = checkAndGetArguments(property, type, 1);
                    if (types[0].getAnnotation(Entity.class) == null) {
                        throw new BeanException(property, "type must be annotated by @Entity");
                    }
                    checkEntity(types[0]);
                } else if (Map.class.isAssignableFrom(type)) {
                    final Class<?>[] types = checkAndGetArguments(property, type, 2);
                    if (types[0].getAnnotation(Entity.class) != null) {
                        checkEntity(types[0]);
                    }
                    if (types[1].getAnnotation(Entity.class) == null) {
                        throw new BeanException(property, "type must be annotated by @Entity");
                    }
                    checkEntity(types[1]);
                } else {
                    throw new BeanException(property, "must be collection");
                }
            }
        }
    }

}
