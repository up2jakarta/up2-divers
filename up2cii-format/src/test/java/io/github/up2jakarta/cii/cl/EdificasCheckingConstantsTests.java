package io.github.up2jakarta.cii.cl;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.TestUtil;
import io.github.up2jakarta.cii.edi.AccountingAccountTypeCodeType;
import io.github.up2jakarta.cii.edi.AccountingAmountTypeCodeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class EdificasCheckingConstantsTests {

    @Autowired
    private TestUtil util;

    /**
     * @see AccountingAmountTypeCodeType
     */
    @Test
    public void e601() throws Exception {
        util.assertSameBinding(AccountingAmountTypeCodeType.class);
    }

    /**
     * @see AccountingAccountTypeCodeType
     */
    @Test
    public void e501() throws Exception {
        util.assertSameBinding(AccountingAccountTypeCodeType.class);
    }

}
