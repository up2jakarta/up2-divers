package io.github.up2jakarta.test.adapters;

import io.github.up2jakarta.xml.adapters.OffsetTimeAdapter;
import org.junit.jupiter.api.Test;

import java.time.OffsetTime;

import static io.github.up2jakarta.xml.adapters.Formatters.ISO_OFFSET_TIME;
import static io.github.up2jakarta.xml.adapters.Formatters.defaultOffset;
import static java.time.ZoneOffset.UTC;
import static java.time.ZoneOffset.ofHours;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OffsetTimeAdapterTest {

    private final OffsetTimeAdapter adapter = new OffsetTimeAdapter(ISO_OFFSET_TIME, "XML-DT02");

    @Test
    public void testTimeWithoutOffset() {
        final String testXml = "09:30:10";
        // Test unmarshal
        final OffsetTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        assertEquals(OffsetTime.of(9, 30, 10, 0, defaultOffset()), testObject);
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
        final String testXml = "09:30:10Z";
        // Test unmarshal
        final OffsetTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        assertEquals(OffsetTime.of(9, 30, 10, 0, UTC), testObject);
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
        final String testXml = "09:30:10+06:00";
        // Test unmarshal
        final OffsetTime testObject = adapter.unmarshal(testXml);
        assertNotNull(testObject);
        assertEquals(OffsetTime.of(9, 30, 10, 0, ofHours(6)), testObject);
        // Test marshal
        String xml = adapter.marshal(testObject);
        assertEquals(testXml, xml);
        // Test unmarshal again
        final OffsetTime testObject2 = adapter.unmarshal(testXml);
        assertNotNull(testObject2);
        assertEquals(testObject, testObject2);
    }

}
