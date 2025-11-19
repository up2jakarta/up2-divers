package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.edi.adapters.TransportModeCodeAdapter;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT R19 : Transport Mode Code.
 * {@see https://unece.org/trade/uncefact/cl-recommendations}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Transport Mode Code", agency = Agency.UN_ECE, version = "2")
@Schema(agency = "UN/CEFACT", version = "2.0", date = "2008-08-23")
@XmlJavaTypeAdapter(TransportModeCodeAdapter.class)
public enum TransportModeCodeType implements CodeList<TransportModeCodeType> {

    /**
     * Transport mode has not been specified.
     *
     * <ul>
     *     <li><u>Notes:</u></li>
     *     <ul>
     *          <li>
     *               1) This code can be used when the mode is not known or when information on is not available
     *               at the time of issuing the document concerned.
     *          </li>
     *     </ul>
     * </ul>
     */
    V_0("0", "Transport mode not specified"),

    /**
     * Transport of goods and/or persons is by sea.
     */
    V_1("1", "Maritime transport"),

    /**
     * Transport of goods and/or persons is by rail.
     */
    V_2("2", "Rail transport"),

    /**
     * Transport of goods and/or persons is by road.
     */
    V_3("3", "Road transport"),

    /**
     * Transport of goods and/or persons is by air.
     */
    V_4("4", "Air transport"),

    /**
     * Method to convey goods is by mail.
     *
     * <ul>
     *     <li><u>Notes:</u></li>
     *     <ul>
     *          <li>
     *               1) This code is provided for practical reasons, despite the fact that mail is not a
     *               genuine mode of transport. In many countries, the value of merchandise exported by mail
     *               is considerable, but the exporter or importer concerned would be unable to state by
     *               which mode postal items had been conveyed.
     *          </li>
     *     </ul>
     * </ul>
     */
    V_5("5", "Mail"),

    /**
     * Method to convey goods and/or persons is by multimodal transport.
     *
     * <ul>
     *     <li><u>Notes:</u></li>
     *     <ul>
     *          <li>
     *               1) This code is provided for practical reasons, despite the fact that multimodal transport
     *               is not a genuine mode of transport.
     *               It can be used when goods are carried by at least two different modes from a place at
     *               which the goods are taken in charge
     *               by a transport operator to a place designated for delivery, on the basis of one transport
     *               contract. (Operations of pick-up and
     *               delivery of goods carried out in the performance of a single mode of transport, as defined
     *               in such a contract, shall not be
     *               considered as multimodal transport).
     *          </li>
     *     </ul>
     * </ul>
     */
    V_6("6", "Multimodal transport"),

    /**
     * Transport of item is via a fixed transport installation.
     *
     * <ul>
     *     <li><u>Notes:</u></li>
     *     <ul>
     *          <li>
     *               1) This code applies to installations for continuous transport such as pipelines, ropeways
     *               and electric power lines.
     *          </li>
     *     </ul>
     * </ul>
     */
    V_7("7", "Fixed transport installations"),

    /**
     * Transport of goods and/or persons is by inland water transport.
     */
    V_8("8", "Inland water transport"),

    /**
     * The mode of transport is not applicable.
     */
    V_9("9", "Transport mode not applicable"),
    ;

    private final String name;
    private final String code;

    TransportModeCodeType(String code, String name) {
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
