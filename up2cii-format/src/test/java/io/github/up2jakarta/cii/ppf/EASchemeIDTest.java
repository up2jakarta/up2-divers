package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XValidationException;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeAgreementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradePartyType;
import io.github.up2jakarta.cii.format.standard.ram.UniversalCommunicationType;
import io.github.up2jakarta.cii.format.standard.udt.UriIDType;
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
public class EASchemeIDTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public EASchemeIDTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_electronic_address_scheme.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var agreement = trade.getApplicableHeaderTradeAgreement();
            var seller = agreement.getSellerTradeParty();
            var communications = seller.getURIUniversalCommunication();
            var communication = communications.get(0);
            var uri = communication.getURIID();
            uri.setSchemeID(WRONG_CODE);
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
        final UniversalCommunicationType communication = communications.get(0);
        assertNotNull(communication);
        final UriIDType uri = communication.getURIID();
        assertNotNull(uri);
        final PartySchemeIDType<?> code = uri.getSchemeID();
        assertNotNull(code);
        assertEquals(EASchemeIDType.EM, code);
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
            assertEquals(121, error.getLineNumber());
            assertEquals(47, error.getColumnNumber());
            assertEquals("PPF-BR63: Unknown value [???] for CodeList[EASchemeIDType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
