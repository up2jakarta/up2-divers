package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.core.AbstractFormatter;
import io.github.up2jakarta.cii.core.PDurationFormatter;
import io.github.up2jakarta.cii.core.TemporalFormatter;
import io.github.up2jakarta.cii.edi.adapters.TimePointFormatCodeAdapter;
import io.github.up2jakarta.xml.clv.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;
import java.time.*;
import java.time.temporal.Temporal;

import static io.github.up2jakarta.cii.CII.*;

/**
 * Based on UN/CEFACT 2379 : Date or time or period format code
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred2379.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("2379")
@Documented(value = "TimePointFormatCode", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "4.2", date = "2008-08-23")
@XmlJavaTypeAdapter(TimePointFormatCodeAdapter.class)
public enum TimePointFormatCodeType implements CodeList<TimePointFormatCodeType> {

    /**
     * Calendar date: C = Century ; Y = Year ; M = Month ; D = Day.
     */
    V_102("102", "CCYYMMDD", new TemporalFormatter<>(FORMATTER_LOCAL_DATE, LocalDate.class, LocalDate::from)),

    /**
     * Calendar date including time with minutes: C=Century; Y=Year; M=Month; D=Day; H=Hour; M=Minutes.
     */
    V_203("203", "CCYYMMDDHHMM", new TemporalFormatter<>(FORMATTER_LOCAL_DATE_TIME, LocalDateTime.class, LocalDateTime::from)),

    /**
     * Calendar date including time and time zone expressed in hours and minutes.
     * ZHHMM = time zone given as offset from Coordinated Universal Time (UTC).
     */
    V_205("205", "CCYYMMDDHHMMZHHMM", new TemporalFormatter<>(FORMATTER_OFFSET_DATE_TIME, OffsetDateTime.class, OffsetDateTime::from)),

    /**
     * A period of time specified by giving the start time followed by the end time (both expressed by hours
     * minutes and seconds). Data is to be transmitted as consecutive characters without hyphen.
     */
    V_502("502", "HHMMSS-HHMMSS", new PDurationFormatter<>(FORMATTER_LOCAL_TIME, LocalTime.class, LocalTime::from)),

    /**
     * Time with seconds and with Time Zone: H = Hour; M = Minute, S = Seconds, Z = leading
     * plus/minus sign, HHMM = difference to UTC in Hours and Minutes.
     */
    V_209("209", "HHMMSSZHHMM", new TemporalFormatter<>(FORMATTER_OFFSET_TIME, OffsetTime.class, OffsetTime::from)),

    /**
     * Calendar year including century: C = Century; Y = Year.
     */
    V_602("602", "CCYY", new TemporalFormatter<>(FORMATTER_YEAR, Year.class, Year::from)),
    ;

    private final AbstractFormatter<? extends Temporal, ?> formatter;
    private final String code;
    private final String name;

    <T extends Temporal> TimePointFormatCodeType(String code, String name, AbstractFormatter<T, ?> formatter) {
        this.code = code;
        this.name = name;
        this.formatter = formatter;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    public AbstractFormatter<? extends Temporal, ?> getFormatter() {
        return formatter;
    }

}
