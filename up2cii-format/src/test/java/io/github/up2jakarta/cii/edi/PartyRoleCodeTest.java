package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.api.IValidationError;
import io.github.up2jakarta.cii.api.XValidationException;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeAgreementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradePartyType;
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
public class PartyRoleCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.PartyRoleCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.PartyRoleCodeType() {{
                setValue("???");
            }};

    public PartyRoleCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_party_role.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var agreement = trade.getApplicableHeaderTradeAgreement();
            var seller = agreement.getSellerTradeParty();
            var roles = seller.getRoleCode();
            roles.clear();
            roles.add(WRONG_CODE);
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
        final List<PartyRoleCodeType> roles = seller.getRoleCode();
        assertNotNull(roles);
        assertEquals(1, roles.size());
        final PartyRoleCodeType code = roles.get(0);
        assertEquals(PartyRoleCodeType.AA, code);
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
            assertEquals(96, error.getLineNumber());
            assertEquals(49, error.getColumnNumber());
            assertEquals("ECE-3035: Unknown value [???] for CodeList[PartyRoleCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }
}
