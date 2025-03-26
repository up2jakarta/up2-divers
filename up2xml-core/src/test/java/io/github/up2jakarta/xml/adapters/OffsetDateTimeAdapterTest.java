package io.github.up2jakarta.xml.adapters;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OffsetDateTimeAdapterTest {

    @Test
    public void testDateTimeWithoutOffset() {
        final OffsetDateTimeAdapter adapter = new OffsetDateTimeAdapter();
        final String testXml = "2002-05-30T09:30:10";
        // Test unmarshal
        final OffsetDateTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml + testObject.getOffset(), xml);
        // Test unmarshal again
        final OffsetDateTime testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

    @Test
    public void testDateTimeWithinOffset() {
        final OffsetDateTimeAdapter adapter = new OffsetDateTimeAdapter();
        final String testXml = "2002-05-30T09:30:10.5Z";
        // Test unmarshal
        final OffsetDateTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final OffsetDateTime testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

    @Test
    public void testDateTimeWithTZ() {
        final OffsetDateTimeAdapter adapter = new OffsetDateTimeAdapter();
        final String testXml = "2002-05-30T09:30:10.5-06:00";
        // Test unmarshal
        final OffsetDateTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final OffsetDateTime testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

}
