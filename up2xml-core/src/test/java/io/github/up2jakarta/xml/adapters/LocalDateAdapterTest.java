package io.github.up2jakarta.xml.adapters;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocalDateAdapterTest {

    @Test
    public void testDateWithoutOffset() {
        final LocalDateAdapter adapter = new LocalDateAdapter();
        final String testXml = "20020924";
        // Test unmarshal
        final LocalDate testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final LocalDate testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }
}
