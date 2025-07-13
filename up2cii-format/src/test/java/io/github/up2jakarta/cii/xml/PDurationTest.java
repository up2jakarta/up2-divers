package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.core.PDuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PDurationTest {

    @Test
    public void testDurationOfLocalTime() {
        //When
        var startTime = LocalTime.of(15, 30, 5);
        var endTime = LocalTime.of(17, 40, 10);
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            //Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());

            Assertions.assertEquals(0, duration.getYears());
            Assertions.assertEquals(0, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(2, duration.getHours());
            Assertions.assertEquals(10, duration.getMinutes());
            Assertions.assertEquals(5, duration.getSeconds());
        }
        {
            //When
            var duration = PDuration.of(endTime, startTime);
            //Then
            Assertions.assertEquals(endTime, duration.getStartTime());
            Assertions.assertEquals(startTime, duration.getUntilTime());
            assertTrue(duration.isNegative());

            Assertions.assertEquals(0, duration.getYears());
            Assertions.assertEquals(0, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(-2, duration.getHours());
            Assertions.assertEquals(-10, duration.getMinutes());
            Assertions.assertEquals(-5, duration.getSeconds());
        }
    }

    @Test
    public void testDurationOfOffsetTime() {
        //When
        var startTime = OffsetTime.of(15, 30, 5, 0, ZoneOffset.UTC);
        var endTime = OffsetTime.of(17, 40, 10, 0, ZoneOffset.UTC);
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            //Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());

            Assertions.assertEquals(0, duration.getYears());
            Assertions.assertEquals(0, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(2, duration.getHours());
            Assertions.assertEquals(10, duration.getMinutes());
            Assertions.assertEquals(5, duration.getSeconds());
        }
        {
            //When
            var duration = PDuration.of(endTime, startTime);
            //Then
            Assertions.assertEquals(endTime, duration.getStartTime());
            Assertions.assertEquals(startTime, duration.getUntilTime());
            assertTrue(duration.isNegative());

            Assertions.assertEquals(0, duration.getYears());
            Assertions.assertEquals(0, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(-2, duration.getHours());
            Assertions.assertEquals(-10, duration.getMinutes());
            Assertions.assertEquals(-5, duration.getSeconds());
        }
    }

    @Test
    public void testDurationOfLocalDate() {
        //When
        var startTime = LocalDate.of(2015, 5, 5);
        var endTime = LocalDate.of(2017, 10, 10);
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            //Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());

            Assertions.assertEquals(2, duration.getYears());
            Assertions.assertEquals(5, duration.getMonths());
            Assertions.assertEquals(5, duration.getDays());
            Assertions.assertEquals(0, duration.getHours());
            Assertions.assertEquals(0, duration.getMinutes());
            Assertions.assertEquals(0, duration.getSeconds());
        }
        {
            //When
            var duration = PDuration.of(endTime, startTime);
            //Then
            Assertions.assertEquals(endTime, duration.getStartTime());
            Assertions.assertEquals(startTime, duration.getUntilTime());
            assertTrue(duration.isNegative());

            Assertions.assertEquals(-2, duration.getYears());
            Assertions.assertEquals(-5, duration.getMonths());
            Assertions.assertEquals(-5, duration.getDays());
            Assertions.assertEquals(0, duration.getHours());
            Assertions.assertEquals(0, duration.getMinutes());
            Assertions.assertEquals(0, duration.getSeconds());
        }
    }

    @Test
    public void testDurationOfLocalDateTime() {
        //When
        var startTime = LocalDateTime.of(2023, 9, 2, 15, 30, 5);
        var endTime = LocalDateTime.of(2024, 10, 17, 17, 40, 10);
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            // Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(1, duration.getYears());
            Assertions.assertEquals(1, duration.getMonths());
            Assertions.assertEquals(15, duration.getDays());
            Assertions.assertEquals(2, duration.getHours());
            Assertions.assertEquals(10, duration.getMinutes());
            Assertions.assertEquals(5, duration.getSeconds());
        }
    }

    @Test
    public void testDurationOfOffsetDateTime() {
        //When
        var startTime = OffsetDateTime.of(2023, 9, 2, 15, 30, 5, 0, ZoneOffset.UTC);
        var endTime = OffsetDateTime.of(2024, 10, 17, 17, 40, 10, 0, ZoneOffset.UTC);
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            // Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(1, duration.getYears());
            Assertions.assertEquals(1, duration.getMonths());
            Assertions.assertEquals(15, duration.getDays());
            Assertions.assertEquals(2, duration.getHours());
            Assertions.assertEquals(10, duration.getMinutes());
            Assertions.assertEquals(5, duration.getSeconds());
        }
    }

    @Test
    public void testDurationOfZonedDateTime() {
        //When
        var startTime = ZonedDateTime.of(2023, 9, 2, 15, 30, 5, 0, ZoneOffset.UTC);
        var endTime = ZonedDateTime.of(2024, 10, 17, 17, 40, 10, 0, ZoneOffset.UTC);
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            // Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(1, duration.getYears());
            Assertions.assertEquals(1, duration.getMonths());
            Assertions.assertEquals(15, duration.getDays());
            Assertions.assertEquals(2, duration.getHours());
            Assertions.assertEquals(10, duration.getMinutes());
            Assertions.assertEquals(5, duration.getSeconds());
        }
    }

    @Test
    public void testDurationOfYearMonth() {
        //When
        var startTime = YearMonth.of(2015, 5);
        var endTime = YearMonth.of(2017, 10);
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            //Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());

            Assertions.assertEquals(2, duration.getYears());
            Assertions.assertEquals(5, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(0, duration.getHours());
            Assertions.assertEquals(0, duration.getMinutes());
            Assertions.assertEquals(0, duration.getSeconds());
        }
        {
            //When
            var duration = PDuration.of(endTime, startTime);
            //Then
            Assertions.assertEquals(endTime, duration.getStartTime());
            Assertions.assertEquals(startTime, duration.getUntilTime());
            assertTrue(duration.isNegative());

            Assertions.assertEquals(-2, duration.getYears());
            Assertions.assertEquals(-5, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(0, duration.getHours());
            Assertions.assertEquals(0, duration.getMinutes());
            Assertions.assertEquals(0, duration.getSeconds());
        }
    }

    @Test
    public void testDurationOfYear() {
        //When
        var startTime = Year.of(2020);
        var endTime = Year.of(2023);
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            //Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());

            Assertions.assertEquals(3, duration.getYears());
            Assertions.assertEquals(0, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(0, duration.getHours());
            Assertions.assertEquals(0, duration.getMinutes());
            Assertions.assertEquals(0, duration.getSeconds());
        }
        {
            //When
            var duration = PDuration.of(endTime, startTime);
            //Then
            Assertions.assertEquals(endTime, duration.getStartTime());
            Assertions.assertEquals(startTime, duration.getUntilTime());
            assertTrue(duration.isNegative());

            Assertions.assertEquals(-3, duration.getYears());
            Assertions.assertEquals(0, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(0, duration.getHours());
            Assertions.assertEquals(0, duration.getMinutes());
            Assertions.assertEquals(0, duration.getSeconds());
        }
    }

    @Test
    public void testDurationOfInstant() {
        //When
        var startTime = Instant.from(ZonedDateTime.of(2023, 9, 2, 15, 30, 5, 0, ZoneOffset.UTC));
        var endTime = Instant.from(ZonedDateTime.of(2023, 9, 2, 17, 40, 10, 0, ZoneOffset.UTC));
        {
            //When
            var duration = PDuration.of(startTime, endTime);
            // Then
            Assertions.assertEquals(startTime, duration.getStartTime());
            Assertions.assertEquals(endTime, duration.getUntilTime());
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(0, duration.getYears());
            Assertions.assertEquals(0, duration.getMonths());
            Assertions.assertEquals(0, duration.getDays());
            Assertions.assertEquals(2, duration.getHours());
            Assertions.assertEquals(10, duration.getMinutes());
            Assertions.assertEquals(5, duration.getSeconds());
        }
    }

    @Test
    public void testUnits() {
        //When
        var startTime = LocalDateTime.of(2023, 9, 25, 15, 30, 5);
        var endTime = LocalDateTime.of(2023, 9, 27, 17, 40, 10);
        var duration = PDuration.of(startTime, endTime);
        {
            // Unit
            var units = duration.getUnits();
            assertTrue(units.contains(ChronoUnit.YEARS));
            assertTrue(units.contains(ChronoUnit.MONTHS));
            assertTrue(units.contains(ChronoUnit.DAYS));
            assertTrue(units.contains(ChronoUnit.HOURS));
            assertTrue(units.contains(ChronoUnit.MINUTES));
            assertTrue(units.contains(ChronoUnit.SECONDS));
            assertTrue(units.contains(ChronoUnit.MILLIS));
            assertTrue(units.contains(ChronoUnit.NANOS));

            assertFalse(units.contains(ChronoUnit.WEEKS));
            assertFalse(units.contains(ChronoUnit.HALF_DAYS));
            assertFalse(units.contains(ChronoUnit.CENTURIES));
            assertFalse(units.contains(ChronoUnit.DECADES));
            assertFalse(units.contains(ChronoUnit.ERAS));
            assertFalse(units.contains(ChronoUnit.MILLENNIA));
            assertFalse(units.contains(ChronoUnit.MILLENNIA));
            assertFalse(units.contains(ChronoUnit.FOREVER));
        }
        {
            // Temporal
            assertEquals(0, duration.get(ChronoUnit.YEARS));
            assertEquals(0, duration.get(ChronoUnit.MONTHS));
            assertEquals(2, duration.get(ChronoUnit.DAYS));
            assertEquals(2, duration.get(ChronoUnit.HOURS));
            assertEquals(10, duration.get(ChronoUnit.MINUTES));
            assertEquals(5, duration.get(ChronoUnit.SECONDS));
        }
    }

    @Test
    public void testToString() {
        {
            //When
            var startTime = LocalTime.of(15, 30, 5);
            var endTime = LocalTime.of(17, 40, 10);
            // //Then
            Assertions.assertEquals("PT2H10M5S", PDuration.of(startTime, endTime).toString());
            Assertions.assertEquals("PT-2H-10M-5S", PDuration.of(endTime, startTime).toString());
            Assertions.assertEquals("PT0S", PDuration.of(startTime, startTime).toString());
        }
        {
            //When
            var startTime = LocalDateTime.of(2023, 9, 5, 10, 40, 10);
            var endTime = LocalDateTime.of(2023, 9, 25, 12, 50, 15);
            //Then
            Assertions.assertEquals("P20DT2H10M5S", PDuration.of(startTime, endTime).toString());
            Assertions.assertEquals("P-20DT-2H-10M-5S", PDuration.of(endTime, startTime).toString());
            Assertions.assertEquals("PT0S", PDuration.of(startTime, startTime).toString());
        }
        {
            //When
            var startTime = LocalDateTime.of(2023, 9, 5, 10, 40, 10);
            var endTime = LocalDateTime.of(2023, 11, 25, 12, 50, 15);
            //Then
            Assertions.assertEquals("P2M20DT2H10M5S", PDuration.of(startTime, endTime).toString());
            Assertions.assertEquals("P-2M-20DT-2H-10M-5S", PDuration.of(endTime, startTime).toString());
            Assertions.assertEquals("PT0S", PDuration.of(startTime, startTime).toString());
        }
        {
            //When
            var startTime = LocalDateTime.of(2023, 9, 5, 10, 40, 10);
            var endTime = LocalDateTime.of(2028, 11, 25, 12, 50, 15);
            //Then
            Assertions.assertEquals("P5Y2M20DT2H10M5S", PDuration.of(startTime, endTime).toString());
            Assertions.assertEquals("P-5Y-2M-20DT-2H-10M-5S", PDuration.of(endTime, startTime).toString());
            Assertions.assertEquals("PT0S", PDuration.of(startTime, startTime).toString());
        }
        {
            //When
            var startTime = LocalDateTime.of(2023, 9, 5, 10, 40, 10);
            var endTime = LocalDateTime.of(2028, 11, 25, 10, 40, 10);
            //Then
            Assertions.assertEquals("P5Y2M20D", PDuration.of(startTime, endTime).toString());
            Assertions.assertEquals("P-5Y-2M-20D", PDuration.of(endTime, startTime).toString());
            Assertions.assertEquals("PT0S", PDuration.of(startTime, startTime).toString());
        }
        {
            //When
            var startTime = LocalDate.of(2023, 9, 5);
            var endTime = LocalDate.of(2023, 9, 25);
            //Then
            Assertions.assertEquals("P20D", PDuration.of(startTime, endTime).toString());
            Assertions.assertEquals("P-20D", PDuration.of(endTime, startTime).toString());
            Assertions.assertEquals("PT0S", PDuration.of(startTime, startTime).toString());
        }
    }

    @Test
    public void testAddToLocalTime() {
        var startTime = LocalTime.of(15, 30, 5);
        var endTime = LocalTime.of(17, 40, 10);
        //Then
        {
            var duration = PDuration.of(startTime, endTime);
            assertEquals(endTime, duration.addTo(startTime));
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
        }
        {
            var duration = PDuration.of(startTime, startTime);
            assertEquals(startTime, duration.addTo(startTime));
            assertFalse(duration.isNegative());
            assertTrue(duration.isZero());
        }
        {
            var duration = PDuration.of(endTime, startTime);
            assertEquals(startTime, duration.addTo(endTime));
            assertTrue(duration.isNegative());
            assertFalse(duration.isZero());
        }
    }

    @Test
    public void testAddToLocalDateTime() {
        var startTime = LocalDateTime.of(2023, 9, 25, 15, 30, 5);
        var endTime = LocalDateTime.of(2028, 8, 27, 17, 40, 10);
        //Then
        {
            var duration = PDuration.of(startTime, endTime);
            assertEquals(endTime, duration.addTo(startTime));
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
        }
        {
            var duration = PDuration.of(startTime, startTime);
            assertEquals(startTime, duration.addTo(startTime));
            assertFalse(duration.isNegative());
            assertTrue(duration.isZero());
        }
        {
            var duration = PDuration.of(endTime, startTime);
            assertEquals(startTime, duration.addTo(endTime));
            assertTrue(duration.isNegative());
            assertFalse(duration.isZero());
        }
    }

    @Test
    public void testAddToLocalDate() {
        var startTime = LocalDate.of(2023, 9, 12);
        var endTime = LocalDate.of(2028, 8, 27);
        //Then
        {
            var duration = PDuration.of(startTime, endTime);
            assertEquals(endTime, duration.addTo(startTime));
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
        }
        {
            var duration = PDuration.of(startTime, startTime);
            assertEquals(startTime, duration.addTo(startTime));
            assertFalse(duration.isNegative());
            assertTrue(duration.isZero());
        }
        {
            var duration = PDuration.of(endTime, startTime);
            assertEquals(startTime, duration.addTo(endTime));
            assertTrue(duration.isNegative());
            assertFalse(duration.isZero());
        }
    }

    @Test
    public void testSubtractFromLocalTime() {
        var startTime = LocalTime.of(15, 30, 5);
        var endTime = LocalTime.of(17, 40, 10, 999_000_000);
        //Then
        {
            var duration = PDuration.of(startTime, endTime);
            assertEquals(startTime, duration.subtractFrom(endTime));
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(Duration.ofHours(2).plusMinutes(10).plusSeconds(5).plusMillis(999), duration.toDuration());
        }
        {
            var duration = PDuration.of(startTime, startTime);
            assertEquals(startTime, duration.subtractFrom(startTime));
            assertFalse(duration.isNegative());
            assertTrue(duration.isZero());
            Assertions.assertEquals(Duration.ZERO, duration.toDuration());
        }
        {
            var duration = PDuration.of(endTime, startTime);
            assertEquals(endTime, duration.subtractFrom(startTime));
            assertTrue(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(Duration.ofHours(-2).plusMinutes(-10).plusSeconds(-5).minusMillis(999), duration.toDuration());
        }
    }

    @Test
    public void testSubtractFromLocalDateTime() {
        var startTime = LocalDateTime.of(2023, 9, 25, 15, 30, 5);
        var endTime = LocalDateTime.of(2028, 8, 27, 17, 40, 10);
        //Then
        {
            var duration = PDuration.of(startTime, endTime);
            assertEquals(startTime, duration.subtractFrom(endTime));
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(Duration.ofHours(2).plusMinutes(10).plusSeconds(5), duration.toDuration());
            Assertions.assertEquals(Period.of(4, 11, 2), duration.toPeriod());
        }
        {
            var duration = PDuration.of(startTime, startTime);
            assertEquals(startTime, duration.subtractFrom(startTime));
            assertFalse(duration.isNegative());
            assertTrue(duration.isZero());
            Assertions.assertEquals(Duration.ZERO, duration.toDuration());
            Assertions.assertEquals(Period.ZERO, duration.toPeriod());
        }
        {
            var duration = PDuration.of(endTime, startTime);
            assertEquals(endTime, duration.subtractFrom(startTime));
            assertTrue(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(Duration.ofHours(-2).plusMinutes(-10).plusSeconds(-5), duration.toDuration());
            Assertions.assertEquals(Period.of(-4, -11, -2), duration.toPeriod());
        }
    }

    @Test
    public void testSubtractFromLocalDate() {
        var startTime = LocalDate.of(2023, 9, 12);
        var endTime = LocalDate.of(2028, 8, 27);
        //Then
        {
            var duration = PDuration.of(startTime, endTime);
            assertEquals(startTime, duration.subtractFrom(endTime));
            assertFalse(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(Period.of(4, 11, 15), duration.toPeriod());
        }
        {
            var duration = PDuration.of(startTime, startTime);
            assertEquals(startTime, duration.subtractFrom(startTime));
            assertFalse(duration.isNegative());
            assertTrue(duration.isZero());
            Assertions.assertEquals(Period.ZERO, duration.toPeriod());
        }
        {
            var duration = PDuration.of(endTime, startTime);
            assertEquals(endTime, duration.subtractFrom(startTime));
            assertTrue(duration.isNegative());
            assertFalse(duration.isZero());
            Assertions.assertEquals(Period.of(-4, -11, -15), duration.toPeriod());
        }
    }
}
