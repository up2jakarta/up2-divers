package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeAgreementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradePartyType;
import io.github.up2jakarta.cii.format.standard.udt.PartyIDType;
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
public class SchemeCodeTest extends CodeAdapterTest {

    private static final String WRONG_CODE = "???";

    public SchemeCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_scheme.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var agreement = trade.getApplicableHeaderTradeAgreement();
            var seller = agreement.getSellerTradeParty();
            var ids = seller.getGlobalID();
            var id = ids.getFirst();
            id.setSchemeID(WRONG_CODE);
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
        final List<PartyIDType> ids = seller.getGlobalID();
        assertNotNull(ids);
        assertEquals(1, ids.size());
        final PartyIDType id = ids.getFirst();
        assertNotNull(id);
        final SchemeCodeType code = id.getSchemeID();
        assertNotNull(code);
        assertEquals(SchemeCodeType.V_0009, code);
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
            assertEquals(94, error.getLineNumber());
            assertEquals(46, error.getLineOffset());
            assertEquals("ISO-6523: Unknown value [???] for CodeList[SchemeCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}
