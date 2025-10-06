package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.cii.ppf.adapters.InvoiceCodeAdapter;
import io.github.up2jakarta.xml.clv.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 1001 (Invoice Type Code) : Document name code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred1001.htm}
 */
@Generated(value = "PPF", comments = "by A.ABBESSI")
@SubList(value = "1001", type = DocumentCodeType.class)
@Documented(value = "Invoice Type Code", agency = Agency.UN_ECE, version = "2.3")
@Schema(agency = "UN/CEFACT", version = "2.3", date = "2023-07-31")
@XmlJavaTypeAdapter(InvoiceCodeAdapter.class)
public enum InvoiceCodeType implements CodeList<InvoiceCodeType> {

    /**
     * A document which indicates that the customer is claiming credit in a self billing environment.
     */
    V_261(DocumentCodeType.V_261),

    /**
     * (1334) Document/message claiming payment for goods or services supplied under conditions
     * agreed between seller and buyer.
     */
    V_380(DocumentCodeType.V_380),

    /**
     * (1113) Document/message for providing credit information to the relevant party.
     */
    V_381(DocumentCodeType.V_381),

    /**
     * Commercial invoice that includes revised information differing from an earlier submission
     * of the same invoice.
     */
    V_384(DocumentCodeType.V_384),

    /**
     * An invoice to pay amounts for goods and services in advance; these amounts will be subtracted
     * from the final invoice.
     */
    V_386(DocumentCodeType.V_386),

    /**
     * An invoice the invoicee is producing instead of the seller.
     */
    V_389(DocumentCodeType.V_389),

    /**
     * Invoice assigned to a third party for collection.
     */
    V_393(DocumentCodeType.V_393),

    /**
     * Credit note related to assigned invoice(s).
     */
    V_396(DocumentCodeType.V_396),

    /**
     * An invoice produced by the buyer (invoicee) instead of the seller, which indicates that
     * the buyer has to pay amounts for goods and services in advance; these amounts will be
     * subtracted from the final invoice.
     */
    V_500(DocumentCodeType.V_500),

    /**
     * An invoice assigned to a third party for collection, produced by the buyer (invoicee)
     * instead of the seller.
     */
    V_501(DocumentCodeType.V_501),

    /**
     * An invoice assigned to a third party for collection, produced by the buyer (invoicee)
     * which indicates that the buyer is claiming credit.
     */
    V_502(DocumentCodeType.V_502),

    /**
     * A document/message providing credit information to the relevant party which indicates
     * that the buyer has to pay amounts for goods and services in advance; these amounts will
     * be subtracted from the prepayment invoice.
     */
    V_503(DocumentCodeType.V_503),

    @Deprecated
    V_918("918", "Self-billed corrective invoice"),

    @Deprecated
    V_919("919", "Factored corrective Invoice"),

    @Deprecated
    V_920("920", "Self-billed factored corrective invoice"),
    ;

    private final String name;
    private final String code;

    InvoiceCodeType(DocumentCodeType cl) {
        this.code = cl.getCode();
        this.name = cl.getName();
    }

    InvoiceCodeType(String code, String name) {
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
