package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.TransportEquipmentFullnessCodeAdapter;
import io.github.up2jakarta.xml.clv.Agency;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.Documented;
import io.github.up2jakarta.xml.clv.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 8169 : Full or empty indicator code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred8169.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Transport Equipment Fullness Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(TransportEquipmentFullnessCodeAdapter.class)
public enum TransportEquipmentFullnessCodeType implements CodeList<TransportEquipmentFullnessCodeType> {

    /**
     * Indicates that there is more than a quarter of the volume available.
     */
    V_1("1", "More than one quarter volume available"),

    /**
     * Indicates that there is more than a half of the volume available.
     */
    V_2("2", "More than half volume available"),

    /**
     * Indicates that there is more than three quarters of the volume available.
     */
    V_3("3", "More than three quarters volume available"),

    /**
     * Indicates that the object is empty.
     */
    V_4("4", "Empty"),

    /**
     * Indicates that the object is full.
     */
    V_5("5", "Full"),

    /**
     * Indicates that there is no space available in the object.
     */
    V_6("6", "No volume available"),

    /**
     * Indicates that the equipment is fully loaded, and includes a number LCL (Less Than Container
     * Load) consignments.
     */
    V_7("7", "Full, mixed consignment"),

    /**
     * Indicates that the container is fully loaded with a single FCL (Full Container Load)
     * consignment.
     */
    V_8("8", "Full, single consignment"),

    /**
     * Container represents part of a consignment declared on a single Customs declaration
     * (i.e. the Customs declaration covers more than one container).
     */
    V_9("9", "Part load"),

    /**
     * Container represents part of the consignment declared on a single Customs declaration
     * with the remainder being in other containers. Other goods, related to other declarations,
     * are also in the container.
     */
    V_10("10", "Part load mixed consignments"),

    /**
     * Merchandise within a container/package covered by a single invoice.
     */
    V_11("11", "Single invoiced load"),

    /**
     * Merchandise within a container/package covered by more than one invoice.
     */
    V_12("12", "Multi invoiced load"),

    /**
     * A container representing a consignment of goods for one consignee with multiple bill
     * of lading numbers.
     */
    V_13("13", "Full load, multiple bills"),
    ;

    private final String name;
    private final String code;

    TransportEquipmentFullnessCodeType(String code, String name) {
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
