package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.CodeAdapterTest;
import io.github.up2jakarta.cii.format.standard.ram.HeaderTradeSettlementType;
import io.github.up2jakarta.cii.format.standard.ram.SupplyChainTradeTransactionType;
import io.github.up2jakarta.cii.format.standard.ram.TradeAccountingAccountType;
import io.github.up2jakarta.cii.format.standard.ram.TradeTaxType;
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
public class AccountingAccountTypeCodeTest extends CodeAdapterTest {

    private static final io.github.up2jakarta.cii.format.unmapped.qdt.AccountingAccountTypeCodeType WRONG_CODE =
            new io.github.up2jakarta.cii.format.unmapped.qdt.AccountingAccountTypeCodeType() {{
                setValue("???");
            }};

    public AccountingAccountTypeCodeTest(@Autowired ApplicationContext context) throws IOException {
        super(context, "invalid_accounting_account_type.xml", (i) -> {
            var trade = i.getSupplyChainTradeTransaction();
            var settlement = trade.getApplicableHeaderTradeSettlement();
            var taxes = settlement.getApplicableTradeTax();
            var tax = taxes.getFirst();
            var account = tax.getBuyerRepayableTaxSpecifiedTradeAccountingAccount();
            account.setTypeCode(WRONG_CODE);
        });
    }

    @Test
    public void value() {
        final SupplyChainTradeTransactionType trade = validInvoice.getSupplyChainTradeTransaction();
        assertNotNull(trade);
        final HeaderTradeSettlementType settlement = trade.getApplicableHeaderTradeSettlement();
        assertNotNull(settlement);
        final List<TradeTaxType> taxes = settlement.getApplicableTradeTax();
        assertNotNull(taxes);
        assertEquals(1, taxes.size());
        final TradeTaxType tax = taxes.getFirst();
        assertNotNull(tax);
        final TradeAccountingAccountType account = tax.getBuyerRepayableTaxSpecifiedTradeAccountingAccount();
        assertNotNull(account);
        final AccountingAccountTypeCodeType code = account.getTypeCode();
        assertEquals(AccountingAccountTypeCodeType.V_6, code);
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
            assertEquals(266, error.getLineNumber());
            assertEquals(53, error.getLineOffset());
            assertEquals("EDI-E501: Unknown value [???] for CodeList[AccountingAccountTypeCodeType].", error.getMessage());
            assertNotNull(error.getLinkedException());
            assertInstanceOf(CodeListException.class, error.getLinkedException());

        }
    }
}
