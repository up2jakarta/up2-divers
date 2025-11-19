package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.ProductClassificationType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeLineItemType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradeProductType;
import io.github.up2jakarta.cii.format.standard.udt.ProductClassCodeType;
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
public class ItemTypeIDCodeTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public ItemTypeIDCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_item_type_id.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var items = trade.getIncludedSupplyChainTradeLineItem();
            var item = items.getFirst();
            var product = item.getSpecifiedTradeProduct();
            var classifications = product.getDesignatedProductClassification();
            var classification = classifications.getFirst();
            var classCode = classification.getClassCode();
            assertNotNull(classCode);
            classCode.setListID(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final List<SupplyChainTradeLineItemType> items = trade.getIncludedSupplyChainTradeLineItem();
        assertNotNull(items);
        assertEquals(1, items.size());
        final SupplyChainTradeLineItemType item = items.getFirst();
        assertNotNull(item);
        final TradeProductType product = item.getSpecifiedTradeProduct();
        assertNotNull(product);
        final List<ProductClassificationType> classifications = product.getDesignatedProductClassification();
        assertNotNull(classifications);
        assertEquals(1, classifications.size());
        final ProductClassificationType classification = classifications.getFirst();
        assertNotNull(classification);
        final ProductClassCodeType classCode = classification.getClassCode();
        assertNotNull(classCode);
        final ItemTypeIDCodeType code = classCode.getListID();
        assertNotNull(code);
        assertEquals(ItemTypeIDCodeType.AB, code);
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
            assertEquals(36, error.getLineNumber());
            assertEquals(49, error.getLineOffset());
            assertEquals("ECE-7143: Unknown value [???] for CodeList[ItemTypeIDCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}
