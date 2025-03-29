package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.PaymentGuaranteeMeansCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 4431 : Payment guarantee means code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred4431.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Payment Guarantee Means Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(PaymentGuaranteeMeansCodeAdapter.class)
public enum PaymentGuaranteeMeansCodeType implements CodeList<PaymentGuaranteeMeansCodeType> {

    /**
     * Payment of an invoice is made by a factor under the guarantee he issued to seller or
     * to another factor.
     */
    V_1("1", "Factor guarantee"),

    /**
     * A bank has agreed to stand as guarantor to ensure that payment is made.
     */
    V_10("10", "Bank guarantee"),

    /**
     * A public authority has agreed to stand as guarantor to ensure that payment is made.
     */
    V_11("11", "Public authority guarantee"),

    /**
     * The party who has agreed to stand as guarantor to ensure that payment is made is neither
     * the payee nor the payer.
     */
    V_12("12", "Third party guarantee"),

    /**
     * The guarantee of payment is in the form of a standby letter of credit.
     */
    V_13("13", "Standby letter of credit"),

    /**
     * No guarantee of payment has been made or is available.
     */
    V_14("14", "No guarantee"),

    /**
     * The payer has provided possession of, or title in goods, as security against payment.
     */
    V_20("20", "Goods as security"),

    /**
     * The payer has provided title in, or a lien over a business whose assets may be sold
     * or sequestered, as security against payment.
     */
    V_21("21", "Business as security"),

    /**
     * The payer has provided a warrant or warehouse receipts for goods or property to be held
     * or used as security against payment.
     */
    V_23("23", "Warrant or similar (warehouse receipts)"),

    /**
     * The payer has provided a mortgage as security against payment.
     */
    V_24("24", "Mortgage"),

    /**
     * A certificate of insurance has been provided as a guarantee of eventual payment.
     */
    V_45("45", "Insurance certificate"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    PaymentGuaranteeMeansCodeType(String code, String name) {
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
