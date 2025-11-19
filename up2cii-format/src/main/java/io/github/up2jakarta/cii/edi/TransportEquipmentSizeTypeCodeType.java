package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.edi.adapters.TransportEquipmentSizeTypeCodeAdapter;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 8155 : Equipment size and type description code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred8155.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Equipment Size Type Description Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.2", date = "2008-08-23")
@XmlJavaTypeAdapter(TransportEquipmentSizeTypeCodeAdapter.class)
public enum TransportEquipmentSizeTypeCodeType implements CodeList<TransportEquipmentSizeTypeCodeType> {

    /**
     * A tank coated with dime.
     */
    V_1("1", "Dime coated tank"),

    /**
     * A tank coated with epoxy.
     */
    V_2("2", "Epoxy coated tank"),

    /**
     * A tank capable of holding pressurized goods.
     */
    V_6("6", "Pressurized tank"),

    /**
     * A tank capable of keeping goods refrigerated.
     */
    V_7("7", "Refrigerated tank"),

    /**
     * A tank made of stainless steel.
     */
    V_9("9", "Stainless steel tank"),

    /**
     * A 40 foot refrigerated container that is not actively controlling temperature of the
     * product.
     */
    V_10("10", "Nonworking reefer container 40 ft"),

    /**
     * 80 x 120 cm.
     */
    V_12("12", "Europallet"),

    /**
     * 100 x 120 cm.
     */
    V_13("13", "Scandinavian pallet"),

    /**
     * Non self-propelled vehicle designed for the carriage of cargo so that it can be towed
     * by a motor vehicle.
     */
    V_14("14", "Trailer"),

    /**
     * A 20 foot refrigerated container that is not actively controlling temperature of the
     * product.
     */
    V_15("15", "Nonworking reefer container 20 ft"),

    /**
     * Standard pallet exchangeable following international convention.
     */
    V_16("16", "Exchangeable pallet"),

    /**
     * Non self propelled vehicle without front wheels designed for the carriage of cargo and
     * provided with a kingpin.
     */
    V_17("17", "Semi-trailer"),

    /**
     * A tank container with a length of 20 feet.
     */
    V_18("18", "Tank container 20 feet"),

    /**
     * A tank container with a length of 30 feet.
     */
    V_19("19", "Tank container 30 feet"),

    /**
     * A tank container with a length of 40 feet.
     */
    V_20("20", "Tank container 40 feet"),

    /**
     * A container owned by InterContainer, a European railway subsidiary, with a length of
     * 20 feet.
     */
    V_21("21", "Container IC 20 feet"),

    /**
     * A container owned by InterContainer, a European railway subsidiary, with a length of
     * 30 feet.
     */
    V_22("22", "Container IC 30 feet"),

    /**
     * A container owned by InterContainer, a European railway subsidiary, with a length of
     * 40 feet.
     */
    V_23("23", "Container IC 40 feet"),

    /**
     * A refrigerated tank with a length of 20 feet.
     */
    V_24("24", "Refrigerated tank 20 feet"),

    /**
     * A refrigerated tank with a length of 30 feet.
     */
    V_25("25", "Refrigerated tank 30 feet"),

    /**
     * A refrigerated tank with a length of 40 feet.
     */
    V_26("26", "Refrigerated tank 40 feet"),

    /**
     * A tank container owned by InterContainer, a European railway subsidiary, with a length
     * of 20 feet.
     */
    V_27("27", "Tank container IC 20 feet"),

    /**
     * A tank container owned by InterContainer, a European railway subsidiary, with a length
     * of 30 feet.
     */
    V_28("28", "Tank container IC 30 feet"),

    /**
     * A tank container, owned by InterContainer, a European railway subsidiary, with a length
     * of 40 feet.
     */
    V_29("29", "Tank container IC 40 feet"),

    /**
     * A refrigerated tank owned by InterContainer, a European railway subsidiary, with a length
     * of 20 feet.
     */
    V_30("30", "Refrigerated tank IC 20 feet"),

    /**
     * Temperature controlled container measuring 30 feet.
     */
    V_31("31", "Temperature controlled container 30 ft."),

    /**
     * A refrigerated tank owned by InterContainer, a European railway subsidiary, with a length
     * of 40 feet.
     */
    V_32("32", "Refrigerated tank IC 40 feet"),

    /**
     * A movable case with a length less than 6,15 metres.
     */
    V_33("33", "Movable case: L < 6,15m"),

    /**
     * A movable case with a length between 6,15 metres and 7,82 metres.
     */
    V_34("34", "Movable case: 6,15m < L < 7,82m"),

    /**
     * A movable case with a length between 7,82 metres and 9,15 metres.
     */
    V_35("35", "Movable case: 7,82m < L < 9,15m"),

    /**
     * A movable case with a length between 9,15 metres and 10,90 metres.
     */
    V_36("36", "Movable case: 9,15m < L < 10,90m"),

    /**
     * A movable case with a length between 10,90 metres and 13,75 metres.
     */
    V_37("37", "Movable case: 10,90m < L < 13,75m"),

    /**
     * A steel open top unit of about 1,5 * 1,5 * 2,5 meters for road transport of bulk cargo.
     */
    V_38("38", "Totebin"),

    /**
     * Temperature controlled container measuring 20 feet.
     */
    V_39("39", "Temperature controlled container 20 ft"),

    /**
     * A temperature controlled container measuring 40 feet.
     */
    V_40("40", "Temperature controlled container 40 ft"),

    /**
     * A 30 foot refrigerated (reefer) container that is not actively cooling the product.
     */
    V_41("41", "Non working refrigerated (reefer) container 30ft."),

    /**
     * Two trailers linked together one behind another and pulled by one tractor.
     */
    V_42("42", "Dual trailers"),

    /**
     * An open top container that is 20 feet in internal length.
     */
    V_43("43", "20 ft IL container (open top)"),

    /**
     * A closed top container that is 20 feet in internal length.
     */
    V_44("44", "20 ft IL container (closed top)"),

    /**
     * A closed top container that is 40 feet in internal length.
     */
    V_45("45", "40 ft IL container (closed top)"),

    /**
     * A standard pallet with standard dimensions 80*120cm made of a synthetic material for
     * hygienic reasons.
     */
    V_46("46", "Synthetic pallet ISO 1"),

    /**
     * A standard pallet with standard dimensions 100*120cm made of a synthetic material for
     * hygienic reasons.
     */
    V_47("47", "Synthetic pallet ISO 2"),
    ;

    private final String name;
    private final String code;

    TransportEquipmentSizeTypeCodeType(String code, String name) {
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
