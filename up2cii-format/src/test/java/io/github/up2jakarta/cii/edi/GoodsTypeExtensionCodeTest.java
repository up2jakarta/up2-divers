package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XValidationException;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class GoodsTypeExtensionCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.GoodsTypeExtensionCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.GoodsTypeExtensionCodeType() {{
                setValue("???");
            }};

    public GoodsTypeExtensionCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_goods_type_extension.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var delivery = trade.getApplicableHeaderTradeDelivery();
            var supplyChain = delivery.getRelatedSupplyChainConsignment();
            var chainConsignments = supplyChain.getIncludedSupplyChainConsignmentItem();
            var chainConsignment = chainConsignments.get(0);
            chainConsignment.setTypeExtensionCode(WRONG_CODE);
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
        final GoodsTypeExtensionCodeType code = chainConsignment.getTypeExtensionCode();
        assertNotNull(code);
        assertEquals(GoodsTypeExtensionCodeType.ZZZ, code);
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
            assertEquals(186, error.getLineNumber());
            assertEquals(71, error.getColumnNumber());
            assertEquals("ECE-7361: Unknown value [???] for CodeList[GoodsTypeExtensionCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
