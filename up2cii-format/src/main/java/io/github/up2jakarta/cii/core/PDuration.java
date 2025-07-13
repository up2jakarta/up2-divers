package io.github.up2jakarta.cii.core;

import java.io.Serializable;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.*;

public class PDuration<T extends Temporal> implements TemporalAmount, Serializable {

    private static final List<TemporalUnit> UNITS = List.of(NANOS, MILLIS, SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS);

    // Times
    private final T startTime;
    private final T untilTime;
    // Differences
    private final Period period;
    private final Duration times;

    private PDuration(final T startTime, final T untilTime) {
        // Times
        this.startTime = Objects.requireNonNull(startTime, "startTime");
        this.untilTime = Objects.requireNonNull(untilTime, "untilTime");
        final Wrapper<Temporal> utw = new Wrapper<>(untilTime);
        // Period
        final int years = between(startTime, utw, YEARS);
        final int months = between(startTime, utw, MONTHS);
        final int days = between(startTime, utw, DAYS);
        this.period = Period.of(years, months, days);
        // Duration
        final int hours = between(startTime, utw, HOURS);
        final int minutes = between(startTime, utw, MINUTES);
        final int seconds = between(startTime, utw, SECONDS);
        final int nanos = between(startTime, utw, NANOS);
        this.times = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds).plusNanos(nanos);
    }

    public static <E extends Temporal> PDuration<E> of(E startTime, E untilTime) {
        return new PDuration<>(startTime, untilTime);
    }

    private static int between(Temporal start, Wrapper<Temporal> endExclusive, TemporalUnit tu) {
        final Temporal until = endExclusive.get();
        if (start.isSupported(tu) && until.isSupported(tu)) {
            final int diff = (int) start.until(until, tu);
            endExclusive.set(until.minus(diff, tu));
            return diff;
        }
        return 0;
    }

    @Override
    public long get(TemporalUnit unit) {
        return switch (unit) {
            case NANOS -> getNanos();
            case MILLIS -> getMillis();
            case SECONDS -> getSeconds();
            case MINUTES -> getMinutes();
            case HOURS -> getHours();
            case DAYS -> getDays();
            case MONTHS -> getMonths();
            case YEARS -> getYears();
            default -> throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        };
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return UNITS;
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        if (!period.isZero()) {
            temporal = period.addTo(temporal);
        }
        if (!times.isZero()) {
            temporal = times.addTo(temporal);
        }
        return temporal;
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        if (!period.isZero()) {
            temporal = period.subtractFrom(temporal);
        }
        if (!times.isZero()) {
            temporal = times.subtractFrom(temporal);
        }
        return temporal;
    }

    public Duration toDuration() {
        return times;
    }

    public Period toPeriod() {
        return period;
    }

    public T getStartTime() {
        return startTime;
    }

    public T getUntilTime() {
        return untilTime;
    }

    public int getYears() {
        return period.getYears();
    }

    public int getMonths() {
        return period.getMonths();
    }

    public int getDays() {
        return period.getDays();
    }

    public int getHours() {
        return times.toHoursPart();
    }

    public int getMinutes() {
        return times.toMinutesPart();
    }

    public int getSeconds() {
        return times.toSecondsPart();
    }

    public int getMillis() {
        return times.toMillisPart();
    }

    public int getNanos() {
        return times.toNanosPart();
    }

    public boolean isZero() {
        return period.isZero() && times.isZero();
    }

    public boolean isNegative() {
        if (period.isNegative()) {
            return true;
        } else if (period.isZero()) {
            return times.isNegative();
        }
        return false;
    }

    @Override
    public String toString() {
        if (period.isZero()) {
            return times.toString();
        } else if (times.isZero()) {
            return period.toString();
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(period);
        final int p = sb.length();
        sb.append(times);
        sb.deleteCharAt(p);
        return sb.toString();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        var that = (PDuration<?>) other;
        return Objects.equals(startTime, that.startTime) && Objects.equals(untilTime, that.untilTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, untilTime);
    }
}
