package io.github.up2jakarta.cii.core;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.*;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoField.*;
import static java.time.temporal.ChronoUnit.*;

public class Duration<T extends Temporal & Serializable> implements TemporalAmount, Serializable {

    public static final int MINUTES_PER_HOUR = 60;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int HOURS_PER_DAY = 24;
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    private static final List<TemporalUnit> UNITS = List.of(SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS);
    // Members
    private final T startTime;
    private final T endTime;
    private final int diffYears;
    private final int diffMonths;
    private final int diffDays;
    private final long diffSeconds;

    @SuppressWarnings("unchecked")
    private Duration(T start, T end) {
        this.startTime = Objects.requireNonNull(start, "start");
        this.endTime = Objects.requireNonNull(end, "end");

        if (start.isSupported(EPOCH_DAY)) {
            var p = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
            this.diffYears = p.getYears();
            this.diffMonths = p.getMonths();
            this.diffDays = p.getDays();

            start = (T) start.with(ChronoField.YEAR, endTime.get(ChronoField.YEAR));
            start = (T) start.with(ChronoField.MONTH_OF_YEAR, endTime.get(ChronoField.MONTH_OF_YEAR));
            start = (T) start.with(DAY_OF_MONTH, endTime.get(DAY_OF_MONTH));
        } else {
            if (startTime.isSupported(ChronoField.YEAR)) {
                this.diffYears = endTime.get(ChronoField.YEAR) - start.get(ChronoField.YEAR);
                start = (T) start.with(ChronoField.YEAR, endTime.get(ChronoField.YEAR));
            } else {
                this.diffYears = 0;
            }
            if (startTime.isSupported(ChronoField.MONTH_OF_YEAR)) {
                this.diffMonths = endTime.get(ChronoField.MONTH_OF_YEAR) - start.get(ChronoField.MONTH_OF_YEAR);
                start = (T) start.with(ChronoField.MONTH_OF_YEAR, endTime.get(ChronoField.MONTH_OF_YEAR));
            } else {
                this.diffMonths = 0;
            }
            if (startTime.isSupported(DAY_OF_MONTH)) {
                this.diffDays = endTime.get(DAY_OF_MONTH) - start.get(DAY_OF_MONTH);
                start = (T) start.with(DAY_OF_MONTH, endTime.get(DAY_OF_MONTH));
            } else {
                this.diffDays = 0;
            }
        }
        if (start.isSupported(SECOND_OF_MINUTE) || start.isSupported(INSTANT_SECONDS)) {
            this.diffSeconds = SECONDS.between(start, end);
        } else {
            this.diffSeconds = 0;
        }
    }

    public static <E extends Temporal & Serializable> Duration<E> of(E startTime, E endTime) {
        return new Duration<>(startTime, endTime);
    }

    @Override
    public long get(TemporalUnit unit) {
        if (unit == SECONDS) {
            return getSeconds();
        } else if (unit == MINUTES) {
            return getMinutes();
        } else if (unit == HOURS) {
            return getHours();
        } else if (unit == DAYS) {
            return getDays();
        } else if (unit == MONTHS) {
            return getMonths();
        } else if (unit == YEARS) {
            return getYears();
        } else {
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return UNITS;
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        if (diffSeconds != 0) {
            temporal = temporal.plus(diffSeconds, SECONDS);
        }
        if (diffDays != 0) {
            temporal = temporal.plus(diffDays, DAYS);
        }
        if (diffMonths != 0) {
            temporal = temporal.plus(diffMonths, MONTHS);
        }
        if (diffYears != 0) {
            temporal = temporal.plus(diffYears, YEARS);
        }
        return temporal;
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        if (diffSeconds != 0) {
            temporal = temporal.minus(diffSeconds, SECONDS);
        }
        if (diffDays != 0) {
            temporal = temporal.minus(diffDays, DAYS);
        }
        if (diffMonths != 0) {
            temporal = temporal.minus(diffMonths, MONTHS);
        }
        if (diffYears != 0) {
            temporal = temporal.minus(diffYears, YEARS);
        }
        return temporal;
    }

    public java.time.Duration getDuration() {
        return java.time.Duration.ofSeconds(diffSeconds);
    }

    public Period getPeriod() {
        return Period.of(diffYears, diffMonths, diffDays);
    }

    public T getStartTime() {
        return startTime;
    }

    public T getEndTime() {
        return endTime;
    }

    public int getYears() {
        return diffYears;
    }

    public int getMonths() {
        return diffMonths;
    }

    public int getDays() {
        return this.diffDays;
    }

    public int getHours() {
        return (int) ((diffSeconds / SECONDS_PER_HOUR) % HOURS_PER_DAY);
    }

    public int getMinutes() {
        return (int) ((diffSeconds / SECONDS_PER_MINUTE) % MINUTES_PER_HOUR);
    }

    public int getSeconds() {
        return (int) (diffSeconds % SECONDS_PER_MINUTE);
    }

    public boolean isZero() {
        return diffYears == 0 && diffMonths == 0 && diffDays == 0 && diffSeconds == 0;
    }

    public boolean isNegative() {
        if (diffYears < 0) {
            return true;
        }
        if (diffYears == 0 && diffMonths < 0) {
            return true;
        }
        if (diffYears == 0 && diffMonths == 0 && diffDays < 0) {
            return true;
        }
        return diffYears == 0 && diffMonths == 0 && diffDays == 0 && diffSeconds < 0;
    }

    @Override
    public String toString() {
        if (diffYears == 0 && diffMonths == 0 && diffDays == 0 && diffSeconds == 0) {
            return "PT0S";
        }
        var buf = new StringBuilder(64);
        buf.append('P');
        // Period Date
        if (diffYears != 0) {
            buf.append(diffYears).append('Y');
        }
        if (diffMonths != 0) {
            buf.append(diffMonths).append('M');
        }
        if (diffDays != 0) {
            buf.append(diffDays).append('D');
        }
        if (diffSeconds == 0) {
            return buf.toString();
        }
        buf.append("T");
        // Period Time
        int hours = getHours();
        int minutes = getMinutes();
        int seconds = getSeconds();
        if (hours != 0) {
            buf.append(hours).append('H');
        }
        if (minutes != 0) {
            buf.append(minutes).append('M');
        }
        if (seconds != 0) {
            buf.append(seconds).append('S');
        }
        return buf.toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        var that = (Duration<?>) other;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}
