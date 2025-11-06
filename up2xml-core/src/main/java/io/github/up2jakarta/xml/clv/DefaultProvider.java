package io.github.up2jakarta.xml.clv;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.reflect.Modifier.*;

public final class DefaultProvider implements CodeListProvider<CodeList<?>> {

    public static final DefaultProvider INSTANCE = new DefaultProvider();

    private DefaultProvider() {
    }

    @Override
    public CodeList<?>[] values(Class<CodeList<?>> type) {
        if (type.isEnum()) {
            return type.getEnumConstants();
        }
        final List<CodeList<?>> constants = new LinkedList<>();
        final Field[] fields = type.getDeclaredFields();
        for (final Field field : fields) {
            this.constant(field, constants::add);
        }
        return constants.toArray(CodeList[]::new);
    }

    private void constant(Field field, Consumer<CodeList<?>> collector) {
        final int fms = field.getModifiers();
        if (isPublic(fms) && isStatic(fms) && isFinal(fms)) {
            try {
                final Object constant = field.get(null);
                if (constant instanceof CodeList<?> cl) {
                    collector.accept(cl);
                }
            } catch (Exception ignore) {
            }
        }
    }
}
