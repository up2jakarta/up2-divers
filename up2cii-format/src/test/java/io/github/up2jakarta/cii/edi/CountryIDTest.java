package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.edi.adapters.CountryIDAdapter;
import io.github.up2jakarta.cii.format.standard.ram.*;
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
public class CountryIDTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.CountryIDType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.CountryIDType() {{
                setValue("???");
            }};

    @Autowired
    private CountryIDAdapter adapter;

    public CountryIDTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_country.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var agreement = trade.getApplicableHeaderTradeAgreement();
            var seller = agreement.getSellerTradeParty();
            var address = seller.getPostalTradeAddress();
            address.setCountryID(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        {
            final HeaderTradeAgreementType agreement = trade.getApplicableHeaderTradeAgreement();
            assertNotNull(agreement);
            final TradePartyType seller = agreement.getSellerTradeParty();
            assertNotNull(seller);
            final TradeAddressType address = seller.getPostalTradeAddress();
            assertNotNull(address);
            assertEquals(CountryIDType.FR, address.getCountryID());
            assertNotNull(address.getCountryID().getName());
        }
        {
            final HeaderTradeAgreementType agreement = trade.getApplicableHeaderTradeAgreement();
            assertNotNull(agreement);
            final TradePartyType buyer = agreement.getBuyerTradeParty();
            assertNotNull(buyer);
            final TradeAddressType address = buyer.getPostalTradeAddress();
            assertNotNull(address);
            assertEquals(CountryIDType.FR, address.getCountryID());
        }
        {
            final HeaderTradeDeliveryType delivery = trade.getApplicableHeaderTradeDelivery();
            assertNotNull(delivery);
            final TradePartyType shipper = delivery.getShipToTradeParty();
            assertNotNull(shipper);
            final TradeAddressType address = shipper.getPostalTradeAddress();
            assertNotNull(address);
            assertEquals(CountryIDType.FR, address.getCountryID());
        }
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
            assertEquals(118, error.getLineNumber());
            assertEquals(55, error.getLineOffset());
            assertEquals("ISO-3166: Unknown value [???] for CodeList[CountryIDType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

    @Test
    public void greeceCountry() throws CodeListException {
        assertEquals("GR", adapter.marshal(CountryIDType.GR));
        assertEquals(CountryIDType.GR, adapter.unmarshal("GR"));
        assertEquals(CountryIDType.GR, adapter.unmarshal("EL"));
    }

    @Test
    public void tunisiaCountry() throws CodeListException {
        assertEquals("TN", adapter.marshal(CountryIDType.TN));
        assertEquals(CountryIDType.TN, adapter.unmarshal("TN"));
    }

    @Test
    public void franceCountry() throws CodeListException {
        assertEquals("FR", adapter.marshal(CountryIDType.FR));
        assertEquals(CountryIDType.FR, adapter.unmarshal("FR"));
    }

}
