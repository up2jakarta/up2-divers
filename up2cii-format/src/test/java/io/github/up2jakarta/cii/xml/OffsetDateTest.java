package io.github.up2jakarta.cii.xml;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;

import static io.github.up2jakarta.cii.xml.Formatters.DEFAULT_OFFSET;
import static io.github.up2jakarta.cii.xml.Formatters.ISO_OFFSET_DATE;
import static java.time.ZoneOffset.UTC;
import static java.time.ZoneOffset.ofHours;
import static org.junit.jupiter.api.Assertions.*;

public class OffsetDateTest {

    @Test
    public void testStatic() {
        assertNotNull(OffsetDate.MAX);
        assertNotNull(OffsetDate.MIN);
        assertNotNull(OffsetDate.timeLineOrder());
        assertEquals(OffsetDate.now(), OffsetDate.of(LocalDate.now(), DEFAULT_OFFSET));
        assertEquals(OffsetDate.now(ZoneId.systemDefault()), OffsetDate.of(LocalDate.now(), DEFAULT_OFFSET));
        assertEquals(OffsetDate.parse("2023-08-30Z"), OffsetDate.of(LocalDate.of(2023, 8, 30), UTC));
    }

    @Test
    public void testNonStatic() {
        final OffsetDate date1 = OffsetDate.of(LocalDate.of(2023, 8, 30), DEFAULT_OFFSET);
        final OffsetDate dateUTC = OffsetDate.of(LocalDate.of(2023, 8, 30), UTC);
        final OffsetDate date2 = OffsetDate.of(2024, 9, 10, UTC);

        assertEquals(date1.withOffsetSameInstant(UTC), dateUTC);
        assertEquals(date1.withOffsetSameInstant(DEFAULT_OFFSET), date1);
        assertEquals(2023, date1.getYear());
        assertEquals(Month.AUGUST, date1.getMonth());
        assertEquals(8, date1.getMonthValue());
        assertEquals(30, date1.getDayOfMonth());
        assertEquals(242, date1.getDayOfYear());
        assertEquals(DayOfWeek.WEDNESDAY, date1.getDayOfWeek());

        assertEquals(dateUTC.withYear(2024).withMonth(9).withDayOfMonth(10), date2);
        assertEquals(date2.withYear(2023).withDayOfYear(242), dateUTC);

        assertEquals(date2.minusYears(1).minusMonths(1).plusDays(20), dateUTC);
        assertEquals(dateUTC.plusYears(1).plusMonths(1).minusDays(20), date2);

        assertEquals(dateUTC.plusDays(14).minusWeeks(2), dateUTC);
        assertEquals(dateUTC.minusDays(14).plusWeeks(2), dateUTC);

        assertTrue(dateUTC.isEqual(dateUTC));
        assertTrue(date2.isAfter(date1));
        assertTrue(date1.isBefore(date2));
        assertTrue(dateUTC.isAfter(date1));
        assertTrue(date1.isBefore(dateUTC));
    }

    @Test
    public void testCompareTo() {
        final OffsetDate date = OffsetDate.of(LocalDate.of(2024, 9, 10), UTC);
        final OffsetDate date1 = OffsetDate.of(LocalDate.of(2024, 9, 10), UTC);
        final OffsetDate date2 = OffsetDate.of(LocalDate.of(2024, 9, 12), UTC);
        final OffsetDate date3 = OffsetDate.of(LocalDate.of(2024, 9, 10), ofHours(2));

        assertEquals(0, date.compareTo(date1));
        assertEquals(-2, date1.compareTo(date2));
        assertEquals(2, date2.compareTo(date1));

        assertEquals(1, date1.compareTo(date3));
        assertEquals(-1, date3.compareTo(date1));
    }

    @Test
    public void testFromTime() {
        final OffsetTime time = OffsetTime.of(2, 0, 0, 0, UTC);
        assertThrows(DateTimeException.class, () -> OffsetDate.from(time));
    }

    @Test
    public void testFrom() {
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);
        final LocalTime time = LocalTime.of(2, 0, 0);
        final OffsetDateTime dateTime = OffsetDateTime.of(local, time, UTC);

        assertEquals(date, OffsetDate.from(date));
        assertEquals(date, OffsetDate.from(dateTime));
        assertEquals(date, OffsetDate.from(dateTime.toZonedDateTime()));
    }

    @Test
    public void testWithTemporalAdjuster() {
        final LocalTime time = LocalTime.of(2, 0, 0);
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);

        assertEquals(date, date.with(local));
        assertEquals(date, date.with(UTC));
        assertEquals(date, date.with(OffsetDateTime.of(local, time, UTC).toInstant()));
        assertEquals(date, date.with(date));
    }

    @Test
    public void testPlusTemporalAmount() {
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);

        assertEquals(date.plusDays(1), date.plus(1, ChronoUnit.DAYS));
        assertEquals(date.plusMonths(1), date.plus(1, ChronoUnit.MONTHS));
        assertEquals(date.plusYears(1), date.plus(1, ChronoUnit.YEARS));
        assertEquals(date.plusDays(1), date.plus(Period.ofDays(1)));
    }

    @Test
    public void testMinusTemporalAmount() {
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);

        assertEquals(date.minusDays(1), date.minus(1, ChronoUnit.DAYS));
        assertEquals(date.minusMonths(1), date.minus(1, ChronoUnit.MONTHS));
        assertEquals(date.minusYears(1), date.minus(1, ChronoUnit.YEARS));
        assertEquals(date.minusDays(1), date.minus(Period.ofDays(1)));
    }

    @Test
    public void tesWithOffsetDateTimeAdjuster() {
        final LocalTime time = LocalTime.of(2, 0, 0);
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);
        final OffsetDateTime offset = OffsetDateTime.of(local, time, UTC);
        assertThrows(DateTimeException.class, () -> date.with(offset));
    }

    @Test
    public void testIsSupportedTemporalField() {
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);

        assertTrue(date.isSupported(ChronoField.DAY_OF_MONTH));
        assertTrue(date.isSupported(ChronoField.MONTH_OF_YEAR));
        assertTrue(date.isSupported(ChronoField.YEAR));
        assertTrue(date.isSupported(ChronoField.DAY_OF_WEEK));
        assertTrue(date.isSupported(ChronoField.DAY_OF_YEAR));
        assertTrue(date.isSupported(ChronoField.OFFSET_SECONDS));

        assertFalse(date.isSupported(ChronoField.INSTANT_SECONDS));
        assertFalse(date.isSupported(ChronoField.MINUTE_OF_HOUR));
        assertFalse(date.isSupported(ChronoField.HOUR_OF_DAY));
        assertFalse(date.isSupported((ChronoField) null));
    }

    @Test
    public void testGetTemporalField() {
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final ZoneOffset offset = ofHours(1);
        final OffsetDate date = OffsetDate.of(local, offset);

        assertEquals(10, date.get(ChronoField.DAY_OF_MONTH));
        assertEquals(9, date.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(2024, date.get(ChronoField.YEAR));
        assertEquals(2, date.get(ChronoField.DAY_OF_WEEK));
        assertEquals(254, date.get(ChronoField.DAY_OF_YEAR));
        assertEquals(3600, date.get(ChronoField.OFFSET_SECONDS));

        final long i = local.toEpochSecond(LocalTime.of(0, 0, 0, 0), offset);
        assertEquals(i, date.getLong(ChronoField.INSTANT_SECONDS));
    }

    @Test
    public void testRange() {
        final OffsetDate date = OffsetDate.of(LocalDate.of(2024, 9, 10), UTC);
        assertNotNull(date.range(ChronoField.DAY_OF_MONTH));
        assertNotNull(date.range(ChronoField.MONTH_OF_YEAR));
        assertNotNull(date.range(ChronoField.YEAR));
        assertNotNull(date.range(ChronoField.DAY_OF_WEEK));
        assertNotNull(date.range(ChronoField.DAY_OF_YEAR));
        assertNotNull(date.range(ChronoField.OFFSET_SECONDS));
    }

    @Test
    public void testQuery() {
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);

        assertEquals(UTC, date.query(TemporalQueries.offset()));
        assertEquals(UTC, date.query(TemporalQueries.zone()));
        assertEquals(local, date.query(TemporalQueries.localDate()));
        assertEquals(IsoChronology.INSTANCE, date.query(TemporalQueries.chronology()));
        assertEquals(ChronoUnit.DAYS, date.query(TemporalQueries.precision()));
        assertNull(date.query(TemporalQueries.zoneId()));
        assertNull(date.query(TemporalQueries.localTime()));
    }

    @Test
    public void testAdjustInto() {
        final LocalTime time = LocalTime.of(2, 0, 0);
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);
        final OffsetDateTime dateTime = OffsetDateTime.of(local, time, UTC);

        assertEquals(dateTime, date.adjustInto(dateTime));
    }

    @Test
    public void testUntil() {
        final LocalTime time = LocalTime.of(2, 0, 0);
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);
        final OffsetDateTime dateTime = OffsetDateTime.of(local, time, UTC);

        assertEquals(1L, date.until(dateTime.plusYears(1), ChronoUnit.YEARS));
        assertEquals(1L, date.until(dateTime.plusMonths(1), ChronoUnit.MONTHS));
        assertEquals(1L, date.until(dateTime.plusDays(1), ChronoUnit.DAYS));
    }

    @Test
    public void testFormat() {
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);

        assertEquals("2024-09-10Z", date.format(ISO_OFFSET_DATE));
        assertEquals("2024-09-10+01:00", date.withOffsetSameInstant(ofHours(1)).format(ISO_OFFSET_DATE));
    }

    @Test
    @SuppressWarnings("ALL")
    public void testHashCode() {
        final LocalDate local = LocalDate.of(2024, 9, 10);
        final OffsetDate date = OffsetDate.of(local, UTC);
        final OffsetDate date2 = OffsetDate.of(local, UTC);

        assertEquals(date, date2);
        assertEquals(date.hashCode(), date2.hashCode());

        assertFalse(date2.equals(local));
        assertEquals("2024-09-10Z", date.toString());
    }

}
