package io.github.up2jakarta.xml.clv;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.lang.reflect.Modifier.*;

public final class DefaultProvider implements CodeListProvider<CodeList<?>> {

    public static final DefaultProvider INSTANCE = new DefaultProvider();

    private DefaultProvider() {
    }

    @SuppressWarnings("unchecked")
    private static <S> Optional<S> value(Field field, Class<S> type) {
        final int fms = field.getModifiers();
        if (!isPrivate(fms) && isStatic(fms) && isFinal(fms)) {
            try {
                if (!isPublic(fms)) {
                    field.setAccessible(true);
                }
                final Object constant = field.get(null);
                if (type.isInstance(constant)) {
                    return Optional.of((S) constant);
                }
            } catch (Exception ignore) {
            }
        }
        return Optional.empty();
    }

    public static <S extends CodeList<?>> S any(Class<S> type) {
        if (type.isEnum()) {
            final S[] constants = type.getEnumConstants();
            if (constants.length != 0) {
                return constants[0];
            }
            return null;
        }
        for (final Field field : type.getDeclaredFields()) {
            final Optional<S> value = value(field, type);
            if (value.isPresent()) {
                return value.get();
            }
        }
        return null;
    }

    @Override
    public List<CodeList<?>> values(Class<CodeList<?>> type) {
        if (type.isEnum()) {
            return List.of(type.getEnumConstants());
        }
        final List<CodeList<?>> values = new LinkedList<>();
        for (final Field field : type.getDeclaredFields()) {
            value(field, type).ifPresent(values::add);
        }
        return Collections.unmodifiableList(values);
    }

}
