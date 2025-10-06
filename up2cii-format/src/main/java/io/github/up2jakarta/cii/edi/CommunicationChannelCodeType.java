package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.CommunicationChannelCodeAdapter;
import io.github.up2jakarta.xml.clv.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 3155 (CommunicationChannelCode) : Communication means type code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred3155.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("3155")
@Documented(value = "Communication Means Type Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.4", date = "2008-08-23")
@XmlJavaTypeAdapter(CommunicationChannelCodeAdapter.class)
public enum CommunicationChannelCodeType implements CodeList<CommunicationChannelCodeType> {

    /**
     * A process that, on demand, connects two or more data terminal equipments and permits
     * the exclusive use of a data circuit between them until the connection is released (ISO).
     */
    AA("AA", "Circuit switching"),

    /**
     * Communications number assigned by Societe Internationale de Telecommunications Aeronautiques
     * (SITA).
     */
    AB("AB", "SITA"),

    /**
     * Communications number assigned by Aeronautical Radio Inc.
     */
    AC("AC", "ARINC"),

    /**
     * AT&amp;T mailbox identifier.
     */
    AD("AD", "AT&T mailbox"),

    /**
     * Peripheral device identification.
     */
    AE("AE", "Peripheral device"),

    /**
     * The switched telecommunications network of the United States Department of Defense.
     */
    AF("AF", "U.S. Defense Switched Network"),

    /**
     * The switched telecommunications network of the United States government.
     */
    AG("AG", "U.S. federal telecommunications system"),

    /**
     * Data exchange via the World Wide Web.
     */
    AH("AH", "World Wide Web"),

    /**
     * Identifies that portion of an international telephone number representing the country
     * code to be used when calling internationally.
     */
    AI("AI", "International calling country code"),

    /**
     * Identifies the alternate telephone number.
     */
    AJ("AJ", "Alternate telephone"),

    /**
     * Code that identifies the communications number for the online videotex service.
     */
    AK("AK", "Videotex number"),

    /**
     * Identifies the cellular phone number.
     */
    AL("AL", "Cellular phone"),

    /**
     * The international telephone direct line number.
     */
    AM("AM", "International telephone direct line"),

    /**
     * ODETTE File Transfer Protocol.
     */
    AN("AN", "O.F.T.P. (ODETTE File Transfer Protocol)"),

    /**
     * Identification of the Uniform Resource Location (URL) Synonym: World wide web address.
     */
    AO("AO", "Uniform Resource Location (URL)"),

    /**
     * VHF radio telephone.
     */
    AP("AP", "Very High Frequency (VHF) radio telephone"),

    /**
     * The X.400 address accepting information in the body text of a message.
     */
    AQ("AQ", "X.400 address for mail text"),

    /**
     * Address capable of receiving messages in accordance with the EDIINT/AS1 protocol for
     * MIME based EDI .
     */
    AR("AR", "AS1 address"),

    /**
     * Address capable of receiving messages in accordance with the EDIINT/AS2 protocol.
     */
    AS("AS", "AS2 address"),

    /**
     * Address capable of receiving messages in accordance with the EDIINT/AS3 protocol.
     */
    AT("AT", "AS3 address"),

    /**
     * Address capable for receiving message in accordance with the File Transfer Protocol
     * (IETF RFC 959 et. al.).
     */
    AU("AU", "File Transfer Protocol"),

    /**
     * Contact number based on Inmarsat.
     */
    AV("AV", "Inmarsat call number"),

    /**
     * Contact number for radio communication based on call sign.
     */
    AW("AW", "Radio communication call sign"),

    /**
     * The communication number identifies a cable address.
     */
    CA("CA", "Cable address"),

    /**
     * Number identifying the service and service user.
     */
    EI("EI", "EDI transmission"),

    /**
     * Exchange of mail by electronic means.
     */
    EM("EM", "Electronic mail"),

    /**
     * Telephone extension.
     */
    EX("EX", "Extension"),

    /**
     * According to ISO.
     */
    FT("FT", "File transfer access method"),

    /**
     * Device used for transmitting and reproducing fixed graphic material (as printing) by
     * means of signals over telephone lines or other electronic transmission media.
     */
    FX("FX", "Telefax"),

    /**
     * The communication number identifies a GEIS mailbox.
     */
    GM("GM", "GEIS (General Electric Information Service) mailbox"),

    /**
     * The communication number identifies an IBM IE mailbox.
     */
    IE("IE", "IBM information exchange"),

    /**
     * Internal mail address/number.
     */
    IM("IM", "Internal mail"),

    /**
     * Postal service document delivery.
     */
    MA("MA", "Mail"),

    /**
     * The communication number identifies a postbox.
     */
    PB("PB", "Postbox number"),

    /**
     * The process of routing and transferring data by means of addressed packets so that a
     * channel is occupied only during the transmission; upon completion of the transmission
     * the channel is made available for the transfer of other packets (ISO).
     */
    PS("PS", "Packet switching"),

    /**
     * Communications address assigned by Society for Worldwide Interbank Financial Telecommunications
     * s.c.
     */
    SW("SW", "S.W.I.F.T."),

    /**
     * Voice/data transmission by telephone.
     */
    TE("TE", "Telephone"),

    /**
     * Text transmission via telegraph.
     */
    TG("TG", "Telegraph"),

    /**
     * Transmission of text/data via telex.
     */
    TL("TL", "Telex"),

    /**
     * Transmission of text/data via telemail.
     */
    TM("TM", "Telemail"),

    /**
     * Transmission of text/data via teletext.
     */
    TT("TT", "Teletext"),

    /**
     * Communication service involving Teletypewriter machines connected by wire or electronic
     * transmission media. Teletypewriter machines are the devices used to send and receive
     * signals and produce hardcopy from them.
     */
    TX("TX", "TWX"),

    /**
     * The X.400 address.
     */
    XF("XF", "X.400 address"),

    /**
     * Identifies that the communication number is for a pager.
     */
    XG("XG", "Pager"),

    /**
     * The international telephone switchboard number.
     */
    XH("XH", "International telephone switchboard"),

    /**
     * The national telephone direct line number.
     */
    XI("XI", "National telephone direct line"),

    /**
     * The national telephone switchboard number.
     */
    XJ("XJ", "National telephone switchboard"),
    ;

    private final String name;
    private final String code;

    CommunicationChannelCodeType(String code, String name) {
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
