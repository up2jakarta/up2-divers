package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.PaymentTermsIDAdapter;
import io.github.up2jakarta.xml.clv.Agency;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.Documented;
import io.github.up2jakarta.xml.clv.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 4277 : Payment terms description identifier.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred4277.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Payment Terms Description Identifier", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(PaymentTermsIDAdapter.class)
public enum PaymentTermsIDType implements CodeList<PaymentTermsIDType> {

    /**
     * Draft(s) must be drawn on the issuing bank.
     */
    V_1("1", "Draft(s) drawn on issuing bank"),

    /**
     * Draft(s) must be drawn on the advising bank.
     */
    V_2("2", "Draft(s) drawn on advising bank"),

    /**
     * Draft(s) must be drawn on the reimbursing bank.
     */
    V_3("3", "Draft(s) drawn on reimbursing bank"),

    /**
     * Draft(s) must be drawn on the applicant.
     */
    V_4("4", "Draft(s) drawn on applicant"),

    /**
     * Draft(s) must be drawn on any other drawee.
     */
    V_5("5", "Draft(s) drawn on any other drawee"),

    /**
     * No drafts required.
     */
    V_6("6", "No drafts"),

    /**
     * An indication that the payment means are specified in a commercial account summary.
     */
    V_7("7", "Payment means specified in commercial account summary"),
    ;

    private final String name;
    private final String code;

    PaymentTermsIDType(String code, String name) {
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
