package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.*;
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
public class PackagingMarkingCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.PackagingMarkingCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.PackagingMarkingCodeType() {{
                setValue("???");
            }};

    public PackagingMarkingCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_packaging_marking.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var items = trade.getIncludedSupplyChainTradeLineItem();
            var item = items.getFirst();
            var delivery = item.getSpecifiedLineTradeDelivery();
            var packagings = delivery.getIncludedSupplyChainPackaging();
            var packaging = packagings.getFirst();
            var packagingMarkings = packaging.getSpecifiedPackagingMarking();
            var packagingMarking = packagingMarkings.getFirst();
            var codes = packagingMarking.getTypeCode();
            codes.clear();
            codes.add(WRONG_CODE);
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
        final LineTradeDeliveryType delivery = item.getSpecifiedLineTradeDelivery();
        assertNotNull(delivery);
        final List<SupplyChainPackagingType> packagings = delivery.getIncludedSupplyChainPackaging();
        assertNotNull(packagings);
        assertEquals(1, packagings.size());
        final SupplyChainPackagingType packaging = packagings.getFirst();
        assertNotNull(packaging);
        final List<PackagingMarkingType> packagingMarkings = packaging.getSpecifiedPackagingMarking();
        assertNotNull(packagingMarkings);
        assertEquals(1, packagingMarkings.size());
        final PackagingMarkingType packagingMarking = packagingMarkings.getFirst();
        assertNotNull(packagingMarking);
        final List<PackagingMarkingCodeType> codes = packagingMarking.getTypeCode();
        assertNotNull(codes);
        assertEquals(1, codes.size());
        final PackagingMarkingCodeType code = codes.getFirst();
        assertEquals(PackagingMarkingCodeType.V_63, code);
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
            assertEquals(54, error.getLineNumber());
            assertEquals(57, error.getColumnNumber());
            assertEquals("ECE-7233: Unknown value [???] for CodeList[PackagingMarkingCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
