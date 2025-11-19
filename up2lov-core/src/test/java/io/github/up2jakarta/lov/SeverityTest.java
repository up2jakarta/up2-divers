package io.github.up2jakarta.lov;

import jakarta.xml.bind.ValidationEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SeverityTest {

    @Test
    public void testFatalError() {
        // Given
        var level = ValidationEvent.FATAL_ERROR;
        // When
        var severity = SeverityType.of(level);
        // Then
        assertNotNull(severity);
        assertEquals(level, severity.getAsInt());
    }

    @Test
    public void testError() {
        // Given
        var level = ValidationEvent.ERROR;
        // When
        var severity = SeverityType.of(level);
        // Then
        assertNotNull(severity);
        assertEquals(level, severity.getAsInt());
    }

    @Test
    public void testWarning() {
        // Given
        var level = ValidationEvent.WARNING;
        // When
        var severity = SeverityType.of(level);
        // Then
        assertNotNull(severity);
        assertEquals(level, severity.getAsInt());
    }
}
