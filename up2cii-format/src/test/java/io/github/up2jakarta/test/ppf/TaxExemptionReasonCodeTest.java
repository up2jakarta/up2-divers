package io.github.up2jakarta.test.ppf;

import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeSettlementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradeTaxType;
import io.github.up2jakarta.cii.format.unmapped.udt.CodeType;
import io.github.up2jakarta.cii.ppf.TaxExemptionReasonCodeType;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.api.CodeAdapterTest;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.XValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class TaxExemptionReasonCodeTest extends CodeAdapterTest {

    private static final CodeType WRONG_CODE = new CodeType() {{
        setValue("???");
    }};

    public TaxExemptionReasonCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_tax_exemption_reason.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var settlement = trade.getApplicableHeaderTradeSettlement();
            var taxes = settlement.getApplicableTradeTax();
            var tax = taxes.getFirst();
            tax.setExemptionReasonCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final HeaderTradeSettlementType settlement = trade.getApplicableHeaderTradeSettlement();
        assertNotNull(settlement);
        final List<TradeTaxType> taxes = settlement.getApplicableTradeTax();
        assertNotNull(taxes);
        assertEquals(1, taxes.size());
        final TradeTaxType tax = taxes.getFirst();
        assertNotNull(tax);
        final TaxExemptionReasonCodeType code = tax.getExemptionReasonCode();
        assertNotNull(code);
        assertEquals(TaxExemptionReasonCodeType.VATEX_EU_AE, code);
        assertNotNull(code.getName());
    }

    @Test
    public void read() throws IOException {
        assertThrows(XValidationException.class, () -> reader.read(invalidInvoiceFile, false));
    }

    @Test
    public void validate() throws IOException {
        final List<IValidationError> errors = validator.validate(invalidInvoiceFile);
        assertNotNull(errors);
        assertEquals(1, errors.size());
        {
            final IValidationError error = errors.getFirst();
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(260, error.getLineNumber());
            assertEquals(71, error.getLineOffset());
            assertEquals("EDI-E307: Unknown input [???] for CodeList[TaxExemptionReasonCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}
