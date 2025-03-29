package io.github.up2jakarta.cii.format.unmapped;

import io.github.up2jakarta.cii.InvoiceWriter;
import io.github.up2jakarta.cii.format.unmapped.ram.ExchangedDocumentContextType;
import io.github.up2jakarta.cii.format.unmapped.ram.ExchangedDocumentType;
import io.github.up2jakarta.cii.format.unmapped.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.unmapped.ram.ValuationBreakdownStatementType;
import io.github.up2jakarta.xml.api.XValidationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.github.up2jakarta.cii.api.TestUtil.loadResource;
import static io.github.up2jakarta.cii.format.unmapped.InvoiceReaderTest.READER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvoiceWriterTest {

    public static final InvoiceWriter<CrossIndustryInvoiceType> WRITER;

    static {
        WRITER = new InvoiceWriter<>(CrossIndustryInvoiceType.class);
    }

    // Test invoices
    protected final CrossIndustryInvoiceType validInvoice;
    protected final CrossIndustryInvoiceType invalidInvoice;

    public InvoiceWriterTest() {
        try {
            var xml = loadResource("xml/ppf/UC1-01-Facture-formatCII.xml");
            this.validInvoice = READER.read(xml, false);
            this.invalidInvoice = READER.read(xml, false);
            this.invalidInvoice.getExchangedDocument().setTypeCode(null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        assertNotNull(validInvoice);
    }

    @Test
    public void testValid() {
        var document = WRITER.write(validInvoice, false);
        assertNotNull(document);
    }

    @Test
    public void testInvalid() {
        var document = WRITER.write(validInvoice, false);
        assertNotNull(document);
    }

    @Test
    public void testEmpty() {
        assertThrows(XValidationException.class, () -> WRITER.write(new CrossIndustryInvoiceType(), false));
    }

    @Test
    public void testNull() {
        assertThrows(XValidationException.class, () -> WRITER.write(null, false));
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
        assertThrows(XValidationException.class, () -> WRITER.write(invalid, false));
    }
}
