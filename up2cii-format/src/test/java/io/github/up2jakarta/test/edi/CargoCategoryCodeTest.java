package io.github.up2jakarta.test.edi;

import io.github.up2jakarta.cii.edi.CargoCategoryCodeType;
import io.github.up2jakarta.cii.format.standard.ram.*;
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
public class CargoCategoryCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.CargoCategoryCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.CargoCategoryCodeType() {{
                setValue("???");
            }};

    public CargoCategoryCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_cargo_category.xml", i -> {
            var trade = i.getSupplyChainTradeTransaction();
            var delivery = trade.getApplicableHeaderTradeDelivery();
            var supplyChain = delivery.getRelatedSupplyChainConsignment();
            var chainConsignments = supplyChain.getIncludedSupplyChainConsignmentItem();
            var chainConsignment = chainConsignments.getFirst();
            var cargo = chainConsignment.getNatureIdentificationTransportCargo();
            cargo.setTypeCode(WRONG_CODE);
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
        final SupplyChainConsignmentItemType chainConsignment = chainConsignments.getFirst();
        assertNotNull(chainConsignment);
        final TransportCargoType cargo = chainConsignment.getNatureIdentificationTransportCargo();
        assertNotNull(cargo);
        final CargoCategoryCodeType code = cargo.getTypeCode();
        assertNotNull(code);
        assertEquals(CargoCategoryCodeType.V_9, code);
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
            assertEquals(189, error.getLineNumber());
            assertEquals(57, error.getLineOffset());
            assertEquals("ECE-R21: Unknown input [???] for CodeList[CargoCategoryCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
