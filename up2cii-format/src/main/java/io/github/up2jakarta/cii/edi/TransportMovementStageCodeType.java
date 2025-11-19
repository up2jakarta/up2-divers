package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.edi.adapters.TransportMovementStageCodeAdapter;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 8051 : Transport stage code qualifier.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred8051.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Transport Movement Stage Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(TransportMovementStageCodeAdapter.class)
public enum TransportMovementStageCodeType implements CodeList<TransportMovementStageCodeType> {

    /**
     * Transport by which goods are moved from or to the frontier, or between inland points.
     */
    V_1("1", "Inland transport"),

    /**
     * Point by which the means of transport are presumed to have left the statistical territory
     * of despatch or to have entered the statistical territory of arrival.
     */
    V_2("2", "At the statistical territory limit"),

    /**
     * Transport by which goods are moved to the place of arrival.
     */
    V_3("3", "At arrival"),

    /**
     * The means of transport used to carry goods that have come under transit procedures of
     * the customs administration to which a transit declaration is being made.
     */
    V_4("4", "Means of transport at transit"),

    /**
     * The means of transport moving into and out of a regulatory territory.
     */
    V_5("5", "Means of transport crossing the regulatory border"),

    /**
     * The means of transport moving into a regulatory territory at the first port of arrival.
     */
    V_6("6", "Means of transport at first port of arrival into regulatory territory"),

    /**
     * Transport by which the goods are moved prior to their main carriage transport.
     */
    V_10("10", "Pre-carriage transport"),

    /**
     * Transport by which goods are moved to the Customs frontier.
     */
    V_11("11", "At border"),

    /**
     * Transport by which goods are moved from the place of departure.
     */
    V_12("12", "At departure"),

    /**
     * Transport by which goods are moved at the place of destination.
     */
    V_13("13", "At destination"),

    /**
     * The fourth carrier of the ordered transport.
     */
    V_15("15", "Main carriage - fourth carrier"),

    /**
     * The fifth carrier of the ordered transport.
     */
    V_16("16", "Main carriage - fifth carrier"),

    /**
     * The sixth carrier of the ordered transport.
     */
    V_17("17", "Main carriage - sixth carrier"),

    /**
     * The seventh carrier of the ordered transport.
     */
    V_18("18", "Main carriage - seventh carrier"),

    /**
     * The eighth carrier of the ordered transport.
     */
    V_19("19", "Main carriage - eighth carrier"),

    /**
     * The primary stage in the movement of cargo from the point of origin to the intended
     * destination.
     */
    V_20("20", "Main-carriage transport"),

    /**
     * The first carrier of the ordered transport when more than one carrier is involved.
     */
    V_21("21", "Main carriage - first carrier"),

    /**
     * The second carrier of the ordered transport when more than one carrier is involved.
     */
    V_22("22", "Main carriage - second carrier"),

    /**
     * The third carrier of the ordered transport when more than one carrier is involved.
     */
    V_23("23", "Main carriage - third carrier"),

    /**
     * Transport by which goods are moved via an inland body of water.
     */
    V_24("24", "Inland waterway transport"),

    /**
     * Carrier responsible from the point of origin to the final delivery destination.
     */
    V_25("25", "Delivery carrier all transport"),

    /**
     * Second transport by which the goods are moved prior to their main carriage transport.
     */
    V_26("26", "Second pre-carriage transport"),

    /**
     * The transport by which the goods are moved to the place of acceptance.
     */
    V_27("27", "Pre-acceptance transport"),

    /**
     * Second transport by which the goods are moved after the main carriage transport.
     */
    V_28("28", "Second on-carriage transport"),

    /**
     * The ninth carrier of the ordered transport.
     */
    V_29("29", "Main carriage - ninth carrier"),

    /**
     * Transport by which the goods are moved after the main carriage transport.
     */
    V_30("30", "On-carriage transport"),

    /**
     * The tenth carrier of the ordered transport.
     */
    V_31("31", "Main carriage - tenth carrier"),

    /**
     * The eleventh carrier of the ordered transport.
     */
    V_32("32", "Main carriage - eleventh carrier"),

    /**
     * The twelfth carrier of the ordered transport.
     */
    V_33("33", "Main carriage - twelfth carrier"),

    /**
     * The movement of a conveyance through the airspace over the territories of a country
     * without landing within the territories of the country.
     */
    V_34("34", "Overflight"),
    ;

    private final String name;
    private final String code;

    TransportMovementStageCodeType(String code, String name) {
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
