package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.qdt.LinearUnitMeasureType;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeDeliveryType;
import io.github.up2jakarta.cii.format.standard.ram.LogisticsTransportEquipmentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainConsignmentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
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
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class LinearMeasurementUnitCodeTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public LinearMeasurementUnitCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_linear_measurement_unit.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var delivery = trade.getApplicableHeaderTradeDelivery();
            var supplyChain = delivery.getRelatedSupplyChainConsignment();
            var transportEquipments = supplyChain.getUtilizedLogisticsTransportEquipment();
            var transportEquipment = transportEquipments.get(0);
            var linearMeasure = transportEquipment.getLoadingLengthMeasure();
            linearMeasure.setUnitCode(WRONG_CODE);
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
        final List<LogisticsTransportEquipmentType> transportEquipments = supplyChain.getUtilizedLogisticsTransportEquipment();
        assertNotNull(transportEquipments);
        assertEquals(1, transportEquipments.size());
        final LogisticsTransportEquipmentType transportEquipment = transportEquipments.get(0);
        assertNotNull(transportEquipment);
        final LinearUnitMeasureType linearMeasure = transportEquipment.getLoadingLengthMeasure();
        assertNotNull(linearMeasure);
        assertEquals(BigDecimal.TEN, linearMeasure.getValue());
        final LinearMeasurementUnitCodeType code = linearMeasure.getUnitCode();
        assertEquals(LinearMeasurementUnitCodeType.MTR, code);
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
            assertEquals(195, error.getLineNumber());
            assertEquals(62, error.getColumnNumber());
            assertEquals("ECE-R20: Unknown value [???] for CodeList[LinearMeasurementUnitCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
