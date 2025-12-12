package io.github.up2jakarta.test.core;

import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.test.lov.AccessMode1;
import io.github.up2jakarta.test.lov.AccessMode30;
import io.github.up2jakarta.test.lov.AccessMode32;
import io.github.up2jakarta.test.lov.Bound;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.github.up2jakarta.lov.core.Beans.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BeansTest {

    @Test
    void testUnknown() {
        // WHEN
        final Type[] arguments = getTypeArguments(ArrayList.class, List.class);
        // THEN
        assertEquals(1, arguments.length);
        assertEquals("E", arguments[0].getTypeName());
    }

    @Test
    void testGeneric() {
        // WHEN
        final Type[] arguments = getTypeArguments(ArrayList.class, List.class, String.class);
        // THEN
        assertEquals(1, arguments.length);
        assertEquals(String.class, arguments[0]);
    }

    @Test
    void testObject() {
        // GIVEN
        final Class<?> beanType = Object.class;
        // WHEN
        final Type[] arguments = getTypeArguments(beanType, String.class);
        // THEN
        assertEquals(0, arguments.length);
    }

    @Test
    void testCodeList() {
        // Given
        final Class<? extends CodeList<?>> beanType = AccessMode32.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, CodeList.class);
        // Then
        assertEquals(1, arguments.length);
        assertEquals(AccessMode30.class, arguments[0]);
    }

    @Test
    void testBound0() {
        // Given
        final Class<?> type = Bound.class;
        // When
        final Type[] types = getTypeArguments(type, type, AccessMode1.class);
        // Then
        assertEquals(1, types.length);
        assertEquals(AccessMode1.class, types[0]);
    }

    @Test
    void testBound1() throws NoSuchFieldException {
        // Given
        final Field property = Bound.class.getDeclaredField("type1");
        final ParameterizedType type = assertInstanceOf(ParameterizedType.class, property.getGenericType());
        // When
        final Type[] types = resolveArguments(CodeList.class, type, AccessMode1.class);
        assertEquals(1, types.length);
        final Class<?> propertyClass = getPropertyClass(property, types[0]);
        // Then
        assertEquals(AccessMode1.class, propertyClass);
    }

    @Test
    void testBound2() throws NoSuchFieldException {
        // Given
        final Field property = Bound.class.getDeclaredField("type2");
        final ParameterizedType type = assertInstanceOf(ParameterizedType.class, property.getGenericType());
        // When
        final Type[] types = resolveArguments(CodeList.class, type, AccessMode1.class);
        assertEquals(1, types.length);
        final Class<?> propertyClass = getPropertyClass(property, types[0]);
        // Then
        assertEquals(Object.class, propertyClass);
    }

}
