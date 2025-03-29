package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.core.LocalDateAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class DateAdapterTest {

    private final LocalDateAdapter adapter;

    @Autowired
    public DateAdapterTest(LocalDateAdapter adapter) {
        this.adapter = adapter;
    }

    @Test
    public void testDateWithoutOffset() {
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
