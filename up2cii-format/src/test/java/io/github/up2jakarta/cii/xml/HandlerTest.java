package io.github.up2jakarta.cii.xml;

import io.github.up2jakarta.cii.api.XValidationException;
import jakarta.xml.bind.helpers.NotIdentifiableEventImpl;
import jakarta.xml.bind.helpers.ValidationEventLocatorImpl;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

import static io.github.up2jakarta.csv.extension.SeverityType.*;
import static org.junit.jupiter.api.Assertions.*;

public class HandlerTest {

    @Test
    public void testSchemaHandlerFatalError() {
        // Given
        var handler = new SchemaErrorCollector();
        var exception = new SAXParseException("MSG", "P", "S", 1, 10);
        // When
        handler.handle(FATAL, exception);
        var errors = handler.getErrors();
        // Then
        assertNotNull(errors);
        assertEquals(1, errors.size());
        var error = errors.get(0);
        assertNotNull(error);
        assertEquals(exception, error.getLinkedException());
        assertEquals(FATAL, error.getSeverity());
        assertEquals("MSG", error.getMessage());
        assertEquals(10, error.getColumnNumber());
        assertEquals(1, error.getLineNumber());
    }

    @Test
    public void testSchemaHandlerError() {
        // Given
        var handler = new SchemaErrorCollector();
        var exception = new SAXParseException("MSG", "P", "S", 1, 10);
        // When
        handler.handle(ERROR, exception);
        var errors = handler.getErrors();
        // Then
        assertNotNull(errors);
        assertEquals(1, errors.size());
        var error = errors.get(0);
        assertNotNull(error);
        assertEquals(exception, error.getLinkedException());
        assertEquals(ERROR, error.getSeverity());
        assertEquals("MSG", error.getMessage());
        assertEquals(10, error.getColumnNumber());
        assertEquals(1, error.getLineNumber());
    }

    @Test
    public void testSchemaHandlerWarning() {
        // Given
        var handler = new SchemaErrorCollector();
        var exception = new SAXParseException("MSG", "P", "S", 1, 10);
        // When
        handler.handle(WARNING, exception);
        var errors = handler.getErrors();
        // Then
        assertNotNull(errors);
        assertEquals(1, errors.size());
        var error = errors.get(0);
        assertNotNull(error);
        assertEquals(exception, error.getLinkedException());
        assertEquals(WARNING, error.getSeverity());
        assertEquals("MSG", error.getMessage());
        assertEquals(10, error.getColumnNumber());
        assertEquals(1, error.getLineNumber());
    }

    @Test
    public void testSchemaHandleNull() {
        var handler = new SchemaErrorCollector();
        assertThrows(NullPointerException.class, () -> handler.handle(WARNING, null));
    }

    @Test
    public void testStrictFastHandler() {
        // Given
        var handler = FailFastHandler.STRICT_INSTANCE;
        var event = new NotIdentifiableEventImpl(0, "unexpected element", null, new NullPointerException());
        // When
        try {
            handler.handleEvent(event);
            fail("Should throw XValidationException");
        } catch (XValidationException ex) {
            assertTrue(true);
        }
        // Then
        assertNotNull(handler.getErrors());
        assertEquals(0, handler.getErrors().size());
    }

    @Test
    public void testLenientFastHandler() {
        // Given
        var handler = FailFastHandler.LENIENT_INSTANCE;
        var event = new NotIdentifiableEventImpl(0, "unexpected element", null, new NullPointerException());
        // When
        handler.handleEvent(event);
        // Then
        assertNotNull(handler.getErrors());
        assertEquals(0, handler.getErrors().size());
    }

    @Test
    public void testStrictSafeHandler() {
        // Given
        var npe = new NullPointerException();
        var handler = new FailSafeHandler(false);
        var exception = new SAXParseException("MSG", "P", "S", 1, 10);
        var locator = new ValidationEventLocatorImpl(exception);
        var warningEvent = new NotIdentifiableEventImpl(WARNING.getLevel(), "unexpected element", locator, npe);
        {
            // When
            handler.handleEvent(warningEvent);
            var errors = handler.getErrors();
            // Then
            assertNotNull(errors);
            assertEquals(1, errors.size());
            var error = errors.get(0);
            assertNotNull(error);
            assertEquals(WARNING, error.getSeverity());
            assertEquals(npe, error.getLinkedException());
            try {
                handler.throwValidationExceptionWhenError();
                fail("Should throw XValidationException");
            } catch (XValidationException ex) {
                assertTrue(true);
            }
        }
        {
            // When ADD again
            handler.handleEvent(warningEvent);
            var errors = handler.getErrors();
            // Then
            assertNotNull(errors);
            assertEquals(1, errors.size());
        }
        {
            // When ADD another error in the same (line, column)
            var errorEvent = new NotIdentifiableEventImpl(ERROR.getLevel(), "", locator, npe);
            handler.handleEvent(errorEvent);
            var errors = handler.getErrors();
            // Then
            assertEquals(1, errors.size());
            var error = errors.get(0);
            assertNotNull(error);
            assertEquals(ERROR, error.getSeverity());
            assertEquals(npe, error.getLinkedException());
        }
        {
            // When ADD another error in the same (line, column)
            var errorEvent = new NotIdentifiableEventImpl(FATAL.getLevel(), "", locator, npe);
            handler.handleEvent(errorEvent);
            var errors = handler.getErrors();
            // Then
            assertNotNull(errors);
            assertEquals(1, errors.size());
            var error = errors.get(0);
            assertNotNull(error);
            assertEquals(FATAL, error.getSeverity());
            assertEquals(npe, error.getLinkedException());
        }
    }

    @Test
    public void testLenientSafeHandler() {
        // Given
        var npe = new NullPointerException();
        var handler = new FailSafeHandler(true);
        var exception = new SAXParseException("MSG", "P", "S", 1, 10);
        var locator = new ValidationEventLocatorImpl(exception);
        var warningEvent = new NotIdentifiableEventImpl(ERROR.getLevel(), "unexpected element", locator, npe);
        // When
        handler.handleEvent(warningEvent);
        var errors = handler.getErrors();
        // Then
        assertNotNull(errors);
        assertEquals(1, errors.size());
        var error = errors.get(0);
        assertNotNull(error);
        assertEquals(WARNING, error.getSeverity());
        assertEquals(npe, error.getLinkedException());
        handler.throwValidationExceptionWhenError();
    }

}
