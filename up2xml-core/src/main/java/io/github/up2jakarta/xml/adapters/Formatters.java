package io.github.up2jakarta.xml.adapters;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import static java.time.temporal.ChronoField.*;

public interface Formatters {

    // ISO Local Time Formatter
    DateTimeFormatter ISO_LOCAL_TIME = new DateTimeFormatterBuilder()
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .optionalStart()
            .appendFraction(NANO_OF_SECOND, 0, 3, true)
            .toFormatter();

    // ISO Offset Time Formatter
    DateTimeFormatter ISO_OFFSET_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_TIME)
            .optionalStart()
            .appendOffsetId()
            .optionalEnd()
            .toFormatter();

    // ISO Local Date Formatter
    DateTimeFormatter ISO_LOCAL_DATE = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral('-')
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral('-')
            .appendValue(DAY_OF_MONTH, 2)
            .toFormatter();

    // ISO Offset Date Formatter
    DateTimeFormatter ISO_OFFSET_DATE = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .parseLenient()
            .optionalStart()
            .appendOffsetId()
            .optionalEnd()
            .parseStrict()
            .toFormatter();

    // ISO Local DateTime Formatter
    DateTimeFormatter ISO_LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral('T')
            .append(ISO_LOCAL_TIME)
            .toFormatter();


    // ISO Offset DateTime Formatter
    DateTimeFormatter ISO_OFFSET_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE_TIME)
            .parseLenient()
            .optionalStart()
            .appendOffsetId()
            .optionalEnd()
            .parseStrict()
            .toFormatter();

    static ZoneOffset defaultOffset() {
        return OffsetDateTime.now(ZoneId.systemDefault()).getOffset();
    }

}
