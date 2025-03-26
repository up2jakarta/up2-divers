package io.github.up2jakarta.cii.format.standard;

import io.github.up2jakarta.cii.InvoiceWriter;
import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.TestUtil;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.XReader;
import io.github.up2jakarta.xml.api.XValidator;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class InvoiceReaderWriterTest {

    @Autowired
    private XValidator<CrossIndustryInvoiceType> validator;
    @Autowired
    private InvoiceWriter<CrossIndustryInvoiceType> writer;
    @Autowired
    private XReader<CrossIndustryInvoiceType> reader;
    @Autowired
    private TestUtil util;

    private void testReadAndWriteInvoice(String xmlFile) throws Exception {
        assertNotNull(xmlFile);
        util.assertInvoice(loadResource(xmlFile), validator, reader, writer);
    }

    @Test
    public void testDOMInvalid() throws Exception {
        final File file = loadResource("xml/invalid2-formatCII.xml");
        assertThrows(SAXParseException.class, () -> writer.newDocument(file));
    }

    @Test
    public void testDOMValid() throws Exception {
        final File file = loadResource("xml/ppf/UC1-01-Facture-formatCII.xml");
        // Read
        final Document document = writer.newDocument(file);
        assertNotNull(document);
    }

    @Test
    public void testValid() throws Exception {
        testReadAndWriteInvoice("xml/ppf/UC1-01-Facture-formatCII.xml");
    }

    @Test
    public void testEmptyCII() {
        final List<IValidationError> errors = validator.validate(new CrossIndustryInvoiceType());
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
