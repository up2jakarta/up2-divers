package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.*;
import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType;
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
public class AllowanceChargeReasonCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.AllowanceChargeReasonCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.AllowanceChargeReasonCodeType() {{
                setValue("???");
            }};

    public AllowanceChargeReasonCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "valid4-formatCII.xml", "invalid_allowance_charge_reason.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            {
                var items = trade.getIncludedSupplyChainTradeLineItem();
                var item = items.getFirst();
                var lineTrade = item.getSpecifiedLineTradeAgreement();
                var net = lineTrade.getNetPriceProductTradePrice();
                var charges = net.getAppliedTradeAllowanceCharge();
                var charge = charges.getFirst();
                charge.setReasonCode(WRONG_CODE);
            }
            {
                var settlement = trade.getApplicableHeaderTradeSettlement();
                var charges = settlement.getSpecifiedTradeAllowanceCharge();
                var charge = charges.getFirst();
                charge.setReasonCode(WRONG_CODE);
                var discount = charges.get(1);
                discount.setReasonCode(WRONG_CODE);
            }
        });
    }

    @Test
    public void value() throws CodeListException {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        {
            final List<SupplyChainTradeLineItemType> items = trade.getIncludedSupplyChainTradeLineItem();
            assertNotNull(items);
            assertEquals(1, items.size());
            final SupplyChainTradeLineItemType item = items.getFirst();
            assertNotNull(item);
            final LineTradeAgreementType lineTrade = item.getSpecifiedLineTradeAgreement();
            assertNotNull(lineTrade);
            final TradePriceType net = lineTrade.getNetPriceProductTradePrice();
            assertNotNull(net);
            final List<TradeAllowanceChargeType> charges = net.getAppliedTradeAllowanceCharge();
            assertNotNull(charges);
            assertEquals(1, charges.size());
            {
                final TradeAllowanceChargeType charge = charges.getFirst();
                assertNotNull(charge);
                assertNull(charge.getChargeIndicator());
                final ChargeReasonCodeType<?> code = charge.getReasonCode();
                assertEquals(AllowanceChargeReasonCodeType.V_12, code);
                assertNotNull(code.getName());
            }
        }
        {
            final HeaderTradeSettlementType settlement = trade.getApplicableHeaderTradeSettlement();
            assertNotNull(settlement);
            final List<TradeAllowanceChargeType> charges = settlement.getSpecifiedTradeAllowanceCharge();
            assertNotNull(charges);
            assertEquals(2, charges.size());
            {
                final TradeAllowanceChargeType charge = charges.getFirst();
                assertNotNull(charge);
                assertNotNull(charge.getChargeIndicator());
                assertFalse(charge.getChargeIndicator().isIndicator());
                final ChargeReasonCodeType<?> code = charge.getReasonCode();
                assertEquals(AllowanceChargeIdentificationCodeType.V_100, code);
                assertNotNull(code.getName());
            }
            {
                final TradeAllowanceChargeType charge = charges.get(1);
                assertNotNull(charge);
                assertNotNull(charge.getChargeIndicator());
                assertTrue(charge.getChargeIndicator().isIndicator());
                final ChargeReasonCodeType<?> code = charge.getReasonCode();
                assertEquals(SpecialServiceDescriptionCodeType.AAA, code);
                assertNotNull(code.getName());
            }
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
        assertEquals(3, errors.size());
        {
            final IValidationError error = errors.getFirst();
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(45, error.getLineNumber());
            assertEquals(61, error.getLineOffset());
            assertEquals("ECE-4465: Unknown value [???] for CodeList[AllowanceChargeReasonCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(1);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(267, error.getLineNumber());
            assertEquals(53, error.getLineOffset());
            assertEquals("ECE-5189: Unknown value [???] for CodeList[AllowanceChargeIdentificationCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
        {
            final IValidationError error = errors.get(2);
            assertEquals(SeverityType.ERROR, error.getLevel());
            assertEquals(282, error.getLineNumber());
            assertEquals(53, error.getLineOffset());
            assertEquals("ECE-7161: Unknown value [???] for CodeList[SpecialServiceDescriptionCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());
        }
    }

}
