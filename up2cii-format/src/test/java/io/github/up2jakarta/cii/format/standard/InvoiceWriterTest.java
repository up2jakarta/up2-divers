package io.github.up2jakarta.cii.format.standard;

import io.github.up2jakarta.cii.InvoiceWriter;
import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.format.standard.ram.ExchangedDocumentContextType;
import io.github.up2jakarta.cii.format.standard.ram.ExchangedDocumentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.ValuationBreakdownStatementType;
import io.github.up2jakarta.xml.api.XConfigurationException;
import io.github.up2jakarta.xml.api.XReader;
import io.github.up2jakarta.xml.api.XValidationException;
import io.github.up2jakarta.xml.api.XWriter;
import io.github.up2jakarta.xml.clv.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class InvoiceWriterTest {

    // Test invoices
    protected final CrossIndustryInvoiceType validInvoice;
    protected final CrossIndustryInvoiceType invalidInvoice;

    @Autowired
    private XWriter<CrossIndustryInvoiceType> writer;

    public InvoiceWriterTest(@Autowired XReader<CrossIndustryInvoiceType> reader) {
        try {
            var xml = loadResource("xml/ppf/UC1-01-Facture-formatCII.xml");
            this.validInvoice = reader.read(xml, false);
            this.invalidInvoice = reader.read(xml, false);
            this.invalidInvoice.getExchangedDocument().setTypeCode(null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        assertNotNull(validInvoice);
    }

    @Test
    public void testValid() {
        var document = writer.write(validInvoice, false);
        assertNotNull(document);
    }

    @Test
    public void testInvalid() {
        var document = writer.write(validInvoice, false);
        assertNotNull(document);
    }

    @Test
    public void testEmpty() {
        assertThrows(XValidationException.class, () -> writer.write(new CrossIndustryInvoiceType(), false));
    }

    @Test
    public void testNull() {
        assertThrows(XValidationException.class, () -> writer.write(null, false));
    }

    @Test
    public void testNotValid() {
        var invalid = new CrossIndustryInvoiceType();
        {
            invalid.setExchangedDocument(new ExchangedDocumentType());
            invalid.setExchangedDocumentContext(new ExchangedDocumentContextType());
            invalid.setSupplyChainTradeTransaction(new SupplyChainTradeTransactionType());
            invalid.setValuationBreakdownStatement(new ValuationBreakdownStatementType());
        }
        assertThrows(XValidationException.class, () -> writer.write(invalid, false));
    }

    @Test
    public void testConfigClass() {
        assertThrows(XConfigurationException.class, () -> new InvoiceWriter<>(CodeList.class, new XmlAdapter[0]));
    }

}
