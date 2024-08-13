package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.test.bean.TestConverter;
import jakarta.persistence.AttributeConverter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeansTest {

    @Test
    void testDummyObject() throws BeanException {
        // GIVEN
        final Class<?> beanType = Object.class;
        // WHEN
        final Type[] arguments = Beans.getTypeArguments(beanType, List.class);
        // THEN
        assertEquals(0, arguments.length);
    }

    @Test
    void testGetSimpleConverterArguments() throws BeanException {
        // Given
        final Class<? extends AttributeConverter<?, ?>> beanType = TestConverter.class;
        // When
        final Type[] arguments = Beans.getTypeArguments(beanType, AttributeConverter.class);
        // Then
        assertEquals(Long.class, arguments[0]);
        assertEquals(String.class, arguments[1]);
    }

}
