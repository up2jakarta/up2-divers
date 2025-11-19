package io.github.up2jakarta.lov;

import io.github.up2jakarta.lov.cl.AccessMode30;
import io.github.up2jakarta.lov.cl.AccessMode32;
import io.github.up2jakarta.lov.core.BeanException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.github.up2jakarta.lov.core.Beans.getTypeArguments;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BeansTest {

    @Test
    @SuppressWarnings("ALL")
    void testSupportGeneric() {
        // GIVEN
        final Class<? extends List> beanType = ArrayList.class;
        // WHEN
        final BeanException thrown = assertThrows(BeanException.class, () -> getTypeArguments(beanType, CodeListConverter.class));
        // THEN
        assertEquals("ArrayList[class] - the root bean can not be generic", thrown.getMessage());
    }

    @Test
    void testDummyObject() throws BeanException {
        // GIVEN
        final Class<?> beanType = Object.class;
        // WHEN
        final Type[] arguments = getTypeArguments(beanType, List.class);
        // THEN
        assertEquals(0, arguments.length);
    }

    @Test
    void testGetSimpleCodeListArguments() throws BeanException {
        // Given
        final Class<? extends CodeList<?>> beanType = AccessMode32.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, CodeList.class);
        // Then
        assertEquals(1, arguments.length);
        assertEquals(AccessMode30.class, arguments[0]);
    }

}
