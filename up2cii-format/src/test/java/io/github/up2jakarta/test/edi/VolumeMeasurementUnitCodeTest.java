package io.github.up2jakarta.test.edi;

import io.github.up2jakarta.cii.edi.VolumeMeasurementUnitCodeType;
import io.github.up2jakarta.cii.format.standard.qdt.VolumeUnitMeasureType;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeDeliveryType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainConsignmentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
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
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class VolumeMeasurementUnitCodeTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public VolumeMeasurementUnitCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "valid2-formatCII.xml", "invalid_volume_measurement_unit.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var delivery = trade.getApplicableHeaderTradeDelivery();
            var supplyChain = delivery.getRelatedSupplyChainConsignment();
            var volumeMeasures = supplyChain.getGrossVolumeMeasure();
            var volumeMeasure = volumeMeasures.getFirst();
            volumeMeasure.setUnitCode(WRONG_CODE);
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
        final List<VolumeUnitMeasureType> volumeMeasures = supplyChain.getGrossVolumeMeasure();
        assertNotNull(volumeMeasures);
        assertEquals(1, volumeMeasures.size());
        final VolumeUnitMeasureType volumeMeasure = volumeMeasures.getFirst();
        assertNotNull(volumeMeasure);
        assertEquals(BigDecimal.TEN, volumeMeasure.getValue());
        final VolumeMeasurementUnitCodeType code = volumeMeasure.getUnitCode();
        assertEquals(VolumeMeasurementUnitCodeType.CMQ, code);
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
            assertEquals(165, error.getLineNumber());
            assertEquals(56, error.getLineOffset());
            assertEquals("ECE-R20: Unknown input [???] for CodeList[VolumeMeasurementUnitCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
