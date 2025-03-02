package io.github.up2jakarta.cii.xml.adapters;

import org.junit.jupiter.api.Test;

import java.time.OffsetTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OffsetTimeAdapterTest {

    @Test
    public void testTimeWithoutOffset() {
        final OffsetTimeAdapter adapter = new OffsetTimeAdapter();
        final String testXml = "09:30:10";
        // Test unmarshal
        final OffsetTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml + testObject.getOffset(), xml);
        // Test unmarshal again
        final OffsetTime testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

    @Test
    public void testTimeWithinOffset() {
        final OffsetTimeAdapter adapter = new OffsetTimeAdapter();
        final String testXml = "09:30:10Z";
        // Test unmarshal
        final OffsetTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final OffsetTime testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

    @Test
    public void testTimeWithinTZ() {
        final OffsetTimeAdapter adapter = new OffsetTimeAdapter();
        final String testXml = "09:30:10-06:00";
        // Test unmarshal
        final OffsetTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final OffsetTime testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

}
