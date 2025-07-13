package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.LineTradeSettlementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeLineItemType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradePaymentTermsType;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.api.XValidationException;
import io.github.up2jakarta.xml.codelist.CodeListException;
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
public class PaymentTermsTypeCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.PaymentTermsTypeCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.PaymentTermsTypeCodeType() {{
                setValue("???");
            }};

    public PaymentTermsTypeCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_payment_terms_type.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var items = trade.getIncludedSupplyChainTradeLineItem();
            var item = items.getFirst();
            var settlement = item.getSpecifiedLineTradeSettlement();
            var terms = settlement.getSpecifiedTradePaymentTerms();
            var term = terms.getFirst();
            term.setTypeCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final List<SupplyChainTradeLineItemType> items = trade.getIncludedSupplyChainTradeLineItem();
        assertNotNull(items);
        assertEquals(1, items.size());
        final SupplyChainTradeLineItemType item = items.getFirst();
        assertNotNull(item);
        final LineTradeSettlementType settlement = item.getSpecifiedLineTradeSettlement();
        assertNotNull(settlement);
        final List<TradePaymentTermsType> terms = settlement.getSpecifiedTradePaymentTerms();
        assertNotNull(terms);
        assertEquals(1, terms.size());
        final TradePaymentTermsType term = terms.getFirst();
        assertNotNull(term);
        final PaymentTermsTypeCodeType code = term.getTypeCode();
        assertEquals(PaymentTermsTypeCodeType.V_1, code);
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
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(76, error.getLineNumber());
            assertEquals(53, error.getColumnNumber());
            assertEquals("ECE-4279: Unknown value [???] for CodeList[PaymentTermsTypeCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
