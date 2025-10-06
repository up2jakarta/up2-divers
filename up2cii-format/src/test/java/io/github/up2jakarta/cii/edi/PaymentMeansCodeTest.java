package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeSettlementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradeSettlementPaymentMeansType;
import io.github.up2jakarta.xml.api.IValidationError;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.api.XValidationException;
import io.github.up2jakarta.xml.clv.CodeListException;
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
public class PaymentMeansCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.PaymentMeansCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.PaymentMeansCodeType() {{
                setValue("???");
            }};

    public PaymentMeansCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_payment_means.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var settlement = trade.getApplicableHeaderTradeSettlement();
            var payments = settlement.getSpecifiedTradeSettlementPaymentMeans();
            var payment = payments.getFirst();
            payment.setTypeCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final HeaderTradeSettlementType settlement = trade.getApplicableHeaderTradeSettlement();
        assertNotNull(settlement);
        final List<TradeSettlementPaymentMeansType> payments = settlement.getSpecifiedTradeSettlementPaymentMeans();
        assertNotNull(payments);
        assertEquals(1, payments.size());
        final TradeSettlementPaymentMeansType payment = payments.getFirst();
        assertNotNull(payment);
        final PaymentMeansCodeType code = payment.getTypeCode();
        assertEquals(PaymentMeansCodeType.V_30, payment.getTypeCode());
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
            assertEquals(244, error.getLineNumber());
            assertEquals(49, error.getColumnNumber());
            assertEquals("ECE-4461: Unknown value [???] for CodeList[PaymentMeansCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}