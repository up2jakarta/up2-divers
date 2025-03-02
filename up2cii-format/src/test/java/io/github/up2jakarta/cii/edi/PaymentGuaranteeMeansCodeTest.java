package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XValidationException;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeSettlementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradeSettlementPaymentMeansType;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.extension.SeverityType;
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
public class PaymentGuaranteeMeansCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.PaymentGuaranteeMeansCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.PaymentGuaranteeMeansCodeType() {{
                setValue("???");
            }};

    public PaymentGuaranteeMeansCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_payment_guarantee_means.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var settlement = trade.getApplicableHeaderTradeSettlement();
            var payments = settlement.getSpecifiedTradeSettlementPaymentMeans();
            var payment = payments.get(0);
            payment.setGuaranteeMethodCode(WRONG_CODE);
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
        final TradeSettlementPaymentMeansType payment = payments.get(0);
        assertNotNull(payment);
        final PaymentGuaranteeMeansCodeType code = payment.getGuaranteeMethodCode();
        assertEquals(PaymentGuaranteeMeansCodeType.V_10, code);
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
            final IValidationError error = errors.get(0);
            assertEquals(SeverityType.ERROR, error.getSeverity());
            assertEquals(245, error.getLineNumber());
            assertEquals(71, error.getColumnNumber());
            assertEquals("ECE-4431: Unknown value [???] for CodeList[PaymentGuaranteeMeansCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
