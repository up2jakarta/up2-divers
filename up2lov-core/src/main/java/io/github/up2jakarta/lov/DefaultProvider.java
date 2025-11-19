package io.github.up2jakarta.lov;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;

import static io.github.up2jakarta.lov.core.Beans.cast;
import static io.github.up2jakarta.lov.core.Beans.getTypeArguments;
import static java.lang.reflect.Modifier.*;

@SuppressWarnings("unchecked")
public final class DefaultProvider implements CodeListProvider<CodeList<?>> {

    public static final DefaultProvider INSTANCE = new DefaultProvider();

    private DefaultProvider() {
    }

    public static <C extends CodeList<?>> List<C> values(C code) {
        return INSTANCE.values((Class<C>) code.getClass());
    }

    private <C> Optional<C> value(Field field, Class<C> type) {
        final int fms = field.getModifiers();
        if (!isPrivate(fms) && isStatic(fms) && isFinal(fms)) {
            try {
                field.setAccessible(true);
                final Object constant = field.get(null);
                if (type.isInstance(constant)) {
                    return Optional.of((C) constant);
                }
            } catch (Exception ignore) {
            }
        }
        return Optional.empty();
    }

    private <C extends CodeList<?>> void values(Stack<Class<C>> stack, Class<C> type, Class<C> node, Consumer<C> cl) {
        stack.add(node);
        for (final Field field : node.getDeclaredFields()) {
            this.value(field, type).ifPresent(cl);
        }
        final Class<C>[] types = (Class<C>[]) node.getPermittedSubclasses();
        if (types != null) {
            for (final Class<C> pc : types) {
                if (!stack.contains(pc)) {
                    this.values(stack, type, pc, cl);
                }
            }
        }
        for (final Class<?> is : node.getInterfaces()) {
            if (!stack.contains(is)) {
                this.values(stack, type, (Class<C>) is, cl);
            }
        }
    }

    @Override
    public <C extends CodeList<?>> List<C> values(Class<C> type) {
        if (type.isEnum()) {
            return List.of(type.getEnumConstants());
        }
        try {
            final Type[] arguments = getTypeArguments(type, CodeList.class);
            if (arguments[0] instanceof Class<?> cl) {
                type = cast(cl);
            }
        } catch (Exception ignore) {
        }
        final List<C> values = new LinkedList<>();
        this.values(new Stack<>(), type, type, values::add);
        return Collections.unmodifiableList(values);
    }

}
