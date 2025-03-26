package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.core.Duration;
import io.github.up2jakarta.cii.core.DurationFormatter;
import io.github.up2jakarta.cii.core.TemporalFormatter;
import io.github.up2jakarta.cii.edi.TimePointFormatCodeType;
import org.junit.jupiter.api.Test;

import java.time.*;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
public class FormatterTest {

    @Test
    public void test102() {
        // Given
        var temporal = LocalDate.of(2024, 10, 22);
        var formatted = "20241022";
        // When
        var code = TimePointFormatCodeType.V_102;
        var formatter = code.getFormatter();
        // Then
        assertNotNull(formatter);
        assertNull(formatter.format(null));
        assertNull(formatter.parse(null));
        assertFalse(formatter.isComposite());
        assertEquals(TemporalFormatter.class, formatter.getClass());
        assertEquals(LocalDate.class, formatter.getTemporalClass());
        var codeFormatter = (TemporalFormatter<LocalDate>) formatter;
        assertEquals(formatted, codeFormatter.format(temporal));
        assertEquals(temporal, codeFormatter.parse(formatted));
    }

    @Test
    public void test203() {
        // Given
        var temporal = of(2024, 10, 22, 10, 30);
        var formatted = "202410221030";
        // When
        var code = TimePointFormatCodeType.V_203;
        var formatter = code.getFormatter();
        // Then
        assertNotNull(formatter);
        assertNull(formatter.format(null));
        assertNull(formatter.parse(null));
        assertFalse(formatter.isComposite());
        assertEquals(TemporalFormatter.class, formatter.getClass());
        assertEquals(LocalDateTime.class, formatter.getTemporalClass());
        var codeFormatter = (TemporalFormatter<LocalDateTime>) formatter;
        assertEquals(formatted, codeFormatter.format(temporal));
        assertEquals(temporal, codeFormatter.parse(formatted));
    }

    @Test
    public void test205() {
        // Given
        var offset = ZoneOffset.ofHoursMinutes(2, 30);
        var temporal = OffsetDateTime.of(of(2024, 10, 22, 10, 30), offset);
        var formatted = "202410221030+0230";
        // When
        var code = TimePointFormatCodeType.V_205;
        var formatter = code.getFormatter();
        // Then
        assertNotNull(formatter);
        assertNull(formatter.format(null));
        assertNull(formatter.parse(null));
        assertFalse(formatter.isComposite());
        assertEquals(TemporalFormatter.class, formatter.getClass());
        assertEquals(OffsetDateTime.class, formatter.getTemporalClass());
        var codeFormatter = (TemporalFormatter<OffsetDateTime>) formatter;
        assertEquals(formatted, codeFormatter.format(temporal));
        assertEquals(temporal, codeFormatter.parse(formatted));
    }

    @Test
    public void test209() {
        // Given
        var offset = ZoneOffset.ofHoursMinutes(2, 30);
        var temporal = OffsetTime.of(LocalTime.of(12, 10, 30), offset);
        var formatted = "121030+0230";
        // When
        var code = TimePointFormatCodeType.V_209;
        var formatter = code.getFormatter();
        // Then
        assertNotNull(formatter);
        assertNull(formatter.format(null));
        assertNull(formatter.parse(null));
        assertFalse(formatter.isComposite());
        assertEquals(TemporalFormatter.class, formatter.getClass());
        assertEquals(OffsetTime.class, formatter.getTemporalClass());
        var codeFormatter = (TemporalFormatter<OffsetTime>) formatter;
        assertEquals(formatted, codeFormatter.format(temporal));
        assertEquals(temporal, codeFormatter.parse(formatted));
    }

    @Test
    public void test602() {
        // Given
        var temporal = Year.of(2024);
        var formatted = "2024";
        // When
        var code = TimePointFormatCodeType.V_602;
        var formatter = code.getFormatter();
        // Then
        assertNotNull(formatter);
        assertNull(formatter.format(null));
        assertNull(formatter.parse(null));
        assertFalse(formatter.isComposite());
        assertEquals(TemporalFormatter.class, formatter.getClass());
        assertEquals(Year.class, formatter.getTemporalClass());
        var codeFormatter = (TemporalFormatter<Year>) formatter;
        assertEquals(formatted, codeFormatter.format(temporal));
        assertEquals(temporal, codeFormatter.parse(formatted));
    }

    @Test
    public void test502() {
        // Given
        var temporal = Duration.of(LocalTime.of(10, 30, 15), LocalTime.of(12, 15, 45));
        var formatted = "103015-121545";
        // When
        var code = TimePointFormatCodeType.V_502;
        var formatter = code.getFormatter();
        // Then
        assertNotNull(formatter);
        assertNull(formatter.format(null));
        assertNull(formatter.parse(null));
        assertTrue(formatter.isComposite());
        assertEquals(DurationFormatter.class, formatter.getClass());
        assertEquals(LocalTime.class, formatter.getTemporalClass());
        var codeFormatter = (DurationFormatter<LocalTime>) formatter;
        assertEquals(formatted, codeFormatter.format(temporal));
        assertEquals(temporal, codeFormatter.parse(formatted));
    }

}
