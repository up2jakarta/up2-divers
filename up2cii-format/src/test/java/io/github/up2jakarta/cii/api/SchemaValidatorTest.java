package io.github.up2jakarta.cii.api;

import io.github.up2jakarta.cii.SchemaValidator;
import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.csv.extension.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class SchemaValidatorTest {

    @Autowired
    private SchemaValidator validator;

    @Test
    public void testNotFound() {
        final File xmlFile = new File("xml/boom.xml");
        assertThrows(FileNotFoundException.class, () -> validator.validate(xmlFile));
    }

    @Test
    public void testValid() throws IOException {
        final File xmlFile = loadResource("xml/ppf/UC1-01-Facture-formatCII.xml");
        List<IValidationError> errors = validator.validate(xmlFile);
        assertEquals(0, errors.size());
    }

    @Test
    public void testEmptyFile() throws IOException {
        final File xmlFile = loadResource("xml/empty.xml");
        List<IValidationError> errors = validator.validate(xmlFile);
        assertEquals(1, errors.size());
        final IValidationError error = errors.get(0);
        assertEquals(SeverityType.FATAL, error.getSeverity());
        assertEquals(1, error.getLineNumber());
        assertEquals(1, error.getColumnNumber());
        assertNotNull(error.getMessage());
        assertNotNull(error.getLinkedException());
    }

    @Test
    public void testInvalid01() throws IOException {
        final File xmlFile = loadResource("xml/invalid1-formatCII.xml");
        List<IValidationError> errors = validator.validate(xmlFile);
        assertEquals(1, errors.size());
        final IValidationError error = errors.get(0);
        assertEquals(SeverityType.ERROR, error.getSeverity());
        assertEquals(5, error.getLineNumber());
        assertEquals(22, error.getColumnNumber());
        assertNotNull(error.getMessage());
        assertNotNull(error.getLinkedException());
    }

    @Test
    public void testInvalid02() throws IOException {
        final File xmlFile = loadResource("xml/invalid2-formatCII.xml");
        List<IValidationError> errors = validator.validate(xmlFile);
        assertEquals(1, errors.size());
        final IValidationError error = errors.get(0);
        assertEquals(SeverityType.FATAL, error.getSeverity());
        assertEquals(5, error.getLineNumber());
        assertEquals(21, error.getColumnNumber());
        assertNotNull(error.getMessage());
        assertNotNull(error.getLinkedException());
    }

    @Test
    public void testValid1ForAdapters() throws IOException {
        final File xmlFile = loadResource("xml/unece/valid1-formatCII.xml");
        List<IValidationError> errors = validator.validate(xmlFile);
        assertEquals(0, errors.size());
    }

    @Test
    public void testValid2ForAdapters() throws IOException {
        final File xmlFile = loadResource("xml/unece/valid2-formatCII.xml");
        List<IValidationError> errors = validator.validate(xmlFile);
        assertEquals(0, errors.size());
    }

    @Test
    public void testValid3ForAdapters() throws IOException {
        final File xmlFile = loadResource("xml/unece/valid3-formatCII.xml");
        List<IValidationError> errors = validator.validate(xmlFile);
        assertEquals(0, errors.size());
    }

    @Test
    public void testValid4ForAdapters() throws IOException {
        final File xmlFile = loadResource("xml/unece/valid4-formatCII.xml");
        List<IValidationError> errors = validator.validate(xmlFile);
        assertEquals(0, errors.size());
    }

}
