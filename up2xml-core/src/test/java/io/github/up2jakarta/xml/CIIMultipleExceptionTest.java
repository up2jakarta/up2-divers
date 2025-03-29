package io.github.up2jakarta.xml;

import io.github.up2jakarta.xml.api.XMultipleException;
import io.github.up2jakarta.xml.api.XValidationException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CIIMultipleExceptionTest {

    private static final XValidationException CAUSE = new XValidationException(new NullPointerException("Cause"));
    private static final XValidationException ANOTHER = new XValidationException(new Exception("Another"));
    private static final List<XValidationException> CAUSES = Arrays.asList(CAUSE, ANOTHER);
    private static final XMultipleException EXCEPTION = new XMultipleException(CAUSE, CAUSES);

    private static final List<String> PRINTED_LINES = Arrays.asList(
            "Multiple exceptions have been occurred:",
            "1) " + XValidationException.class.getName() + ": java.lang.NullPointerException: Cause",
            "Caused by: java.lang.NullPointerException: Cause",
            "2) " + XValidationException.class.getName() + ": java.lang.Exception: Another",
            "Caused by: java.lang.Exception: Another"
    );

    private void asserTraceEquals(String buffer) {
        var lines = Arrays.asList(buffer.split(System.lineSeparator()));
        for (var pl : PRINTED_LINES) {
            assertTrue(lines.contains(pl));
        }
    }

    @Test
    public void testGetCause() {
        assertNotNull(EXCEPTION);
        assertEquals(CAUSE, EXCEPTION.getCause());
        assertArrayEquals(CAUSES.toArray(), EXCEPTION.getCauses().toArray());
    }

    @Test
    @SuppressWarnings("ALL")
    public void testDefaultPrintStackTrace() {
        var buffer = "";
        final PrintStream systemError = System.err;
        synchronized (systemError) {
            final ByteArrayOutputStream aos = new ByteArrayOutputStream();
            System.setErr(new PrintStream(aos));
            EXCEPTION.printStackTrace();
            System.setErr(systemError);
            buffer = aos.toString(Charset.defaultCharset());
        }
        assertNotNull(buffer);
        assertFalse(buffer.isEmpty());
        asserTraceEquals(buffer);
    }

    @Test
    public void testPrintStackTraceToPrintStream() {
        final ByteArrayOutputStream aos = new ByteArrayOutputStream();
        {
            final PrintStream stream = new PrintStream(aos);
            EXCEPTION.printStackTrace(stream);
            stream.flush();
            stream.close();
        }
        var buffer = aos.toString(Charset.defaultCharset());
        assertNotNull(buffer);
        assertFalse(buffer.isEmpty());
        asserTraceEquals(buffer);
    }

    @Test
    public void testPrintStackTraceToPrintWriter() {
        final ByteArrayOutputStream aos = new ByteArrayOutputStream();
        {
            final PrintWriter writer = new PrintWriter(aos);
            EXCEPTION.printStackTrace(writer);
            writer.flush();
            writer.close();
        }
        var buffer = aos.toString(Charset.defaultCharset());
        assertNotNull(buffer);
        assertFalse(buffer.isEmpty());
        asserTraceEquals(buffer);
    }
}
