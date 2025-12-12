package io.github.up2jakarta.test.edi;

import io.github.up2jakarta.cii.edi.CommunicationChannelCodeType;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeAgreementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradePartyType;
import io.github.up2jakarta.cii.format.standard.ram.UniversalCommunicationType;
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
public class CommunicationChannelCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.CommunicationChannelCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.CommunicationChannelCodeType() {{
                setValue("???");
            }};

    public CommunicationChannelCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_communication_channel_code.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var agreement = trade.getApplicableHeaderTradeAgreement();
            var seller = agreement.getSellerTradeParty();
            var communications = seller.getURIUniversalCommunication();
            var communication = communications.getFirst();
            communication.setChannelCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final HeaderTradeAgreementType agreement = trade.getApplicableHeaderTradeAgreement();
        assertNotNull(agreement);
        final TradePartyType seller = agreement.getSellerTradeParty();
        assertNotNull(seller);
        final List<UniversalCommunicationType> communications = seller.getURIUniversalCommunication();
        assertNotNull(communications);
        assertEquals(1, communications.size());
        final UniversalCommunicationType communication = communications.getFirst();
        assertNotNull(communication);
        final CommunicationChannelCodeType code = communication.getChannelCode();
        assertNotNull(code);
        assertEquals(CommunicationChannelCodeType.EM, code);
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
            assertEquals(122, error.getLineNumber());
            assertEquals(59, error.getLineOffset());
            assertEquals("ECE-3155: Unknown input [???] for CodeList[CommunicationChannelCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
