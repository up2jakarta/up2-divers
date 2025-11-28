package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeSettlementType;
import io.github.up2jakarta.cii.format.standard.ram.LogisticsServiceChargeType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.SeverityType;
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
public class LogisticsChargeCalculationBasisCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.LogisticsChargeCalculationBasisCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.LogisticsChargeCalculationBasisCodeType() {{
                setValue("???");
            }};

    public LogisticsChargeCalculationBasisCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_logistics_charge_calculation_basis.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var settlement = trade.getApplicableHeaderTradeSettlement();
            var services = settlement.getSpecifiedLogisticsServiceCharge();
            var service = services.getFirst();
            service.setCalculationBasisCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final HeaderTradeSettlementType settlement = trade.getApplicableHeaderTradeSettlement();
        assertNotNull(settlement);
        final List<LogisticsServiceChargeType> services = settlement.getSpecifiedLogisticsServiceCharge();
        assertNotNull(services);
        assertEquals(1, services.size());
        final LogisticsServiceChargeType service = services.getFirst();
        assertNotNull(service);
        final LogisticsChargeCalculationBasisCodeType code = service.getCalculationBasisCode();
        assertEquals(LogisticsChargeCalculationBasisCodeType.ZZZ, code);
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
            assertEquals(298, error.getLineNumber());
            assertEquals(73, error.getLineOffset());
            assertEquals("ECE-6131: Unknown input [???] for CodeList[LogisticsChargeCalculationBasisCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}
