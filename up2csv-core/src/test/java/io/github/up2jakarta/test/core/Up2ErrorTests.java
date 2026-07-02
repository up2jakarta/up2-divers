package io.github.up2jakarta.test.core;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.core.hdl.FailureException;
import io.github.up2jakarta.csv.core.hdl.PropertyFailureException;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.TypeException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.lov.core.SafeAdapter;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.core.misc.DummyException;
import io.github.up2jakarta.test.core.misc.cvr.Test1Converter;
import io.github.up2jakarta.test.core.misc.ext.Dummy1Processor;
import io.github.up2jakarta.test.core.misc.ext.DummyConverter;
import io.github.up2jakarta.test.core.misc.map.Test1Exception;
import io.github.up2jakarta.test.core.misc.map.Test2Exception;
import io.github.up2jakarta.test.core.misc.prc.Test6Processor;
import io.github.up2jakarta.test.impl.InputCollector;
import io.github.up2jakarta.test.impl.InputError;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.AssertionFailureBuilder;
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
import java.util.Objects;

import static io.github.up2jakarta.test.core.BusinessTests.TU_MODULE;
import static io.github.up2jakarta.test.fmt.misc.Tests.*;
import static io.github.up2jakarta.test.impl.SegmentType.S00;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class Up2ErrorTests {

    static final String EX_CAUSE = DummyException.class.getName();
    static final String EX_EVENT = TypeException.class.getName();
    static final String EX_FAILURE = FailureException.class.getName();
    static final String EX_EVENT_FAILURE = PropertyFailureException.class.getName();
    static final String LOV_MODULE = name(CodeList.class.getModule());

    private final Up2Factory<TermType> factory;

    @Autowired
    Up2ErrorTests(Up2Factory<TermType> factory) {
        this.factory = factory;
    }

    public static String name(Module m) {
        if (m == null || m.getDescriptor() == null) {
            return null;
        }
        return m.getDescriptor().toNameAndVersion();
    }

    public static String name(String m, Class<?> c) {
        if (m == null) {
            return c.getName();
        }
        return m + '/' + c.getName();
    }

    @SafeVarargs
    public static <T> void assertIn(T actual, T... expected) {
        for (final T value : expected) {
            if (Objects.equals(actual, value)) {
                return;
            }
        }
        AssertionFailureBuilder.assertionFailure()
                .reason("not in")
                .expected(stream(expected).map(Objects::toString).collect(joining("], [", "[", "]")))
                .actual(actual)
                .buildAndThrow();
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
        final Up2Mapper<Test6Processor, ?> mapper = factory.mapper(Test6Processor.class);
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
        final Up2Mapper<Test1Exception, TermType> mapper = factory.mapper(Test1Exception.class);
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
        final Up2Mapper<Test2Exception, TermType> mapper = factory.mapper(Test2Exception.class);
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
        final Up2Mapper<Test1Exception, TermType> mapper = factory.mapper(Test1Exception.class);
        final InputRecord row = record(S00, "TN", "TND", "null");
        final InputCollector handler = new InputCollector(row);
        // Then
        mapper.map(row, handler);
        // THEN
        assertEquals(1, handler.toList().size());
        final InputError error = handler.toList().getFirst();
        final String cn = name(TU_MODULE, Dummy1Processor.class);
        assertTrace(error.getTrace(),
                "java.lang.NullPointerException: null message",
                "\t" + cn + ".process(Dummy1Processor.java:19)",
                "\t" + cn + ".process(Dummy1Processor.java:32)",
                "\t" + cn + ".process(Dummy1Processor.java:9)"
        );
    }

    @Test
    void testErrorTrace2() throws BeanException {
        // Given
        final Up2Mapper<Test1Converter, TermType> mapper = factory.mapper(Test1Converter.class);
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
        final Up2Mapper<Test1Converter, TermType> mapper = factory.mapper(Test1Converter.class);
        final InputRecord row = record(S00, "TND", "dummy");
        final InputCollector handler = new InputCollector(row);
        // Then
        mapper.map(row, handler);
        // THEN
        assertEquals(1, handler.toList().size());
        final InputError error = handler.toList().getFirst();
        assertTrace(error.getTrace(),
                EX_CAUSE + ": dummy wrapped message",
                "\t" + name(TU_MODULE, DummyConverter.class) + ".doParse(DummyConverter.java:23)",
                "\t" + name(TU_MODULE, DummyConverter.class) + ".doParse(DummyConverter.java:8)",
                "\t" + name(LOV_MODULE, SafeAdapter.class) + ".parse(SafeAdapter.java:73)",
                "Caused by java.lang.RuntimeException: NPE message",
                "\t" + name(TU_MODULE, Dummy1Processor.class) + ".process(Dummy1Processor.java:27)",
                "\t" + name(TU_MODULE, DummyConverter.class) + ".doParse(DummyConverter.java:21)",
                "\t" + name(TU_MODULE, DummyConverter.class) + ".doParse(DummyConverter.java:8)",
                "\t" + name(LOV_MODULE, SafeAdapter.class) + ".parse(SafeAdapter.java:73)"
        );
    }

}
