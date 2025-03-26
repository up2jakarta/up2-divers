package io.github.up2jakarta.cii.format.unmapped;


import io.github.up2jakarta.xml.api.IValidationError;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.cii.api.TestUtil.assertInvoice;
import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static io.github.up2jakarta.cii.format.unmapped.InvoiceReaderTest.READER;
import static io.github.up2jakarta.cii.format.unmapped.InvoiceValidatorTest.VALIDATOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InvoiceReaderValidatorTest {

    private static void testReadValidateInvoice(String xmlFile) throws IOException {
        assertNotNull(xmlFile);
        assertInvoice(loadResource(xmlFile), VALIDATOR, READER);
    }

    @Test
    public void testValid() throws IOException {
        testReadValidateInvoice("xml/ppf/UC1-01-Facture-formatCII.xml");
    }

    @Test
    public void testEmptyCII() {
        final List<IValidationError> errors = VALIDATOR.validate(new CrossIndustryInvoiceType());
        assertNotNull(errors);
        assertEquals(1, errors.size());
    }

    @Test
    public void testCII01() throws IOException {
        testReadValidateInvoice("xml/ppf/UC1-01-Facture-formatCII.xml");
    }

    @Test
    public void testCII02() throws IOException {
        testReadValidateInvoice("xml/ppf/UC4-01-FactureCII.xml");
    }

    @Test
    public void testCII03() throws IOException {
        testReadValidateInvoice("xml/ppf/UC5-01-Autofacturation-formatCII.xml");
    }

    @Test
    public void testCII04() throws IOException {
        testReadValidateInvoice("xml/ppf/UC5-04-Avoir-formatCII.xml");
    }

    @Test
    public void testCII05() throws IOException {
        testReadValidateInvoice("xml/ppf/UC2-01-Facture-formatCII.xml");
    }

    @Test
    public void testCII06() throws IOException {
        testReadValidateInvoice("xml/ppf/UC2-04-Rectificative-formatCII.xml");
    }

    @Test
    public void testCII07() throws IOException {
        testReadValidateInvoice("xml/ppf/UC1-05-Avoir-formatCII.xml");
    }

    @Test
    public void testCII08() throws IOException {
        testReadValidateInvoice("xml/ppf/UC6-01-Acompte-formatCII.xml");
    }

    @Test
    public void testCII09() throws IOException {
        testReadValidateInvoice("xml/ppf/UC1-10-Rectificative-formatCII.xml");
    }

    @Test
    public void testCII13() throws IOException {
        testReadValidateInvoice("xml/invalid1-formatCII.xml");
    }

    @Test
    public void testCII14() throws IOException {
        testReadValidateInvoice("xml/invalid2-formatCII.xml");
    }

}
