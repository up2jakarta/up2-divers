package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.LineTradeSettlementType;
import io.github.up2jakarta.cii.format.standard.ram.ReferencedDocumentType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeLineItemType;
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
public class ReferenceCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.ReferenceCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.ReferenceCodeType() {{
                setValue("???");
            }};

    public ReferenceCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_reference.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var items = trade.getIncludedSupplyChainTradeLineItem();
            var item = items.getFirst();
            var settlement = item.getSpecifiedLineTradeSettlement();
            var references = settlement.getAdditionalReferencedDocument();
            var reference = references.getFirst();
            reference.setReferenceTypeCode(WRONG_CODE);
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
        final LineTradeSettlementType settlement = item.getSpecifiedLineTradeSettlement();
        assertNotNull(settlement);
        final List<ReferencedDocumentType> references = settlement.getAdditionalReferencedDocument();
        assertNotNull(references);
        assertEquals(1, references.size());
        final ReferencedDocumentType reference = references.getFirst();
        assertNotNull(reference);
        final ReferenceCodeType code = reference.getReferenceTypeCode();
        assertEquals(ReferenceCodeType.AOP, code);
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
            assertEquals(84, error.getLineNumber());
            assertEquals(71, error.getLineOffset());
            assertEquals("ECE-1153: Unknown input [???] for CodeList[ReferenceCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
