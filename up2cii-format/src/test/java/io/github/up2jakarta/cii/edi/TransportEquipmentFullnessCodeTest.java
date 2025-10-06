package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeDeliveryType;
import io.github.up2jakarta.cii.format.standard.ram.LogisticsTransportEquipmentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainConsignmentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
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
public class TransportEquipmentFullnessCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.TransportEquipmentFullnessCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.TransportEquipmentFullnessCodeType() {{
                setValue("???");
            }};

    public TransportEquipmentFullnessCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_transport_equipment_fullness.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var delivery = trade.getApplicableHeaderTradeDelivery();
            var supplyChain = delivery.getRelatedSupplyChainConsignment();
            var transportEquipments = supplyChain.getUtilizedLogisticsTransportEquipment();
            var transportEquipment = transportEquipments.getFirst();
            transportEquipment.setUsedCapacityCode(WRONG_CODE);
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
        final LogisticsTransportEquipmentType transportEquipment = transportEquipments.getFirst();
        assertNotNull(transportEquipment);
        final TransportEquipmentFullnessCodeType code = transportEquipment.getUsedCapacityCode();
        assertEquals(TransportEquipmentFullnessCodeType.V_5, code);
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
            assertEquals(198, error.getLineNumber());
            assertEquals(69, error.getColumnNumber());
            assertEquals("ECE-8169: Unknown value [???] for CodeList[TransportEquipmentFullnessCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
