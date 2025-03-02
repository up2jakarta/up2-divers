package io.github.up2jakarta.cii.format.minified;

import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XReader;
import io.github.up2jakarta.cii.api.XValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.cii.api.TestUtil.assertInvoice;
import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MiniConfiguration.class)
public class InvoiceReaderValidatorTest {

    @Autowired
    private XValidator<CrossIndustryInvoiceType> validator;
    @Autowired
    private XReader<CrossIndustryInvoiceType> reader;

    private void testReadValidateInvoice(String xmlFile) throws IOException {
        assertNotNull(xmlFile);
        assertInvoice(loadResource(xmlFile), validator, reader);
    }

    @Test
    public void testValid() throws IOException {
        testReadValidateInvoice("xml/ppf/UC1-01-Facture-formatCII.xml");
    }

    @Test
    public void testEmptyCII() {
        final List<IValidationError> errors = validator.validate(new CrossIndustryInvoiceType());
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
