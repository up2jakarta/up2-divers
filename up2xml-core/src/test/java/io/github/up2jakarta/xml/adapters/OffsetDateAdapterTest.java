package io.github.up2jakarta.xml.adapters;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.github.up2jakarta.xml.adapters.Formatters.ISO_OFFSET_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OffsetDateAdapterTest {

    private final OffsetDateAdapter adapter = new OffsetDateAdapter(ISO_OFFSET_DATE, "XML-DT03");

    @Test
    public void testDateWithoutOffset() {
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

    @Test
    public void testDateWithinOffset() {
        final String testXml = "2002-09-24";
        // Test unmarshal
        final LocalDate testObject = adapter.unmarshal(testXml + 'Z');
        assertNotNull(testObject);
        assertEquals(LocalDate.of(2002, 9, 24), testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final LocalDate testObject2 = adapter.unmarshal(testXml + 'Z');
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

    @Test
    public void testDateWithinTZ() {
        final String testXml = "2002-09-24";
        // Test unmarshal
        final LocalDate testObject = adapter.unmarshal(testXml + "-06:00");
        assertNotNull(testObject);
        assertEquals(LocalDate.of(2002, 9, 24), testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final LocalDate testObject2 = adapter.unmarshal(testXml + "-06:00");
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

}
