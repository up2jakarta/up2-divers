package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.TypeContext;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

import static io.github.up2jakarta.lov.core.AccessException.notNull;
import static io.github.up2jakarta.lov.core.Beans.cast;
import static io.github.up2jakarta.lov.core.Beans.getTypeArgument;
import static java.lang.reflect.Modifier.*;

/**
 * Simple implementation of {@link CodeListProvider} that supports {@link Enum} values registry
 * or java-beans constants excepts {@link DynamicCode} in favor of {@link DynamicProvider}.
 */
@Support(excludes = DynamicCode.class)
public final class ConstantProvider extends CodeListProvider<CodeList<?>> {

    public static final ConstantProvider INSTANCE = new ConstantProvider();

    private ConstantProvider() {
    }

    private static boolean isValid(Field field) {
        final int fms = field.getModifiers();
        if (isStatic(fms) && isFinal(fms) && !field.isSynthetic()) {
            final Deprecated config = field.getAnnotation(Deprecated.class);
            return (config == null || !config.exclude());
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static <C extends CodeList<?>> void values(List<Class<C>> cs, Class<C> type, Class<C> nd, Consumer<C> cl) {
        cs.add(nd);
        for (final Field field : nd.getDeclaredFields()) {
            if (isValid(field)) {
                try {
                    if (!isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    final Object constant = field.get(null);
                    if (type.isInstance(constant)) {
                        cl.accept((C) constant);
                    }
                } catch (Exception ignore) {
                }
            }
        }
        final Class<C>[] types = (Class<C>[]) nd.getPermittedSubclasses();
        if (types != null) {
            for (final Class<C> pc : types) {
                if (!cs.contains(pc)) {
                    values(cs, type, pc, cl);
                }
            }
        }
        for (final Class<?> is : nd.getInterfaces()) {
            if (!cs.contains(is)) {
                values(cs, type, (Class<C>) is, cl);
            }
        }
    }

    /**
     * @see CodeListProvider#values(Class, TypeContext)
     */
    public static <C extends CodeList<?>> List<C> values(Class<C> type) {
        notNull(type, ConstantProvider.class, "type");
        final Set<C> values = new LinkedHashSet<>();
        final List<Class<C>> stack = new LinkedList<>();
        values(stack, type, type, values::add);
        type = getTypeArgument(type, CodeList.class, 0, type);
        if (!stack.contains(type)) {
            values(stack, type, type, values::add);
        }
        return new ArrayList<>(values);
    }

    /**
     * @see CodeListProvider#values(Class, TypeContext)
     */
    public static <C extends CodeList<?>> List<C> values(C code) {
        notNull(code, ConstantProvider.class, "code");
        final Class<C> type = cast(code.getClass());
        if (type == DynamicCode.class) {
            throw new AccessException(DynamicProvider.class, "unsupported type: " + type.getName());
        }
        return values(type);
    }

    /**
     * Generates and returns code-list name from the specified <code>code</code>.
     * <p>
     * If the specified code is unique then the returned constant name will be unique too.
     *
     * @param code the code-list code
     * @return Java valid constant name
     */
    public static String constant(String code) {
        code = code.toUpperCase();
        if (Character.isDigit(code.charAt(0))) {
            code = "V_" + code;
        }
        return code.replace("-", "_");
    }

    @Override
    protected <C extends CodeList<?>> List<C> values(Class<C> type, TypeContext ignore) {
        return values(type);
    }

}
