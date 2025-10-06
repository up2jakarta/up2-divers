package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.AccountingAccountTypeCodeAdapter;
import io.github.up2jakarta.xml.clv.Agency;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.Documented;
import io.github.up2jakarta.xml.clv.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on EU/EDIFICAS E501 : Accounting Account Type.
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Accounting Account Type", agency = Agency.EU_EDI, version = "D11A")
@Schema(agency = "UN/CEFACT", version = "1.0", date = "2008-08-23")
@XmlJavaTypeAdapter(AccountingAccountTypeCodeAdapter.class)
public enum AccountingAccountTypeCodeType implements CodeList<AccountingAccountTypeCodeType> {

    /**
     * The code indicates a financial account.
     */
    V_1("1", "Financial"),

    /**
     * The code indicates a subsidiary account.
     */
    V_2("2", "Subsidiary"),

    /**
     * The code indicates a budget account.
     */
    V_3("3", "Budget"),

    /**
     * The code indicates a cost accounting account.
     */
    V_4("4", "Cost Accounting"),

    /**
     * The code indicates a receivable account.
     */
    V_5("5", "Receivable"),

    /**
     * The code indicates a payable account.
     */
    V_6("6", "Payable"),

    /**
     * The code indicates a job cost accounting.
     */
    V_7("7", "Job Cost Accounting"),
    ;

    private final String name;
    private final String code;

    AccountingAccountTypeCodeType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

}
