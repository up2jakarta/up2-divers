package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.impl.FastCollector;
import io.github.up2jakarta.csv.impl.FastException;
import io.github.up2jakarta.csv.impl.FatalException;
import io.github.up2jakarta.csv.ops.impl.*;
import io.github.up2jakarta.csv.test.Tests;
import io.github.up2jakarta.csv.test.bean.converter.Test1Converter;
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

    private final SimpleCreator creator;
    private final MapperFactory<GroupType> factory;

    @Autowired
    MapperExceptionTest(MapperFactory<GroupType> factory, SimpleCreator creator) {
        this.factory = factory;
        this.creator = creator;
    }

    private void assertContains(String buffer, List<String> expected) {
        List<String> lines = Arrays.asList(buffer.split(System.lineSeparator()));
        for (var pl : expected) {
            assertTrue(lines.contains(pl), pl);
        }
        assertFalse(lines.contains(""), "empty line");
    }

    private void assertTrace(String buffer, String... expected) {
        final List<String> lines = Arrays.asList(buffer.split(System.lineSeparator()));
        assertEquals(expected.length, lines.size());
        for (var i = 0; i < expected.length; i++) {
            assertEquals(expected[i], lines.get(i));
        }
    }

    @Test
    void testWithoutCauses() throws BeanException {
        // Given
        final Mapper<Test6Processor, ?> mapper = factory.build(Test6Processor.class);
        final List<String> expected = Arrays.asList(
                "io.github.up2jakarta.csv.impl.FastException: io.github.up2jakarta.xml.clv.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.xml.clv.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.csv.test.ext.DummyException: dummy"
        );
        // Then
        final FastException thrown = assertThrows(FastException.class, () -> mapper.map("dummy"));
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
            assertContains(buffer, expected);
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
            assertContains(buffer, expected);
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
            assertContains(buffer, expected);
        }
    }

    @Test
    void testWithinCauses() throws BeanException {
        // Given
        final Mapper<Test1Exception, GroupType> mapper = factory.build(Test1Exception.class);
        final List<String> expected = Arrays.asList(
                "io.github.up2jakarta.csv.impl.FatalException: io.github.up2jakarta.xml.clv.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Multiple events have been occurred:",
                "1) the data #[1] has warning: W001 - Unknown value [EURO] for CodeList[CountryCodeType]",
                "2) the data #[2] has error: E002 - Unknown value [USA] for CodeList[CurrencyCodeType]"
        );
        final InputRowEntity row = Tests.create(SegmentType.S00, "EURO", "USA", "dummy");
        final StackHandler handler = new StackHandler(row, creator);
        // Then
        final FatalException thrown = assertThrows(FatalException.class, () -> mapper.map(row, handler));
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
            assertContains(buffer, expected);
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
            assertContains(buffer, expected);
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
            assertContains(buffer, expected);
        }
    }

    @Test
    void testStackTrace() throws BeanException {
        // Given
        final Mapper<Test2Exception, GroupType> mapper = factory.build(Test2Exception.class);
        final List<String> expected = Arrays.asList(
                "io.github.up2jakarta.csv.impl.FatalException: io.github.up2jakarta.xml.clv.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.xml.clv.PropertyException: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Caused by: io.github.up2jakarta.csv.test.ext.DummyException: dummy",
                "Multiple events have been occurred:",
                "1) the data #[0] has warning: W001 - java.lang.RuntimeException: EURO",
                "java.lang.RuntimeException: EURO",
                "2) the data #[1] has error: E002 - java.lang.RuntimeException: USA",
                "java.lang.RuntimeException: USA"
        );
        final InputRowEntity row = Tests.create(SegmentType.S00, "EURO", "USA", "dummy");
        final StackHandler handler = new StackHandler(row, creator);
        // Then
        final FatalException thrown = assertThrows(FatalException.class, () -> mapper.map(row, handler));
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
            assertContains(buffer, expected);
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
            assertContains(buffer, expected);
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
            assertContains(buffer, expected);
        }
    }

    @Test
    void testErrorTrace1() throws BeanException {
        // Given
        final Mapper<Test1Exception, GroupType> mapper = factory.build(Test1Exception.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "TN", "TND");
        final SimpleHandler handler = new SimpleHandler(row, creator);
        // Then
        mapper.map(row, handler);
        // THEN
        assertEquals(1, handler.toCollection().size());
        final InputErrorEntity error = handler.toCollection().iterator().next();
        assertTrace(error.getTrace(),
                "java.lang.NullPointerException: NPE",
                "\tio.github.up2jakarta.csv.test.ext.Dummy1Processor.process(Dummy1Processor.java:17)",
                "\tio.github.up2jakarta.csv.test.ext.Dummy1Processor.process(Dummy1Processor.java:29)",
                "\tio.github.up2jakarta.csv.test.ext.Dummy1Processor.process(Dummy1Processor.java:9)"
        );

    }

    @Test
    void testErrorTrace2() throws BeanException {
        // Given
        final Mapper<Test1Converter, GroupType> mapper = factory.build(Test1Converter.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "USD");
        final SimpleHandler handler = new SimpleHandler(row, creator);
        // Then
        mapper.map(row, handler);
        // THEN
        assertEquals(1, handler.toCollection().size());
        final InputErrorEntity error = handler.toCollection().iterator().next();
        assertEquals("Unknown value [USD] for CodeList[CurrencyCodeType]", error.getMessage());
        assertNull(error.getTrace());

    }

    @Test
    void testErrorTrace3() throws BeanException {
        // Given
        final Mapper<Test1Converter, GroupType> mapper = factory.build(Test1Converter.class);
        final InputRowEntity row = Tests.create(SegmentType.S00, "TND", "dummy");
        final SimpleHandler handler = new SimpleHandler(row, creator);
        // Then
        mapper.map(row, handler);
        // THEN
        assertEquals(1, handler.toCollection().size());
        final InputErrorEntity error = handler.toCollection().iterator().next();
        assertTrace(error.getTrace(),
                "io.github.up2jakarta.csv.test.ext.DummyException: Dummy message",
                "\tio.github.up2jakarta.csv.test.ext.DummyConverter.parse(DummyConverter.java:23)",
                "\tio.github.up2jakarta.csv.test.ext.DummyConverter.parse(DummyConverter.java:7)",
                "\tio.github.up2jakarta.csv.api.ext.PropertyConverter.lambda$of$0(PropertyConverter.java:42)",
                "java.lang.RuntimeException: NPE"
        );

    }

    private static class StackHandler extends FastCollector<InputRowEntity, GroupType, InputErrorEntity> {

        public StackHandler(InputRowEntity row, SimpleCreator creator) {
            super(row, creator);
        }

    }

}
