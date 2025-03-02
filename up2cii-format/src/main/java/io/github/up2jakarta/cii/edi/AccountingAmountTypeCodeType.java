package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.AccountingAmountTypeCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on EU/EDIFICAS E601 : Accounting Amount Type.
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Accounting Amount Type", agency = Agency.EU_EDI, version = "D11A")
@Schema(agency = "UN/CEFACT", version = "1.0", date = "2008-08-23")
@XmlJavaTypeAdapter(AccountingAmountTypeCodeAdapter.class)
public enum AccountingAmountTypeCodeType implements CodeList<AccountingAmountTypeCodeType> {

    /**
     * The code indicates an allowance charge amount.
     */
    V_1("1", "Allowance Charge Amount"),

    /**
     * The code indicates an insurance charge amount.
     */
    V_2("2", "Insurance Charge Amount"),

    /**
     * The code indicates a taxable transport charge amount.
     */
    V_3("3", "Taxable Transport Charge Amount"),

    /**
     * The code indicates an adjustment amount.
     */
    V_4("4", "Adjustment Amount"),

    /**
     * The code indicates a taxable amount.
     */
    V_5("5", "Taxable Amount"),

    /**
     * The code indicates a tax amount.
     */
    V_6("6", "Tax Amount"),
    ;

    private final String name;
    private final String code;

    AccountingAmountTypeCodeType(String code, String name) {
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
