package io.github.up2jakarta.test.adapters;

import io.github.up2jakarta.xml.adapters.LocalDateAdapter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.github.up2jakarta.xml.adapters.Formatters.ISO_LOCAL_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocalDateAdapterTest {

    private final LocalDateAdapter adapter = new LocalDateAdapter(ISO_LOCAL_DATE, "XML-DT04");

    @Test
    public void testDate() {
        final String testXml = "2002-09-24";
        // Test unmarshal
        final LocalDate testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        assertEquals(LocalDate.of(2002, 9, 24), testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final LocalDate testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

}
