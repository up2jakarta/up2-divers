package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.core.hdl.PropertyFailureException;
import io.github.up2jakarta.csv.core.misc.DummyException;
import io.github.up2jakarta.csv.core.misc.cvr.Test1Converter;
import io.github.up2jakarta.csv.core.misc.ext.Dummy1Processor;
import io.github.up2jakarta.csv.core.misc.ext.DummyConverter;
import io.github.up2jakarta.csv.core.misc.map.Test1Exception;
import io.github.up2jakarta.csv.core.misc.map.Test2Exception;
import io.github.up2jakarta.csv.core.misc.prc.Test6Processor;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputCollector;
import io.github.up2jakarta.csv.impl.InputError;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.SafeAdapter;
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

import static io.github.up2jakarta.csv.fmt.misc.Tests.*;
import static io.github.up2jakarta.csv.impl.SegmentType.S00;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class Up2ErrorTests {

    static final String EX_CAUSE = DummyException.class.getName();
    static final String EX_EVENT = TypeException.class.getName();
    static final String EX_FAILURE = FailureException.class.getName();
    static final String EX_EVENT_FAILURE = PropertyFailureException.class.getName();

    private final Up2Factory<GroupType> factory;

    @Autowired
    Up2ErrorTests(Up2Factory<GroupType> factory) {
        this.factory = factory;
    }

    static void assertTrace(String buffer, String... expected) {
        final List<String> lines = Arrays.asList(buffer.split(System.lineSeparator()));
        assertEquals(expected.length, lines.size());
        for (var i = 0; i < expected.length; i++) {
            assertEquals(expected[i], lines.get(i));
        }
    }

    private void assertContains(String buffer, List<String> expected) {
        List<String> lines = Arrays.asList(buffer.split(System.lineSeparator()));
        for (var pl : expected) {
            assertTrue(lines.contains(pl), pl);
        }
        assertFalse(lines.contains(""), "empty line");
    }

    @Test
    void testWithoutCauses() throws BeanException {
        // Given
        final Up2Mapper<Test6Processor, ?> mapper = factory.build(Test6Processor.class);
        final List<String> expected = Arrays.asList(
                EX_FAILURE + ": #[1] throws #[UP2-P001] " + EX_CAUSE + ": dummy message",
                "Caused by: " + EX_CAUSE + ": dummy message"
        );
        // Then
        final FailureException thrown = assertThrows(FailureException.class, () -> mapper.map("dummy"));
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
        final Up2Mapper<Test1Exception, GroupType> mapper = factory.build(Test1Exception.class);
        final List<String> expected = Arrays.asList(
                EX_EVENT_FAILURE + ": #[3] throws #[F003] dummy message",
                "Caused by: " + EX_EVENT + ": #[F003] dummy message",
                "Caused by: " + EX_CAUSE + ": dummy message",
                "Multiple events have been occurred:",
                "1) #[1] has warning #[W001] Unknown input [EURO] for CodeList[CountryCodeType]",
                "2) #[2] has error #[E002] Unknown input [USA] for CodeList[CurrencyCodeType]"
        );
        final TURecord row = new TURecord(S00, null, "EURO", "USA", "dummy");
        final TUCollector handler = new TUCollector(row);
        // Then
        final PropertyFailureException thrown = assertThrows(PropertyFailureException.class, () -> mapper.map(row, handler));
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
        final Up2Mapper<Test2Exception, GroupType> mapper = factory.build(Test2Exception.class);
        final List<String> expected = Arrays.asList(
                EX_EVENT_FAILURE + ": #[2] throws #[F003] dummy message",
                "Caused by: " + EX_EVENT + ": #[F003] dummy message",
                "Caused by: " + EX_CAUSE + ": dummy message",
                "Multiple events have been occurred:",
                "1) #[0] has warning #[W001] EURO message",
                "java.lang.RuntimeException: EURO message",
                "2) #[1] has error #[E002] USA message",
                "java.lang.RuntimeException: USA message"
        );
        final TURecord row = new TURecord(S00, null, "EURO", "USA", "dummy");
        final TUCollector handler = new TUCollector(row);
        // Then
        final PropertyFailureException thrown = assertThrows(PropertyFailureException.class, () -> mapper.map(row, handler));
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
        final Up2Mapper<Test1Exception, GroupType> mapper = factory.build(Test1Exception.class);
        final InputRecord row = record(S00, "TN", "TND", "null");
        final InputCollector handler = new InputCollector(row);
        // Then
        mapper.map(row, handler);
        // THEN
        assertEquals(1, handler.toList().size());
        final InputError error = handler.toList().getFirst();
        assertTrace(error.getTrace(),
                "java.lang.NullPointerException: null message",
                "\t" + Dummy1Processor.class.getName() + ".process(Dummy1Processor.java:21)",
                "\t" + Dummy1Processor.class.getName() + ".process(Dummy1Processor.java:33)",
                "\t" + Dummy1Processor.class.getName() + ".process(Dummy1Processor.java:10)"
        );
    }

    @Test
    void testErrorTrace2() throws BeanException {
        // Given
        final Up2Mapper<Test1Converter, GroupType> mapper = factory.build(Test1Converter.class);
        final InputRecord row = record(S00, "USD");
        final InputCollector handler = new InputCollector(row);
        // Then
        mapper.map(row, handler);
        // THEN
        assertEquals(1, handler.toList().size());
        final InputError error = handler.toList().getFirst();
        assertEquals("Unknown input [USD] for CodeList[CurrencyCodeType]", error.getMessage());
        assertNull(error.getTrace());
    }

    @Test
    void testErrorTrace3() throws BeanException {
        // Given
        final Up2Mapper<Test1Converter, GroupType> mapper = factory.build(Test1Converter.class);
        final InputRecord row = record(S00, "TND", "dummy");
        final InputCollector handler = new InputCollector(row);
        // Then
        mapper.map(row, handler);
        // THEN
        assertEquals(1, handler.toList().size());
        final InputError error = handler.toList().getFirst();
        assertTrace(error.getTrace(),
                EX_CAUSE + ": dummy wrapped message",
                "\t" + DummyConverter.class.getName() + ".doParse(DummyConverter.java:23)",
                "\t" + DummyConverter.class.getName() + ".doParse(DummyConverter.java:8)",
                "\t" + SafeAdapter.class.getName() + ".parse(SafeAdapter.java:73)",
                "Caused by java.lang.RuntimeException: NPE message",
                "\t" + Dummy1Processor.class.getName() + ".process(Dummy1Processor.java:29)",
                "\t" + DummyConverter.class.getName() + ".doParse(DummyConverter.java:21)",
                "\t" + DummyConverter.class.getName() + ".doParse(DummyConverter.java:8)",
                "\t" + SafeAdapter.class.getName() + ".parse(SafeAdapter.java:73)"
        );
    }

}
