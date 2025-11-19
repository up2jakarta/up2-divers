package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.core.misc.TestConverter;
import io.github.up2jakarta.csv.core.misc.cvr.SupportEntity;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyConverter;
import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.lov.CodeListConverter;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.persistence.AttributeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
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
    void testDummyGeneric() throws BeanException {
        // GIVEN
        final Class<? extends CodeListConverter<?>> beanType = CurrencyConverter.class;
        // WHEN
        final Type[] arguments = getTypeArguments(beanType, beanType);
        // THEN
        assertEquals(0, arguments.length);
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
        final Class<? extends CodeListConverter<?>> beanType = CurrencyConverter.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, CodeListConverter.class);
        // Then
        assertEquals(1, arguments.length);
        assertEquals(CurrencyCodeType.class, arguments[0]);
    }

    @Test
    void testGetComplexAdapterArguments() throws BeanException {
        // Given
        final Class<? extends CodeListConverter<?>> beanType = CurrencyConverter.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, XmlAdapter.class);
        // Then
        assertEquals(2, arguments.length);
        assertEquals(String.class, arguments[0]);
        assertEquals(CurrencyCodeType.class, arguments[1]);
    }

    @Test
    void testGetSimpleConverterArguments() throws BeanException {
        // Given
        final Class<? extends AttributeConverter<?, ?>> beanType = TestConverter.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, AttributeConverter.class);
        // Then
        assertEquals(Long.class, arguments[0]);
        assertEquals(String.class, arguments[1]);
    }

    @Test
    void testGetComplexConverterArguments() throws BeanException {
        // Given
        final Class<? extends CodeListConverter<?>> beanType = CurrencyConverter.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, AttributeConverter.class);
        // Then
        assertEquals(2, arguments.length);
        assertEquals(CurrencyCodeType.class, arguments[0]);
        assertEquals(String.class, arguments[1]);
    }

    @Test
    void testGetParsedArguments() throws BeanException {
        // Given
        final Class<? extends Recordable<?>> beanType = SupportEntity.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, Recordable.class);
        // Then
        assertEquals(InputRecord.class, arguments[0]);
    }

    @Test
    void testGetObjectArguments() throws BeanException {
        // Given
        final Class<? extends Recordable<?>> beanType = SupportEntity.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, Object.class);
        // Then
        assertEquals(0, arguments.length);
    }

}
