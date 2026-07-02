package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.core.BSBuilder.MEP;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.reflect.*;

import static io.github.up2jakarta.csv.api.Container.TRUSTED_MODE;
import static io.github.up2jakarta.csv.core.ext.Beans.getValue;
import static io.github.up2jakarta.csv.core.ext.Beans.setValue;
import static io.github.up2jakarta.lov.core.Defaults.prototype;
import static io.github.up2jakarta.lov.core.Localizable.CLASS;
import static io.github.up2jakarta.lov.core.Localizable.CREATOR;
import static java.lang.System.getProperty;
import static java.lang.invoke.MethodHandles.Lookup;

/**
 * Bean Accessor Factory for creating method, field and constructor accessors.
 *
 * @see IAccessor
 * @see IGetter
 * @see ISetter
 */
public abstract sealed class BeanAccessor permits BeanAccessor.IM, BeanAccessor.RM {

    /**
     * @return the singleton instance depending on the system property.
     */
    public static BeanAccessor getInstance() {
        return Holder.INSTANCE;
    }

    public abstract <V> IGetter<V> toGetter(Method getter) throws AccessException;

    public abstract <V> ISetter<V> toSetter(Method setter) throws AccessException;

    public abstract <V> IGetter<V> toGetter(Field field) throws AccessException;

    @SuppressWarnings("unused")
    public abstract <V> ISetter<V> toSetter(Field field) throws AccessException;

    public abstract <V> IAccessor<V> toAccessor(Field field) throws AccessException;

    @SuppressWarnings("unused")
    public abstract <V> IAccessor<V> toAccessor(Method getter, Method setter) throws AccessException;

    public abstract <T> ICreator<T> toCreator(Constructor<T> source) throws BeanException;

    /**
     * Property Getter Accessor.
     *
     * @param <V> the property type
     */
    public sealed interface IGetter<V> extends MEP permits FG, HG, IAccessor, MG {
        V get(Object bean, Member source) throws AccessException;
    }

    /**
     * Property Setter Accessor.
     *
     * @param <V> the property type
     */
    public sealed interface ISetter<V> extends MEP permits FS, HS, IAccessor, MS {
        void set(Object bean, V value, Member source) throws AccessException;
    }

    /**
     * Property Getter and Setter Accessor.
     *
     * @param <V> the property type
     */
    public sealed interface IAccessor<V> extends ISetter<V>, IGetter<V> permits FA, HA, MA, VA {
    }

    /**
     * Bean Constructor Accessor.
     *
     * @param <T> the bean type
     */
    public abstract sealed static class ICreator<T> permits HC, RC {
        protected final Constructor<T> source;

        protected ICreator(Constructor<T> source) {
            this.source = source;
        }

        public abstract T newInstance(Object... arguments) throws AccessException;

        public final Parameter[] getParameters() {
            return source.getParameters();
        }

        public Object[] getPrototype() {
            return prototype(source);
        }
    }

    /**
     * Internal {@link BeanAccessor} Holder
     */
    private static class Holder {
        private static final BeanAccessor INSTANCE;

        static {
            final boolean trusted = "true".equals(getProperty(TRUSTED_MODE));
            if (trusted) {
                try {
                    final Field tf = Lookup.class.getDeclaredField("IMPL_LOOKUP");
                    tf.setAccessible(true);
                    INSTANCE = new IM((Lookup) tf.get(null));
                } catch (Exception cause) {
                    throw new AccessException(Lookup.class, CLASS, cause);
                }
            } else {
                INSTANCE = new RM();
            }
        }
    }

    /**
     * Invoke API Mode.
     */
    static final class IM extends BeanAccessor {
        private final Lookup lookup;

        private IM(Lookup lookup) {
            this.lookup = lookup;
        }

        private MethodHandle handle(Method method) {
            try {
                return lookup.unreflect(method);
            } catch (Exception cause) {
                throw new AccessException(method, cause);
            }
        }

        @Override
        public <V> IGetter<V> toGetter(Method getter) throws AccessException {
            return new HG<>(this.handle(getter));
        }

        @Override
        public <V> ISetter<V> toSetter(Method setter) throws AccessException {
            return new HS<>(this.handle(setter));
        }

        @Override
        public <V> IGetter<V> toGetter(Field field) throws AccessException {
            try {
                return new HG<>(lookup.findGetter(field.getDeclaringClass(), field.getName(), field.getType()));
            } catch (Exception cause) {
                throw new AccessException(field, cause);
            }
        }

        @Override
        public <V> ISetter<V> toSetter(Field field) throws AccessException {
            try {
                return new HS<>(lookup.findSetter(field.getDeclaringClass(), field.getName(), field.getType()));
            } catch (Exception cause) {
                throw new AccessException(field, cause);
            }
        }

        @Override
        public <V> IAccessor<V> toAccessor(Field field) throws AccessException {
            try {
                return new VA<>(lookup.unreflectVarHandle(field));
            } catch (Exception cause) {
                throw new AccessException(field, cause);
            }
        }

        @Override
        public <V> IAccessor<V> toAccessor(Method getter, Method setter) throws AccessException {
            return new HA<>(this.handle(getter), this.handle(setter));
        }

        @Override
        public <T> ICreator<T> toCreator(Constructor<T> source) {
            final Parameter[] parameters = source.getParameters();
            final Class<?>[] types = new Class[parameters.length];
            for (var i = 0; i < parameters.length; i++) {
                types[i] = parameters[i].getType();
            }
            try {
                final MethodType type = MethodType.methodType(void.class, types);
                final MethodHandle handle = lookup.findConstructor(source.getDeclaringClass(), type);
                return new HC<>(source, handle);
            } catch (Exception cause) {
                throw new AccessException(source.getDeclaringClass(), CREATOR, cause);
            }
        }
    }

    /**
     * Reflection API Mode.
     */
    static final class RM extends BeanAccessor {
        private RM() {
        }

        @Override
        public <V> IGetter<V> toGetter(Method getter) throws AccessException {
            getter.setAccessible(true);
            return new MG<>(getter);
        }

        @Override
        public <V> ISetter<V> toSetter(Method setter) throws AccessException {
            setter.setAccessible(true);
            return new MS<>(setter);
        }

        @Override
        public <T> ICreator<T> toCreator(Constructor<T> source) {
            source.setAccessible(true);
            return new RC<>(source);
        }

        @Override
        public <V> IGetter<V> toGetter(Field field) throws AccessException {
            field.setAccessible(true);
            return new FG<>(field);
        }

        @Override
        public <V> ISetter<V> toSetter(Field field) throws AccessException {
            field.setAccessible(true);
            return new FS<>(field);
        }

        @Override
        public <V> IAccessor<V> toAccessor(Field field) throws AccessException {
            field.setAccessible(true);
            return new FA<>(field);
        }

        @Override
        public <V> IAccessor<V> toAccessor(Method getter, Method setter) throws AccessException {
            getter.setAccessible(true);
            setter.setAccessible(true);
            return new MA<>(getter, setter);
        }
    }

    private static final class HC<T> extends ICreator<T> {
        private final MethodHandle handle;

        private HC(Constructor<T> source, MethodHandle handle) {
            super(source);
            this.handle = handle;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T newInstance(Object... arguments) {
            try {
                return (T) handle.invokeWithArguments(arguments);
            } catch (Throwable cause) {
                throw new AccessException(source.getDeclaringClass(), CREATOR, cause);
            }
        }
    }

    private static final class RC<T> extends ICreator<T> {
        private RC(Constructor<T> delegate) {
            super(delegate);
        }

        @Override
        public T newInstance(Object... arguments) {
            try {
                return source.newInstance(arguments);
            } catch (InvocationTargetException cause) {
                throw new AccessException(source.getDeclaringClass(), CREATOR, cause.getTargetException());
            } catch (Exception cause) {
                throw new AccessException(source.getDeclaringClass(), CREATOR, cause);
            }
        }
    }

    private static final class HG<V> implements IGetter<V> {
        private final MethodHandle handle;

        private HG(MethodHandle handle) {
            this.handle = handle;
        }

        @Override
        public V get(Object bean, Member source) throws AccessException {
            return getValue(handle, bean, source);
        }
    }

    private static final class MG<V> implements IGetter<V> {
        private final Method getter;

        private MG(Method getter) {
            this.getter = getter;
        }

        @Override
        public V get(Object bean, Member source) throws AccessException {
            return getValue(getter, bean, source);
        }
    }

    private static final class FG<V> implements IGetter<V> {
        private final Field field;

        private FG(Field field) {
            this.field = field;
        }

        @Override
        public V get(Object bean, Member source) throws AccessException {
            return getValue(field, bean, source);
        }
    }

    private static final class HS<V> implements ISetter<V> {
        private final MethodHandle handle;

        private HS(MethodHandle handle) {
            this.handle = handle;
        }

        @Override
        public void set(Object bean, V value, Member source) throws AccessException {
            setValue(handle, bean, value, source);
        }
    }

    private static final class MS<V> implements ISetter<V> {
        private final Method setter;

        private MS(Method setter) {
            this.setter = setter;
        }

        @Override
        public void set(Object bean, V value, Member source) throws AccessException {
            setValue(setter, bean, value, source);
        }
    }

    private static final class FS<V> implements ISetter<V> {
        private final Field field;

        private FS(Field field) {
            this.field = field;
        }

        @Override
        public void set(Object bean, V value, Member source) throws AccessException {
            setValue(field, bean, value, source);
        }
    }

    private static final class FA<V> implements IAccessor<V> {
        private final Field field;

        private FA(Field field) {
            this.field = field;
        }

        @Override
        public V get(Object bean, Member source) throws AccessException {
            return getValue(field, bean, source);
        }

        @Override
        public void set(Object bean, V value, Member source) throws AccessException {
            setValue(field, bean, value, source);
        }
    }

    private static final class VA<V> implements IAccessor<V> {
        private final VarHandle handle;

        private VA(VarHandle handle) {
            this.handle = handle;
        }

        @Override
        public V get(Object bean, Member source) throws AccessException {
            return getValue(handle, bean, source);
        }

        @Override
        public void set(Object bean, V value, Member source) throws AccessException {
            setValue(handle, bean, value, source);
        }
    }

    private static final class HA<V> implements IAccessor<V> {
        private final MethodHandle getter, setter;

        private HA(MethodHandle getter, MethodHandle setter) {
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public V get(Object bean, Member source) throws AccessException {
            return getValue(getter, bean, source);
        }

        @Override
        public void set(Object bean, V value, Member source) throws AccessException {
            setValue(setter, bean, value, source);
        }
    }

    private static final class MA<V> implements IAccessor<V> {
        private final Method getter, setter;

        private MA(Method getter, Method setter) {
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public V get(Object bean, Member source) throws AccessException {
            return getValue(getter, bean, source);
        }

        @Override
        public void set(Object bean, V value, Member source) throws AccessException {
            setValue(setter, bean, value, source);
        }
    }
}
