package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.data.DataResolver;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public final class InputResolver extends DataResolver<GroupType> {

    public InputResolver() {
        super(GroupType.class);
    }

    @Override
    public Optional<? extends GroupType> get(List<Class<? extends Segment>> stack, Field[] path, Field field) throws BeanException {
        var def = field.getAnnotation(InputType.class);
        if (def != null) {
            return Optional.of(def.value());
        }
        for (int i = path.length - 1; i >= 0; i--) {
            def = path[i].getAnnotation(InputType.class);
            if (def != null) {
                return Optional.of(def.value());
            }
        }
        for (int i = stack.size() - 1; i >= 0; i--) {
            def = stack.get(i).getAnnotation(InputType.class);
            if (def != null) {
                return Optional.of(def.value());
            }
        }

        return Optional.empty();
    }

}
