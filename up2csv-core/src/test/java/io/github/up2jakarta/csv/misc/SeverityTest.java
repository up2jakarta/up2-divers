package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.misc.SeverityType.Level;
import jakarta.validation.Payload;
import jakarta.xml.bind.ValidationEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeverityTest {

    @Test
    void defaultValues() {
        interface TestLevel extends Payload {
        }
        // Then
        assertEquals(SeverityType.ERROR, SeverityType.of(TestLevel.class));
        assertEquals(SeverityType.ERROR, SeverityType.of(-1));
    }

    @Test
    void warning() {
        interface TestLevel extends Level.Warning {
        }
        // Given
        final SeverityType severity = SeverityType.WARNING;
        // Then
        assertEquals(ValidationEvent.WARNING, severity.getLevel());
        assertEquals("W", severity.getCode());
        assertEquals(severity, SeverityType.of(TestLevel.class));
        assertEquals(severity, SeverityType.of(ValidationEvent.WARNING));
    }

    @Test
    void error() {
        interface TestLevel extends Level.Error {
        }
        // Given
        final SeverityType severity = SeverityType.ERROR;
        // Then
        assertEquals(ValidationEvent.ERROR, severity.getLevel());
        assertEquals("E", severity.getCode());
        assertEquals(severity, SeverityType.of(TestLevel.class));
        assertEquals(severity, SeverityType.of(ValidationEvent.ERROR));
    }

    @Test
    void fatal() {
        interface TestLevel extends Level.Fatal {
        }
        // Given
        final SeverityType severity = SeverityType.FATAL;
        // Then
        assertEquals(ValidationEvent.FATAL_ERROR, severity.getLevel());
        assertEquals("F", severity.getCode());
        assertEquals(severity, SeverityType.of(TestLevel.class));
        assertEquals(severity, SeverityType.of(ValidationEvent.FATAL_ERROR));
    }

}