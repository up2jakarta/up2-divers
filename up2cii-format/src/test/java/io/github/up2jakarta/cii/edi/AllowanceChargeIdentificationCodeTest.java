package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeSettlementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradeAllowanceChargeType;
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
public class AllowanceChargeIdentificationCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.AllowanceChargeIdentificationCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.AllowanceChargeIdentificationCodeType() {{
                setValue("???");
            }};

    public AllowanceChargeIdentificationCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "valid2-formatCII.xml", "invalid_allowance_charge_id.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var settlement = trade.getApplicableHeaderTradeSettlement();
            var charges = settlement.getSpecifiedTradeAllowanceCharge();
            var charge = charges.getFirst();
            charge.setTypeCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final HeaderTradeSettlementType settlement = trade.getApplicableHeaderTradeSettlement();
        assertNotNull(settlement);
        final List<TradeAllowanceChargeType> charges = settlement.getSpecifiedTradeAllowanceCharge();
        assertNotNull(charges);
        assertEquals(1, charges.size());
        final TradeAllowanceChargeType charge = charges.getFirst();
        assertNotNull(charge);
        final AllowanceChargeIdentificationCodeType code = charge.getTypeCode();
        assertEquals(AllowanceChargeIdentificationCodeType.V_105, code);
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
            assertEquals(246, error.getLineNumber());
            assertEquals(49, error.getColumnNumber());
            assertEquals("ECE-5189: Unknown value [???] for CodeList[AllowanceChargeIdentificationCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}
