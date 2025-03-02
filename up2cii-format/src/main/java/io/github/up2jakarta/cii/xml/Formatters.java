package io.github.up2jakarta.cii.xml;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import static java.time.temporal.ChronoField.*;

@SuppressWarnings("unused")
public interface Formatters {

    // Default Zone Offset
    ZoneOffset DEFAULT_OFFSET = OffsetDateTime.now(ZoneId.systemDefault()).getOffset();

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

    // Simple Formatters
    DateTimeFormatter TP_YEAR = DateTimeFormatter.ofPattern("yyyy");
    DateTimeFormatter TP_LOCAL_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter TP_LOCAL_TIME = DateTimeFormatter.ofPattern("HHmmss");
    DateTimeFormatter TP_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    // Offset config
    String OFFSET_PATTERN = "+HHMM";
    String NO_OFFSET_TEXT = "+0000";
    //  Complex Formatters
    DateTimeFormatter TP_OFFSET_DATE_TIME = new DateTimeFormatterBuilder()
            .append(TP_LOCAL_DATE_TIME)
            .appendOffset(OFFSET_PATTERN, NO_OFFSET_TEXT)
            .toFormatter();
    DateTimeFormatter TP_OFFSET_TIME = new DateTimeFormatterBuilder()
            .append(TP_LOCAL_TIME)
            .appendOffset(OFFSET_PATTERN, NO_OFFSET_TEXT)
            .toFormatter();
    DateTimeFormatter TP_OFFSET_DATE = new DateTimeFormatterBuilder()
            .append(TP_LOCAL_DATE)
            .appendOffset(OFFSET_PATTERN, NO_OFFSET_TEXT)
            .toFormatter();
}
