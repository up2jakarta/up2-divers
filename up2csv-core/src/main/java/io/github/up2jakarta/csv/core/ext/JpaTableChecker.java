package io.github.up2jakarta.csv.core.ext;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ext.TypeContext;
import io.github.up2jakarta.csv.api.ext.TypeListener;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

import static io.github.up2jakarta.csv.core.ext.JpaColumnChecker.checkName;
import static io.github.up2jakarta.csv.core.ext.JpaColumnChecker.getPrefix;
import static io.github.up2jakarta.lov.core.Overrides.get;

/**
 * JPA {@link jakarta.persistence.Table} checker implementation.
 */
@Named
@Singleton
public final class JpaTableChecker implements TypeListener {

    private static Class<?>[] getArguments(Field field, Class<?> type, int length) throws BeanException {
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

    private static Class<?> checkEntity(Class<?> type) throws BeanException {
        final Table table = type.getAnnotation(Table.class);
        if (table == null && type.isAnnotationPresent(DiscriminatorValue.class)) {
            return checkEntity(type.getSuperclass());
        }
        if (table == null) {
            throw new BeanException(type, "must be annotated with @Table");
        }
        final String prefix = getPrefix(type.getPackage());
        checkName(type, table.name(), prefix, "@Table[name]");
        return type;
    }

    private static Class<?> checkFragment(Field fragment, Class<? extends Segment> type) throws BeanException {
        final OneToOne o2o = fragment.getAnnotation(OneToOne.class);
        if (o2o != null) {
            final Class<?> entityType = (o2o.targetEntity() != void.class) ? o2o.targetEntity() : type;
            if (entityType.getAnnotation(Entity.class) == null) {
                throw new BeanException(type, "must be annotated with @Entity");
            }
            return checkEntity(entityType);
        }
        final ManyToOne m2o = fragment.getAnnotation(ManyToOne.class);
        if (m2o != null) {
            final Class<?> entityType = (m2o.targetEntity() != void.class) ? m2o.targetEntity() : type;
            if (entityType.getAnnotation(Entity.class) == null) {
                throw new BeanException(type, "must be annotated with @Entity");
            }
            return checkEntity(entityType);
        }
        if (type.getAnnotation(Entity.class) != null) {
            return checkEntity(type);
        }
        return type;
    }

    private static void checkToMany(Field property, Class<?> type) throws BeanException {
        final OneToMany o2m = property.getAnnotation(OneToMany.class);
        final ManyToMany m2m = property.getAnnotation(ManyToMany.class);
        if (o2m != null || m2m != null) {
            if (Collection.class.isAssignableFrom(type)) {
                final Class<?>[] types = getArguments(property, type, 1);
                if (types[0].getAnnotation(Entity.class) == null) {
                    throw new BeanException(property, "type must be annotated with @Entity");
                }
                checkEntity(types[0]);
            } else if (Map.class.isAssignableFrom(type)) {
                final Class<?>[] types = getArguments(property, type, 2);
                if (types[0].getAnnotation(Entity.class) != null) {
                    checkEntity(types[0]);
                }
                if (types[1].getAnnotation(Entity.class) == null) {
                    throw new BeanException(property, "type must be annotated with @Entity");
                }
                checkEntity(types[1]);
            } else {
                throw new BeanException(property, "must be collection");
            }
        }
    }

    private static void checkToOne(Field property, Class<?> type) throws BeanException {
        final OneToOne o2o = property.getAnnotation(OneToOne.class);
        if (o2o != null) {
            final Class<?> entityType = (o2o.targetEntity() != void.class) ? o2o.targetEntity() : type;
            if (entityType.getAnnotation(Entity.class) == null) {
                throw new BeanException(type, "must be annotated with @Entity");
            }
            checkEntity(entityType);
        }
        final ManyToOne m2o = property.getAnnotation(ManyToOne.class);
        if (m2o != null) {
            final Class<?> entityType = (m2o.targetEntity() != void.class) ? m2o.targetEntity() : type;
            if (entityType.getAnnotation(Entity.class) == null) {
                throw new BeanException(type, "must be annotated with @Entity");
            }
            checkEntity(entityType);
        }
    }

    @Override
    public boolean isActivated(Class<? extends Segment> type) {
        return get(type, Object.class, Entity.class) != null;
    }

    @Override
    public TypeContext beforeSegment(Class<? extends Segment> type) throws BeanException {
        checkEntity(type);
        return new Context(type);
    }

    private static class Context implements TypeContext {
        private final List<Class<?>> stack = new LinkedList<>();

        private Context(Class<? extends Segment> type) {
            stack.addLast(type);
        }

        @Override
        public void beforeFragmentProperty(Field fragment, Class<? extends Segment> type, int offset) throws BeanException {
            stack.addLast(checkFragment(fragment, type));
            this.unknownProperty(fragment, type);
        }

        @Override
        public void afterFragmentProperty(Field fragment, Class<? extends Segment> type) {
            stack.removeLast();
        }

        @Override
        public void unknownProperty(Field property, Class<?> type) throws BeanException {
            final SecondaryTable[] st = property.getAnnotationsByType(SecondaryTable.class);
            for (final SecondaryTable t : st) {
                final String prefix = getPrefix(stack.getLast().getPackage());
                checkName(property.getDeclaringClass(), t.name(), prefix, "@SecondaryTable[name]");
            }
            final JoinTable jt = property.getAnnotation(JoinTable.class);
            if (jt != null) {
                final String prefix = getPrefix(stack.getLast().getPackage());
                checkName(property.getDeclaringClass(), jt.name(), prefix, "@JoinTable[name]");
            }
            checkToOne(property, type);
            checkToMany(property, type);
        }
    }

}
