package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.extension.SeverityType;
import jakarta.xml.bind.ValidationEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeverityTypeTest {

    @Test
    void defaultValues() {
        // Then
        assertEquals(SeverityType.ERROR, SeverityType.of(-1));
    }

    @Test
    void warning() {
        // Given
        final SeverityType severity = SeverityType.WARNING;
        // Then
        assertEquals(ValidationEvent.WARNING, severity.getLevel());
        assertEquals("W", severity.getCode());
        assertEquals(severity, SeverityType.of(ValidationEvent.WARNING));
    }

    @Test
    void error() {
        // Given
        final SeverityType severity = SeverityType.ERROR;
        // Then
        assertEquals(ValidationEvent.ERROR, severity.getLevel());
        assertEquals("E", severity.getCode());
        assertEquals(severity, SeverityType.of(ValidationEvent.ERROR));
    }

    @Test
    void fatal() {
        // Given
        final SeverityType severity = SeverityType.FATAL;
        // Then
        assertEquals(ValidationEvent.FATAL_ERROR, severity.getLevel());
        assertEquals("F", severity.getCode());
        assertEquals(severity, SeverityType.of(ValidationEvent.FATAL_ERROR));
    }

}