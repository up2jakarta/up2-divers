package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XValidationException;
import io.github.up2jakarta.cii.format.standard.qdt.WeightUnitMeasureType;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeDeliveryType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainConsignmentItemType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainConsignmentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.extension.SeverityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class WeightMeasurementUnitCodeTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public WeightMeasurementUnitCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_weight_measurement_unit.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var delivery = trade.getApplicableHeaderTradeDelivery();
            var supplyChain = delivery.getRelatedSupplyChainConsignment();
            var chainConsignments = supplyChain.getIncludedSupplyChainConsignmentItem();
            var chainConsignment = chainConsignments.get(0);
            var weightMeasure = chainConsignment.getGrossWeightMeasure();
            weightMeasure.setUnitCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final HeaderTradeDeliveryType delivery = trade.getApplicableHeaderTradeDelivery();
        assertNotNull(delivery);
        final SupplyChainConsignmentType supplyChain = delivery.getRelatedSupplyChainConsignment();
        assertNotNull(supplyChain);
        final List<SupplyChainConsignmentItemType> chainConsignments = supplyChain.getIncludedSupplyChainConsignmentItem();
        assertNotNull(chainConsignments);
        assertEquals(1, chainConsignments.size());
        final SupplyChainConsignmentItemType chainConsignment = chainConsignments.get(0);
        assertNotNull(chainConsignment);
        final WeightUnitMeasureType weightMeasure = chainConsignment.getGrossWeightMeasure();
        assertNotNull(weightMeasure);
        assertEquals(BigDecimal.TEN, weightMeasure.getValue());
        final WeightMeasurementUnitCodeType code = weightMeasure.getUnitCode();
        assertEquals(WeightMeasurementUnitCodeType.KGM, code);
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
            assertEquals(187, error.getLineNumber());
            assertEquals(60, error.getColumnNumber());
            assertEquals("ECE-R20: Unknown value [???] for CodeList[WeightMeasurementUnitCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
