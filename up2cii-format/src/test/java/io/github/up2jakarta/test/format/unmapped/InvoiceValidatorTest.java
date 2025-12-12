package io.github.up2jakarta.test.format.unmapped;

import io.github.up2jakarta.cii.InvoiceValidator;
import io.github.up2jakarta.cii.format.unmapped.CrossIndustryInvoiceType;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.XValidator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.test.api.TestUtil.loadResource;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceValidatorTest {

    static final XValidator<CrossIndustryInvoiceType> VALIDATOR;

    static {
        VALIDATOR = new InvoiceValidator<>(CrossIndustryInvoiceType.class);
    }

    @Test
    public void testNotFound() {
        final File xmlFile = new File("xml/boom.xml");
        assertThrows(FileNotFoundException.class, () -> VALIDATOR.validate(xmlFile));
    }

    @Test
    public void testValid() throws IOException {
        final File xmlFile = loadResource("xml/ppf/UC1-01-Facture-formatCII.xml");
        List<? extends IValidationError> errors = VALIDATOR.validate(xmlFile);
        assertEquals(0, errors.size());
    }

    @Test
    public void testEmptyFile() throws IOException {
        final File xmlFile = loadResource("xml/empty.xml");
        List<? extends IValidationError> errors = VALIDATOR.validate(xmlFile);
        assertEquals(1, errors.size());
        final IValidationError error = errors.getFirst();
        assertEquals(SeverityType.FATAL, error.getLevel());
        assertEquals(1, error.getLineNumber());
        assertEquals(1, error.getLineOffset());
        assertEquals("Premature end of file.", error.getMessage());
        assertNotNull(error.getLinkedException());
    }

    @Test
    public void testInvalid01() throws IOException {
        final File xmlFile = loadResource("xml/invalid1-formatCII.xml");
        List<? extends IValidationError> errors = VALIDATOR.validate(xmlFile);
        assertEquals(1, errors.size());
        {
            final IValidationError error = errors.getFirst();
            assertEquals(SeverityType.FATAL, error.getLevel());
            assertEquals(5, error.getLineNumber());
            assertEquals(22, error.getLineOffset());
            assertEquals("cvc-complex-type.2.4.a: Invalid content was found starting with element '{ram:UID}'. One of '{ram:ID, ram:Value, ram:SpecifiedDocumentVersion}' is expected.", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
    }

    @Test
    public void testInvalid02() throws IOException {
        final File xmlFile = loadResource("xml/invalid2-formatCII.xml");
        List<? extends IValidationError> errors = VALIDATOR.validate(xmlFile);
        assertEquals(1, errors.size());
        {
            final IValidationError error = errors.getFirst();
            assertEquals(SeverityType.FATAL, error.getLevel());
            assertEquals(5, error.getLineNumber());
            assertEquals(21, error.getLineOffset());
            assertEquals("The prefix \"bla\" for element \"bla:ID\" is not bound.", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
    }

    @Test
    public void testInvalid03() throws IOException {
        final File xmlFile = loadResource("xml/ppf/UC4-01-FactureCII.xml");
        List<? extends IValidationError> errors = VALIDATOR.validate(xmlFile);
        assertEquals(0, errors.size());
    }
}
