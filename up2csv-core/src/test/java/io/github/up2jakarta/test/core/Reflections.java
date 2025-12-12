package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.lov.core.AccessException;

import java.lang.reflect.Field;
import java.util.List;

public class Reflections {

    private static final Field NODE;
    private static final Field LIST;

    static {
        final Class<?> c1 = Up2Mapper.class.getSuperclass();
        try {
            NODE = c1.getDeclaredField("node");
            NODE.setAccessible(true);
        } catch (Exception cause) {
            throw new AccessException(c1, "node", cause);
        }
        final Class<?> c2 = NODE.getType();
        try {
            LIST = c2.getDeclaredField("properties");
            LIST.setAccessible(true);
        } catch (Exception cause) {
            throw new AccessException(c2, "properties", cause);
        }
    }

    static Object node(Object bean) {
        try {
            return NODE.get(bean);
        } catch (Exception cause) {
            throw new AccessException(NODE, cause);
        }
    }

    static List<?> list(Object bean) {
        try {
            final Object node = NODE.get(bean);
            return (List<?>) LIST.get(node);
        } catch (Exception cause) {
            throw new AccessException(NODE, cause);
        }
    }
}
