package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.PaymentMeansChannelCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 4435 : Payment channel code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred4435.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Payment Means Channel Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(PaymentMeansChannelCodeAdapter.class)
public enum PaymentMeansChannelCodeType implements CodeList<PaymentMeansChannelCodeType> {

    /**
     * The payment shall be/has been made via ordinary post.
     */
    V_1("1", "Ordinary post"),

    /**
     * The payment shall be/has been made via air mail.
     */
    V_2("2", "Air mail"),

    /**
     * The payment shall be/has been made via telegraph.
     */
    V_3("3", "Telegraph"),

    /**
     * The payment shall be/has been made via telex.
     */
    V_4("4", "Telex"),

    /**
     * Society for Worldwide Interbank Financial Telecommunications s.c.
     */
    V_5("5", "S.W.I.F.T."),

    /**
     * The payment shall be/has been made via other transmission networks.
     */
    V_6("6", "Other transmission networks"),

    /**
     * The payment shall be/has been made via not defined networks.
     */
    V_7("7", "Networks not defined"),

    /**
     * The payment shall be/has been made via Fedwire.
     */
    V_8("8", "Fedwire"),

    /**
     * Indicates that payment should be made by the bank to the beneficiary or his identified
     * agent, in person.
     */
    V_9("9", "Personal (face-to-face)"),

    /**
     * The payment shall be/has been made via registered air mail.
     */
    V_10("10", "Registered air mail"),

    /**
     * The payment shall be/has been made via registered mail.
     */
    V_11("11", "Registered mail"),

    /**
     * Public courier service.
     */
    V_12("12", "Courier"),

    /**
     * Private messenger service.
     */
    V_13("13", "Messenger"),

    /**
     * Nation wide clearing house for automated payment.
     */
    V_14("14", "National ACH"),

    /**
     * Other than nation wide clearing house system.
     */
    V_15("15", "Other ACH"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    PaymentMeansChannelCodeType(String code, String name) {
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
