package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.PaymentTermsEventTimeReferenceCodeAdapter;
import io.github.up2jakarta.xml.codelist.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 2475 (Payment Terms Event) : Event time reference code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred2475.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList(value = "2475", type = TimeReferenceCodeType.class)
@Documented(value = "Event Time Reference Code Payment Terms Event", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(PaymentTermsEventTimeReferenceCodeAdapter.class)
public enum PaymentTermsEventTimeReferenceCodeType implements CodeList<PaymentTermsEventTimeReferenceCodeType> {

    /**
     * Payment time reference is date of invoice.
     */
    V_5(TimeReferenceCodeType.V_5),

    /**
     * Payment time reference is date when goods are leaving the sellers factory.
     */
    V_24(TimeReferenceCodeType.V_24),

    /**
     * Date the goods are delivered at agreed place of destination.
     */
    V_29(TimeReferenceCodeType.V_29),

    /**
     * The date of issuance of a bill of lading, consignment note or similar transport document.
     */
    V_45(TimeReferenceCodeType.V_45),

    /**
     * Payment time reference is the date when documents are presented.
     */
    V_71(TimeReferenceCodeType.V_71),
    ;

    private final String name;
    private final String code;

    PaymentTermsEventTimeReferenceCodeType(TimeReferenceCodeType cl) {
        this.code = cl.getCode();
        this.name = cl.getName();
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
