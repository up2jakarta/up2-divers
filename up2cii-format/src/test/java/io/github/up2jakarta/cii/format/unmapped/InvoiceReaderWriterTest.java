package io.github.up2jakarta.cii.format.unmapped;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.util.List;

import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static io.github.up2jakarta.cii.format.unmapped.InvoiceReaderTest.READER;
import static io.github.up2jakarta.cii.format.unmapped.InvoiceValidatorTest.VALIDATOR;
import static io.github.up2jakarta.cii.format.unmapped.InvoiceWriterTest.WRITER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class InvoiceReaderWriterTest {

    @Autowired
    private TestUtil util;

    private void testReadAndWriteInvoice(String xmlFile) throws Exception {
        assertNotNull(xmlFile);
        util.assertInvoice(loadResource(xmlFile), VALIDATOR, READER, WRITER);
    }

    @Test
    public void testDOMInvalid() throws Exception {
        final File file = loadResource("xml/invalid2-formatCII.xml");
        assertThrows(SAXParseException.class, () -> util.parseDocument(file));
    }

    @Test
    public void testDOMValid() throws Exception {
        final File file = loadResource("xml/ppf/UC1-01-Facture-formatCII.xml");
        // Read
        final Document document = util.parseDocument(file);
        assertNotNull(document);
    }

    @Test
    public void testValid() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC1-01-Facture-formatCII.xml");
    }

    @Test
    public void testEmptyCII() {
        final List<IValidationError> errors = VALIDATOR.validate(new CrossIndustryInvoiceType());
        assertNotNull(errors);
        assertEquals(1, errors.size());
    }

    @Test
    public void testCII01() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC1-01-Facture-formatCII.xml");
    }

    @Test
    public void testCII03() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC5-01-Autofacturation-formatCII.xml");
    }

    @Test
    public void testCII04() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC5-04-Avoir-formatCII.xml");
    }

    @Test
    public void testCII05() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC2-01-Facture-formatCII.xml");
    }

    @Test
    public void testCII06() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC2-04-Rectificative-formatCII.xml");
    }

    @Test
    public void testCII07() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC1-05-Avoir-formatCII.xml");
    }

    @Test
    public void testCII08() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC6-01-Acompte-formatCII.xml");
    }

    @Test
    public void testCII09() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC1-10-Rectificative-formatCII.xml");
    }

    @Test
    public void testValid1ForAdapters() throws Exception {
        testReadAndWriteInvoice("xml/unece/valid1-formatCII.xml");
    }

    @Test
    public void testValid2ForAdapters() throws Exception {
        testReadAndWriteInvoice("xml/unece/valid2-formatCII.xml");
    }

    @Test
    public void testValid3ForAdapters() throws Exception {
        testReadAndWriteInvoice("xml/unece/valid3-formatCII.xml");
    }

    @Test
    public void testValid4ForAdapters() throws Exception {
        testReadAndWriteInvoice("xml/unece/valid4-formatCII.xml");
    }

}
