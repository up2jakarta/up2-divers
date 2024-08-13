package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

final class FragmentProperty extends Property<Object> {

    private final Constructor<?> constructor;
    private final Property<?>[] properties;

    FragmentProperty(Class<? extends Segment> type, Field field, int offset, Property<?>[] properties) throws BeanException {
        super(field, offset);
        this.constructor = Beans.getDefaultConstructor(type);
        this.properties = properties;
    }

    Constructor<?> getConstructor() {
        return constructor;
    }

    Property<?>[] getProperties() {
        return properties;
    }

    @Override
    void setValue(Object bean, Object value) throws BeanException {
        Beans.setValue(bean, value, setter);
    }

}
