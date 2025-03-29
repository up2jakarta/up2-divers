package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.DimensionTypeCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 6145 : Dimension type code qualifier.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred6145.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Dimension Type Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.3", date = "2008-08-23")
@XmlJavaTypeAdapter(DimensionTypeCodeAdapter.class)
public enum DimensionTypeCodeType implements CodeList<DimensionTypeCodeType> {

    /**
     * The dimension expressed in a gross value.
     */
    V_1("1", "Gross dimensions"),

    /**
     * The dimension of the goods including the packaging.
     */
    V_2("2", "Package dimensions (including goods)"),

    /**
     * The dimension of a pallet excluding the goods.
     */
    V_3("3", "Pallet dimensions (excluding goods)"),

    /**
     * The dimension of a pallet including the goods.
     */
    V_4("4", "Pallet dimensions (including goods)"),

    /**
     * The dimension in the length that the cargo exceeds the standard length at the front
     * of an equipment.
     */
    V_5("5", "Off-standard dimension front"),

    /**
     * The dimension in the length that the cargo exceeds the standard length at the back of
     * an equipment.
     */
    V_6("6", "Off-standard dimension back"),

    /**
     * The dimension in the width that the cargo exceeds the standard width at the right side
     * of an equipment.
     */
    V_7("7", "Off-standard dimension right"),

    /**
     * The dimension in the width that the cargo exceeds the standard width at the left side
     * of an equipment.
     */
    V_8("8", "Off-standard dimension left"),

    /**
     * The dimensions that the cargo exceeds the standard dimensions.
     */
    V_9("9", "Off-standard dimension general"),

    /**
     * The external dimensions of transport equipment.
     */
    V_10("10", "External equipment dimension"),

    /**
     * The internal dimensions of equipment.
     */
    V_11("11", "Internal equipment dimensions"),

    /**
     * Dimensions of the damaged area.
     */
    V_12("12", "Damage dimensions"),

    /**
     * The dimension in the height that the cargo exceeds the standard height at the top of
     * a piece of equipment.
     */
    V_13("13", "Off-standard dimensions height"),

    /**
     * Dimensions (width and height) of the equipment door.
     */
    V_14("14", "Equipment door dimensions"),

    /**
     * The dimension of the width that the cargo exceeds the standard width of a piece of equipment.
     */
    V_15("15", "Off-standard dimension width"),

    /**
     * The dimension of the length that the cargo exceeds the standard length of a piece of
     * equipment.
     */
    V_16("16", "Off-standard dimension length"),

    /**
     * Total height of multiple pieces of equipment transported as a single unit (e.g. bundles
     * of folded flat racks).
     */
    V_17("17", "Bundled equipment total height"),

    /**
     * Actual height of equipment. To be specified for containers if actual height is not defined
     * by a specific ISO size type code.
     */
    V_18("18", "Equipment off-standard dimension height, actual"),

    /**
     * Height of equipment in folded condition (e.g. flat rack with end walls folded).
     */
    V_19("19", "Folded equipment height"),

    /**
     * Actual height of adjustable equipment (e.g. flat rack with telescopic corner posts).
     */
    V_20("20", "Adjustable equipment height"),

    /**
     * Floor height of supporting equipment where un-containerized cargo is stowed on.
     */
    V_21("21", "Equipment floor height"),

    /**
     * Container's actual width at corner post. To be specified if different than 8 feet.
     */
    V_22("22", "Container off-standard dimension width at corner posts"),

    /**
     * Container's actual width of body. To be specified if different than 8 feet.
     */
    V_23("23", "Container off-standard dimension width of body"),

    /**
     * The gross dimensions of a transport unit.
     */
    V_24("24", "Transport unit gross dimensions"),
    ;

    private final String name;
    private final String code;

    DimensionTypeCodeType(String code, String name) {
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
