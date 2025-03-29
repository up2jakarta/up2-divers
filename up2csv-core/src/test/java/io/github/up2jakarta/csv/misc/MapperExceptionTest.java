package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.core.MapperFactory;
import io.github.up2jakarta.csv.impl.*;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.mapper.Test1Exception;
import io.github.up2jakarta.csv.test.bean.mapper.Test2Exception;
import io.github.up2jakarta.csv.test.bean.processor.Test6Processor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class MapperExceptionTest {

    private final ErrorCreator creator;
    private final MapperFactory<DataId> factory;

    @Autowired
    MapperExceptionTest(MapperFactory<DataId> factory, ErrorCreator creator) {
        this.factory = factory;
        this.creator = creator;
    }

    private void asserTraceEquals(String buffer, List<String> expected) {
        var lines = Arrays.asList(buffer.split(System.lineSeparator()));
        for (var pl : expected) {
            assertTrue(lines.contains(pl), pl);
        }
        assertFalse(lines.contains(""), "empty line");
    }

    @Test
    void testWithoutCauses() throws BeanException {
        // Given
        final Mapper<Test6Processor, ?> mapper = factory.build(Test6Processor.class);
        final List<String> expected = Arrays.asList(
                "io.github.up2jakarta.csv.misc.MapperException: io.github.up2jakarta.xml.codelist.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.xml.codelist.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.csv.test.ext.DummyException: dummy"
        );
        // Then
        final MapperException thrown = assertThrows(MapperException.class, () -> mapper.map("dummy"));
        // THEN
        {
            var buffer = "";
            final PrintStream systemError = System.err;
            synchronized (systemError) {
                final ByteArrayOutputStream aos = new ByteArrayOutputStream();
                System.setErr(new PrintStream(aos));
                thrown.printStackTrace();
                System.setErr(systemError);
                buffer = aos.toString(Charset.defaultCharset());
            }
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
        {
            final ByteArrayOutputStream aos = new ByteArrayOutputStream();
            {
                final PrintStream stream = new PrintStream(aos);
                thrown.printStackTrace(stream);
                stream.flush();
                stream.close();
            }
            var buffer = aos.toString(Charset.defaultCharset());
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
        {
            final ByteArrayOutputStream aos = new ByteArrayOutputStream();
            {
                final PrintWriter writer = new PrintWriter(aos);
                thrown.printStackTrace(writer);
                writer.flush();
                writer.close();
            }
            var buffer = aos.toString(Charset.defaultCharset());
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
    }

    @Test
    void testWithinCauses() throws BeanException {
        // Given
        final Mapper<Test1Exception, DataId> mapper = factory.build(Test1Exception.class);
        final List<String> expected = Arrays.asList(
                "io.github.up2jakarta.csv.misc.MapperException: io.github.up2jakarta.xml.codelist.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.xml.codelist.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Multiple events have been occurred:",
                "1) the data #[1] has warning: W001 - Unknown value [EURO] for CodeList[CountryCodeType]",
                "2) the data #[2] has error: E002 - Unknown value [USA] for CodeList[CurrencyCodeType]"
        );
        final InputRowEntity row = Tests.create(SegmentType.S00, "EURO", "USA", "dummy");
        final ErrorHandler handler = new ErrorHandler(row, creator);
        // Then
        final MapperException thrown = assertThrows(MapperException.class, () -> mapper.map(row, handler));
        // THEN
        {
            var buffer = "";
            final PrintStream systemError = System.err;
            synchronized (systemError) {
                final ByteArrayOutputStream aos = new ByteArrayOutputStream();
                System.setErr(new PrintStream(aos));
                thrown.printStackTrace();
                System.setErr(systemError);
                buffer = aos.toString(Charset.defaultCharset());
            }
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
        {
            final ByteArrayOutputStream aos = new ByteArrayOutputStream();
            {
                final PrintStream stream = new PrintStream(aos);
                thrown.printStackTrace(stream);
                stream.flush();
                stream.close();
            }
            var buffer = aos.toString(Charset.defaultCharset());
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
        {
            final ByteArrayOutputStream aos = new ByteArrayOutputStream();
            {
                final PrintWriter writer = new PrintWriter(aos);
                thrown.printStackTrace(writer);
                writer.flush();
                writer.close();
            }
            var buffer = aos.toString(Charset.defaultCharset());
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
    }

    @Test
    void testTrace() throws BeanException {
        // Given
        final Mapper<Test2Exception, DataId> mapper = factory.build(Test2Exception.class);
        final List<String> expected = Arrays.asList(
                "io.github.up2jakarta.csv.misc.MapperException: io.github.up2jakarta.xml.codelist.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.xml.codelist.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Multiple events have been occurred:",
                "1) the data #[0] has warning: W001 - java.lang.RuntimeException: EURO",
                "io.github.up2jakarta.xml.codelist.PropertyException: java.lang.RuntimeException: EURO",
                "java.lang.RuntimeException: EURO",
                "2) the data #[1] has error: E002 - java.lang.RuntimeException: USA",
                "io.github.up2jakarta.xml.codelist.PropertyException: java.lang.RuntimeException: USA",
                "java.lang.RuntimeException: USA"
        );
        final InputRowEntity row = Tests.create(SegmentType.S00, "EURO", "USA", "dummy");
        final ErrorHandler handler = new ErrorHandler(row, creator);
        // Then
        final MapperException thrown = assertThrows(MapperException.class, () -> mapper.map(row, handler));
        // THEN
        {
            var buffer = "";
            final PrintStream systemError = System.err;
            synchronized (systemError) {
                final ByteArrayOutputStream aos = new ByteArrayOutputStream();
                System.setErr(new PrintStream(aos));
                thrown.printStackTrace();
                System.setErr(systemError);
                buffer = aos.toString(Charset.defaultCharset());
            }
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
        {
            final ByteArrayOutputStream aos = new ByteArrayOutputStream();
            {
                final PrintStream stream = new PrintStream(aos);
                thrown.printStackTrace(stream);
                stream.flush();
                stream.close();
            }
            var buffer = aos.toString(Charset.defaultCharset());
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
        {
            final ByteArrayOutputStream aos = new ByteArrayOutputStream();
            {
                final PrintWriter writer = new PrintWriter(aos);
                thrown.printStackTrace(writer);
                writer.flush();
                writer.close();
            }
            var buffer = aos.toString(Charset.defaultCharset());
            assertNotNull(buffer);
            assertFalse(buffer.isEmpty());
            asserTraceEquals(buffer, expected);
        }
    }

}
