package io.github.up2jakarta.test.format.standard;

import io.github.up2jakarta.cii.InvoiceValidator;
import io.github.up2jakarta.cii.format.standard.CrossIndustryInvoiceType;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.XConfigurationException;
import io.github.up2jakarta.xml.api.XValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.test.api.TestUtil.loadResource;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class InvoiceValidatorTest {

    @Autowired
    private XValidator<CrossIndustryInvoiceType> validator;

    @Test
    public void testNotFound() {
        final File xmlFile = new File("xml/boom.xml");
        assertThrows(FileNotFoundException.class, () -> validator.validate(xmlFile));
    }

    @Test
    public void testValid() throws IOException {
        final File xmlFile = loadResource("xml/ppf/UC1-01-Facture-formatCII.xml");
        List<? extends IValidationError> errors = validator.validate(xmlFile);
        assertEquals(0, errors.size());
    }

    @Test
    public void testEmptyFile() throws IOException {
        final File xmlFile = loadResource("xml/empty.xml");
        List<? extends IValidationError> errors = validator.validate(xmlFile);
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
        List<? extends IValidationError> errors = validator.validate(xmlFile);
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
        List<? extends IValidationError> errors = validator.validate(xmlFile);
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
        List<? extends IValidationError> errors = validator.validate(xmlFile);
        assertEquals(8, errors.size());
        {
            final IValidationError error = errors.getFirst();
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(61, error.getLineNumber());
            assertEquals(54, error.getLineOffset());
            assertEquals("ECE-R20: Unknown input [S1] for CodeList[MeasurementUnitCode].", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(1);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(65, error.getLineNumber());
            assertEquals(51, error.getLineOffset());
            assertEquals("ECE-R20: Unknown input [S1] for CodeList[MeasurementUnitCode].", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(2);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(85, error.getLineNumber());
            assertEquals(70, error.getLineOffset());
            assertEquals("ECE-1153: Unknown input [01] for CodeList[ReferenceCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(3);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(107, error.getLineNumber());
            assertEquals(54, error.getLineOffset());
            assertEquals("ECE-R20: Unknown input [B2] for CodeList[MeasurementUnitCode].", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(4);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(111, error.getLineNumber());
            assertEquals(51, error.getLineOffset());
            assertEquals("ECE-R20: Unknown input [B2] for CodeList[MeasurementUnitCode].", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(5);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(131, error.getLineNumber());
            assertEquals(70, error.getLineOffset());
            assertEquals("ECE-1153: Unknown input [01] for CodeList[ReferenceCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(6);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(177, error.getLineNumber());
            assertEquals(70, error.getLineOffset());
            assertEquals("ECE-1153: Unknown input [01] for CodeList[ReferenceCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(7);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(223, error.getLineNumber());
            assertEquals(70, error.getLineOffset());
            assertEquals("ECE-1153: Unknown input [01] for CodeList[ReferenceCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
        }
    }

    @Test
    public void testConfigClass() {
        assertThrows(XConfigurationException.class, () -> new InvoiceValidator<>(CodeList.class));
    }

}
