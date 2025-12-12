package io.github.up2jakarta.test.edi;

import io.github.up2jakarta.cii.edi.PackageTypeCodeType;
import io.github.up2jakarta.cii.format.standard.ram.LineTradeDeliveryType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainPackagingType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeLineItemType;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class PackageTypeCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.PackageTypeCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.PackageTypeCodeType() {{
                setValue("???");
            }};

    public PackageTypeCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_package_type.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var items = trade.getIncludedSupplyChainTradeLineItem();
            var item = items.getFirst();
            var delivery = item.getSpecifiedLineTradeDelivery();
            var packagings = delivery.getIncludedSupplyChainPackaging();
            var packaging = packagings.getFirst();
            packaging.setTypeCode(WRONG_CODE);
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
        final PackageTypeCodeType code = packaging.getTypeCode();
        assertEquals(PackageTypeCodeType.AL, code);
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
            assertEquals(52, error.getLineNumber());
            assertEquals(53, error.getLineOffset());
            assertEquals("ECE-7065: Unknown input [???] for CodeList[PackageTypeCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
