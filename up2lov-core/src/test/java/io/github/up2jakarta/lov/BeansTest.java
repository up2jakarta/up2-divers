package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.cl.AccessMode30;
import io.github.up2jakarta.lov.cl.AccessMode32;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.github.up2jakarta.lov.core.Beans.getTypeArguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
