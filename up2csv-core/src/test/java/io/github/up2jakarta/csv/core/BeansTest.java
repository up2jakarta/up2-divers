package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.core.misc.TestConverter;
import io.github.up2jakarta.csv.core.misc.cvr.SupportEntity;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyConverter;
import io.github.up2jakarta.csv.data.Recordable;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.persistence.AttributeConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static io.github.up2jakarta.lov.core.Beans.getTypeArguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BeansTest {

    @Test
    void testDummyGeneric() {
        // GIVEN
        final Class<? extends CodeListAdapter<?>> beanType = CurrencyConverter.class;
        // WHEN
        final Type[] arguments = getTypeArguments(beanType, beanType);
        // THEN
        assertEquals(0, arguments.length);
    }

    @Test
    void testGetSimpleCodeListArguments() {
        // Given
        final Class<? extends CodeListAdapter<?>> beanType = CurrencyConverter.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, CodeListAdapter.class);
        // Then
        assertEquals(1, arguments.length);
        assertEquals(CurrencyCodeType.class, arguments[0]);
    }

    @Test
    void testGetComplexAdapterArguments() {
        // Given
        final Class<? extends CodeListAdapter<?>> beanType = CurrencyConverter.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, XmlAdapter.class);
        // Then
        assertEquals(2, arguments.length);
        assertEquals(String.class, arguments[0]);
        assertEquals(CurrencyCodeType.class, arguments[1]);
    }

    @Test
    void testGetSimpleConverterArguments() {
        // Given
        final Class<? extends AttributeConverter<?, ?>> beanType = TestConverter.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, AttributeConverter.class);
        // Then
        assertEquals(Long.class, arguments[0]);
        assertEquals(String.class, arguments[1]);
    }

    @Test
    void testGetComplexConverterArguments() {
        // Given
        final Class<? extends CodeListAdapter<?>> beanType = CurrencyConverter.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, AttributeConverter.class);
        // Then
        assertEquals(2, arguments.length);
        assertEquals(CurrencyCodeType.class, arguments[0]);
        assertEquals(String.class, arguments[1]);
    }

    @Test
    void testGetParsedArguments() {
        // Given
        final Class<? extends Recordable<?>> beanType = SupportEntity.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, Recordable.class);
        // Then
        assertEquals(InputRecord.class, arguments[0]);
    }

    @Test
    void testGetObjectArguments() {
        // Given
        final Class<? extends Recordable<?>> beanType = SupportEntity.class;
        // When
        final Type[] arguments = getTypeArguments(beanType, Object.class);
        // Then
        assertEquals(0, arguments.length);
    }

}
