package io.github.up2jakarta.test.cl;

import io.github.up2jakarta.cii.edi.AccountingAccountTypeCodeType;
import io.github.up2jakarta.cii.edi.AccountingAmountTypeCodeType;
import org.junit.jupiter.api.Test;

import static io.github.up2jakarta.test.api.TestUtil.assertUniqueness;

public class EdificasCheckingUniquenessTests {

    /**
     * @see AccountingAmountTypeCodeType
     */
    @Test
    public void e601() {
        assertUniqueness(AccountingAmountTypeCodeType.class, AccountingAmountTypeCodeType::getCode);
    }

    /**
     * @see AccountingAmountTypeCodeType
     */
    @Test
    public void e501() {
        assertUniqueness(AccountingAccountTypeCodeType.class, AccountingAccountTypeCodeType::getCode);
    }

}
