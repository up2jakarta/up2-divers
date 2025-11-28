package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.core.BeanContext;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Flatter;
import io.github.up2jakarta.csv.data.DataResolver;
import io.github.up2jakarta.csv.data.DynamicType;
import io.github.up2jakarta.csv.fmt.FullError;
import io.github.up2jakarta.csv.fmt.FullRecord;
import io.github.up2jakarta.csv.io.impl.SegmentType;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import static io.github.up2jakarta.csv.io.impl.SegmentType.S01;
import static io.github.up2jakarta.csv.io.misc.Tests.TUGenerator.path;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SingleErrorTests {

    private static final int MIN = 10;
    private static final int MAX = 99;

    private static final String MSG = "Text cannot be parsed to a LocalDate";
    private static final String TRACE = "java.time.format.DateTimeParseException: " + MSG + " ...";

    private final SingleWriter<TSError, DynamicType> writer;
    private final CSVFormat format;

    @Autowired
    SingleErrorTests(BeanContext context, CSVFormat fmt) throws BeanException {
        final Up2Factory<DynamicType> factory = new Up2Factory<>(context, DataResolver.dynamic());
        final Up2Flatter<TSError, DynamicType> format = factory.format(TSError.class);
        this.writer = new SingleWriter<>(format, fmt);
        this.format = fmt;
    }

    private void write(File file) throws IOException {
        try {
            writer.open(file);
            for (var i = MIN; i <= MAX; i++) {
                final TSRecord row = new TSRecord("R" + i, S01, "I2025N" + i, "");
                final TSError error = new TSError(row, i, "DT-" + i);
                writer.write(error);
                writer.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            writer.close();
        }
    }

    private void assertError(int i, String[] error) {
        assertEquals(9, error.length);
        assertEquals("R" + i, error[0]);
        assertEquals("01", error[1]);
        assertEquals("I2025N" + i, error[2]);
        assertNull(error[3]);
        assertEquals("3", error[4]);
        assertEquals("E", error[5]);
        assertEquals("DT-" + i, error[6]);
        assertEquals(MSG, error[7]);
        assertEquals(TRACE, error[8]);
    }

    private int read(File file, String... header) throws IOException {
        try (final CSVParser parser = format.parse(new FileReader(file, UTF_8))) {
            final Iterator<CSVRecord> iterator = parser.iterator();
            // Checking Header
            assertTrue(iterator.hasNext());
            final String[] first = iterator.next().values();
            assertEquals(9, first.length);
            assertArrayEquals(header, first);
            // Checking errors
            var i = MIN;
            while (iterator.hasNext()) {
                this.assertError(i++, iterator.next().values());
            }
            return i;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            writer.close();
        }
    }

    @Test
    void testReader() throws IOException {
        // GIVEN
        final File file = path(this.getClass()).resolve("errors.csv").toFile();
        // WHEN Generating file
        this.write(file);
        // THEN
        var i = read(file, "Record", "Type", "Pivot", "Data", "Offset", "Level", "Code", "Message", "Stack");
        // Checking number of record
        assertEquals(MAX, i - 1);
    }

    public static class TSError extends FullError<DynamicType, String, TSRecord> {
        public TSError(TSRecord row, int order, String code) {
            super(row, order, null, 3, new TypeException(ERROR, code, MSG), TRACE);
        }
    }

    public static class TSRecord extends FullRecord<SegmentType, String> {
        public TSRecord(String rowKey, SegmentType type, String invoiceNumber, String... data) {
            super(rowKey, type, invoiceNumber, data);
        }
    }

}
