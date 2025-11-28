package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.core.PDuration;
import io.github.up2jakarta.cii.core.PDurationFormatter;
import io.github.up2jakarta.cii.core.TemporalFormatter;
import io.github.up2jakarta.cii.format.standard.udt.DateStringType;
import io.github.up2jakarta.cii.format.standard.udt.DateTimeType;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.XValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class TimePointFormatCodeTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public TimePointFormatCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_time_point_format.xml", (i) -> {
            var issueDateTime = i.getExchangedDocument().getIssueDateTime();
            var dateTime = issueDateTime.getDateTimeString();
            dateTime.setFormat(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final DateTimeType issueDateTime = validInvoice.getExchangedDocument().getIssueDateTime();
        assertNotNull(issueDateTime);
        final DateStringType dateTime = issueDateTime.getDateTimeString();
        assertNotNull(dateTime);
        final TimePointFormatCodeType code = dateTime.getFormat();
        assertNotNull(dateTime.getValue());
        assertEquals(TimePointFormatCodeType.V_102, code);
        assertNotNull(code.getName());
    }

    @Test
    public void read() throws IOException {
        assertThrows(XValidationException.class, () -> reader.read(invalidInvoiceFile, false));
    }

    @Test
    public void validate() throws IOException {
        final List<IValidationError> errors = validator.validate(invalidInvoiceFile);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        {
            final IValidationError error = errors.getFirst();
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(15, error.getLineNumber());
            assertEquals(46, error.getLineOffset());
            assertEquals("ECE-2379: Unknown input [???] for CodeList[TimePointFormatCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

    @Test
    public void testParseLocalDate() {
        //When
        var code = TimePointFormatCodeType.V_102;
        var testString = "20230922";
        var testTemporal = LocalDate.of(2023, 9, 22);
        //Then
        var codeFormatter = code.getFormatter();
        assertNotNull(codeFormatter);
        assertEquals(TemporalFormatter.class, codeFormatter.getClass());
        assertEquals(LocalDate.class, codeFormatter.getTemporalClass());
        final TemporalFormatter<LocalDate> formatter = (TemporalFormatter<LocalDate>) codeFormatter;
        {
            var date = formatter.parse(testString);
            assertNotNull(date);
            assertEquals(LocalDate.class, date.getClass());
            assertEquals(testTemporal, date);
        }
        {
            var format = formatter.format(testTemporal);
            assertEquals(testString, format);
        }
    }

    @Test
    public void testParseLocalDateTime() {
        //When
        var code = TimePointFormatCodeType.V_203;
        var testString = "202309221530";
        var testTemporal = LocalDateTime.of(2023, 9, 22, 15, 30);
        //Then
        var codeFormatter = code.getFormatter();
        assertNotNull(codeFormatter);
        assertEquals(TemporalFormatter.class, codeFormatter.getClass());
        assertEquals(LocalDateTime.class, codeFormatter.getTemporalClass());
        final TemporalFormatter<LocalDateTime> formatter = (TemporalFormatter<LocalDateTime>) codeFormatter;
        {
            var date = formatter.parse(testString);
            assertNotNull(date);
            assertEquals(LocalDateTime.class, date.getClass());
            assertEquals(testTemporal, date);
        }
        {
            var format = formatter.format(testTemporal);
            assertEquals(testString, format);
        }
    }

    @Test
    public void testParseOffsetDateTime() {
        //When
        var code = TimePointFormatCodeType.V_205;

        var testString = "202309221530+0230";
        var testTemporal = OffsetDateTime.of(2023, 9, 22, 15, 30, 0, 0, ZoneOffset.ofHoursMinutes(2, 30));
        //Then
        var codeFormatter = code.getFormatter();
        assertNotNull(codeFormatter);
        assertEquals(TemporalFormatter.class, codeFormatter.getClass());
        assertEquals(OffsetDateTime.class, codeFormatter.getTemporalClass());
        final TemporalFormatter<OffsetDateTime> formatter = (TemporalFormatter<OffsetDateTime>) codeFormatter;
        {
            var date = formatter.parse(testString);
            assertNotNull(date);
            assertEquals(OffsetDateTime.class, date.getClass());
            assertEquals(testTemporal, date);
        }
        {
            var format = formatter.format(testTemporal);
            assertEquals(testString, format);
        }
    }

    @Test
    public void testZOffsetDateTime() {
        final Executable test = () -> TimePointFormatCodeType.V_205.getFormatter().parse("202309221530Z");
        assertThrows(DateTimeException.class, test);
    }


    @Test
    public void testParseOffsetTime() {
        //When
        var code = TimePointFormatCodeType.V_209;
        var testString = "153005+0230";
        var testTemporal = OffsetTime.of(15, 30, 5, 0, ZoneOffset.ofHoursMinutes(2, 30));
        //Then
        var codeFormatter = code.getFormatter();
        assertNotNull(codeFormatter);
        assertEquals(TemporalFormatter.class, codeFormatter.getClass());
        assertEquals(OffsetTime.class, codeFormatter.getTemporalClass());
        final TemporalFormatter<OffsetTime> formatter = (TemporalFormatter<OffsetTime>) codeFormatter;
        {
            var date = formatter.parse(testString);
            assertNotNull(date);
            assertEquals(OffsetTime.class, date.getClass());
            assertEquals(testTemporal, date);
        }
        {
            var format = formatter.format(testTemporal);
            assertEquals(testString, format);
        }
    }

    @Test
    public void testZOffsetTime() {
        final Executable test = () -> TimePointFormatCodeType.V_209.getFormatter().parse("153005Z");
        assertThrows(DateTimeException.class, test);
    }

    @Test
    public void testLOffsetTime() {
        final Executable test = () -> TimePointFormatCodeType.V_209.getFormatter().parse("153005");
        assertThrows(DateTimeException.class, test);
    }

    @Test
    public void testParseYear() {
        //When
        var code = TimePointFormatCodeType.V_602;
        var testString = "2023";
        var testTemporal = Year.of(2023);
        //Then
        var codeFormatter = code.getFormatter();
        assertNotNull(codeFormatter);
        assertEquals(TemporalFormatter.class, codeFormatter.getClass());
        assertEquals(Year.class, codeFormatter.getTemporalClass());
        final TemporalFormatter<Year> formatter = (TemporalFormatter<Year>) codeFormatter;
        {
            var date = formatter.parse(testString);
            assertNotNull(date);
            assertEquals(Year.class, date.getClass());
            assertEquals(testTemporal, date);
        }
        {
            var format = formatter.format(testTemporal);
            assertEquals(testString, format);
        }
    }

    @Test
    public void testParseDurationTime() {
        //When
        var code = TimePointFormatCodeType.V_502;
        var startTime = LocalTime.of(15, 30, 5);
        var endTime = LocalTime.of(17, 40, 10);
        var testString = "153005-174010";
        var testTemporal = PDuration.of(startTime, endTime);
        //Then
        var codeFormatter = code.getFormatter();
        assertNotNull(codeFormatter);
        assertEquals(PDurationFormatter.class, codeFormatter.getClass());
        assertEquals(LocalTime.class, codeFormatter.getTemporalClass());
        final PDurationFormatter<LocalTime> formatter = (PDurationFormatter<LocalTime>) codeFormatter;
        {
            var duration = formatter.parse(testString);
            assertNotNull(duration);
            assertEquals(PDuration.class, duration.getClass());
            assertEquals(LocalTime.class, duration.getStartTime().getClass());
            assertEquals(LocalTime.class, duration.getUntilTime().getClass());
            assertEquals(testTemporal, duration);
        }
        {
            var format = formatter.format(testTemporal);
            assertEquals(testString, format);
        }
    }

    @Test
    public void testStartDurationTime() {
        final Executable test = () -> TimePointFormatCodeType.V_502.getFormatter().parse("530-174010");
        assertThrows(DateTimeException.class, test);
    }

    @Test
    public void testEndDurationTime() {
        final Executable test = () -> TimePointFormatCodeType.V_502.getFormatter().parse("53000-1740");
        assertThrows(DateTimeException.class, test);
    }

    @Test
    public void testSeparatorDurationTime() {
        final Executable test = () -> TimePointFormatCodeType.V_502.getFormatter().parse("153005 - 174010");
        assertThrows(DateTimeException.class, test);
    }

}
