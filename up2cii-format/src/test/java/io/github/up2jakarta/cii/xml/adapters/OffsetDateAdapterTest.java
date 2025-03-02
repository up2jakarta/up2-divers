package io.github.up2jakarta.cii.xml.adapters;

import io.github.up2jakarta.cii.xml.OffsetDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OffsetDateAdapterTest {

    @Test
    public void testDateWithoutOffset() {
        final OffsetDateAdapter adapter = new OffsetDateAdapter();
        final String testXml = "2002-09-24";
        // Test unmarshal
        final OffsetDate testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml + testObject.getOffset(), xml);
        // Test unmarshal again
        final OffsetDate testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

    @Test
    public void testDateWithinOffset() {
        final OffsetDateAdapter adapter = new OffsetDateAdapter();
        final String testXml = "2002-09-24Z";
        // Test unmarshal
        final OffsetDate testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final OffsetDate testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

    @Test
    public void testDateWithinTZ() {
        final OffsetDateAdapter adapter = new OffsetDateAdapter();
        final String testXml = "2002-09-24-06:00";
        // Test unmarshal
        final OffsetDate testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final OffsetDate testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

}
