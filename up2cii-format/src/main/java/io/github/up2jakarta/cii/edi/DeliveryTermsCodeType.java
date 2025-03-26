package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.DeliveryTermsCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 4053 : Delivery or transport terms description code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred4053.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Delivery Terms Code", agency = Agency.UN_ECE, version = "2020")
@Schema(agency = "UN/CEFACT", version = "3.0", date = "2008-08-23")
@XmlJavaTypeAdapter(DeliveryTermsCodeAdapter.class)
public enum DeliveryTermsCodeType implements CodeList<DeliveryTermsCodeType> {

    /**
     * Indicates that the supplier will arrange delivery of the goods.
     */
    V_1("1", "Delivery arranged by the supplier"),

    /**
     * Code indicating that the logistic service provider has arranged the delivery of goods.
     */
    V_2("2", "Delivery arranged by logistic service provider"),
    CFR("CFR", "Cost and Freight (insert named port of destination)"),
    CIF("CIF", "Cost, Insurance and Freight (insert named port of destination)"),
    CIP("CIP", "Carriage and Insurance Paid to (insert named place of destination)"),
    CPT("CPT", "Carriage Paid To (insert named place of destination)"),
    DAP("DAP", "Delivered At Place (insert named place of destination)"),

    @Deprecated(since = "D23A", forRemoval = true)
    DAT("DAT", "Unknown"),
    DDP("DDP", "Delivered Duty Paid (insert named place of destination)"),

    @Deprecated(since = "D23A", forRemoval = false)
    DPU("DPU", "Delivered At Place Unloaded (insert named place of destination)"),
    EXW("EXW", "Ex Works (insert named place of delivery)"),
    FAS("FAS", "Free Alongside Ship (insert named port of shipment)"),
    FCA("FCA", "Free Carrier (insert named place of delivery)"),
    FOB("FOB", "Free On Board (insert named port of shipment)"),
    ;

    private final String name;
    private final String code;

    DeliveryTermsCodeType(String code, String name) {
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
