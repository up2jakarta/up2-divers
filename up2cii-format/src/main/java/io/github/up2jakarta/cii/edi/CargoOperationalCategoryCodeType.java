package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.CargoOperationalCategoryCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 7085 : Cargo type classification code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred7085.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Cargo Operational Category Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.4", date = "2008-08-23")
@XmlJavaTypeAdapter(CargoOperationalCategoryCodeAdapter.class)
public enum CargoOperationalCategoryCodeType implements CodeList<CargoOperationalCategoryCodeType> {

    /**
     * Printed, typed or written matter including leaflets, pamphlets, certificates etc.,
     * which are not subject to import duties and taxes, restrictions and prohibitions.
     */
    V_1("1", "Documents"),

    /**
     * Imported consignments/items/goods in respect of which Customs duties and other taxes
     * are waived as they are below a value determined by the Customs administration.
     */
    V_2("2", "Low value non-dutiable consignments"),

    /**
     * Imported consignments/items/goods in respect of which Customs duties and other taxes
     * are payable are below a certain amount as determined by the Customs administration.
     */
    V_3("3", "Low value dutiable consignments"),

    /**
     * Imported consignments/items/goods which are determined as having a value above a certain
     * amount fixed by the Customs administration, which may or may not attract duties and
     * taxes.
     */
    V_4("4", "High value consignments"),

    /**
     * Non-containerized cargo which cannot be categorized by any of the other nature of cargo
     * code.
     */
    V_5("5", "Other non-containerized"),

    /**
     * Vehicles which are not stowed in containers.
     */
    V_6("6", "Vehicles"),

    /**
     * Cargo transported or to be transported on roll-on roll-off vessels and which is transportable
     * on its own wheels or stowed on special heavy duty trailers.
     */
    V_7("7", "Roll-on roll-off"),

    /**
     * Non-containerized cargo which is palletized.
     */
    V_8("8", "Palletized"),

    /**
     * Cargo stowed or to be stowed in a container.
     */
    V_9("9", "Containerized"),

    /**
     * Non-containerized cargo stowed in vessels' holds.
     */
    V_10("10", "Breakbulk"),

    /**
     * Cargo with dangerous properties, according to appropriate dangerous goods regulations.
     */
    V_11("11", "Hazardous cargo"),

    /**
     * Cargo of a general nature, not otherwise specified.
     */
    V_12("12", "General cargo"),

    /**
     * Cargo in liquid form.
     */
    V_13("13", "Liquid cargo"),

    /**
     * Cargo transported under specified temperature conditions.
     */
    V_14("14", "Temperature controlled cargo"),

    /**
     * Cargo is an environmental pollutant.
     */
    V_15("15", "Environmental pollutant cargo"),

    /**
     * Cargo which is not hazardous.
     */
    V_16("16", "Not-hazardous cargo"),

    /**
     * Cargo transported under diplomatic conditions.
     */
    V_17("17", "Diplomatic"),

    /**
     * Cargo for military purposes.
     */
    V_18("18", "Military"),

    /**
     * Cargo that is objectionable to human senses.
     */
    V_19("19", "Obnoxious"),

    /**
     * Cargo that has at least one non-standard dimension.
     */
    V_20("20", "Out of gauge"),

    /**
     * Cargo consisting of household goods and personal effects.
     */
    V_21("21", "Household goods and personal effects"),

    /**
     * Cargo of frozen products.
     */
    V_22("22", "Frozen cargo"),

    /**
     * No cargo, means of transport is carrying only ballast.
     */
    V_23("23", "Ballast only"),

    /**
     * Incompatible cargo to be transported / stored with other types of cargo.
     */
    V_24("24", "Incompatible cargo"),

    /**
     * Cargo of deep-frozen products.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_25("25", "Deep-frozen cargo"),
    ;

    private final String name;
    private final String code;

    CargoOperationalCategoryCodeType(String code, String name) {
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
