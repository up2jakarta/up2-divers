package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.TimeReferenceCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 2475 : Event time reference code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred2475.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Event Time Reference Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.2", date = "2008-08-23")
@XmlJavaTypeAdapter(TimeReferenceCodeAdapter.class)
public enum TimeReferenceCodeType implements CodeList<TimeReferenceCodeType> {

    /**
     * Payment time reference is date of order.
     */
    @Deprecated(forRemoval = true)
    V_1("1", "Date of order"),

    /**
     * Payment time reference is date of confirmation.
     */
    @Deprecated(forRemoval = true)
    V_2("2", "Date of confirmation"),

    /**
     * Payment time reference is date of contract.
     */
    @Deprecated(forRemoval = true)
    V_3("3", "Date of contract"),

    /**
     * Payment time reference is date of signature of contract.
     */
    @Deprecated(forRemoval = true)
    V_4("4", "Date of signature of contract"),

    /**
     * Payment time reference is date of invoice.
     */
    V_5("5", "Date of invoice"),

    /**
     * Payment time reference is date of credit note.
     */
    @Deprecated(forRemoval = true)
    V_6("6", "Date of credit note"),

    /**
     * Payment time reference is date of present document.
     */
    @Deprecated(forRemoval = true)
    V_7("7", "Date of present document"),

    /**
     * Payment time reference is date of confirmation received.
     */
    @Deprecated(forRemoval = true)
    V_8("8", "Date of confirmation of order received"),

    /**
     * Payment time reference is date of invoice received.
     */
    @Deprecated(forRemoval = true)
    V_9("9", "Date invoice received"),

    /**
     * The date when, at the latest, the bids have to be submitted. This date is required for
     * each call for tender, either in public or private sector.
     */
    @Deprecated(forRemoval = true)
    V_10("10", "Latest date/time for bid reception"),

    /**
     * Payment time reference is date of credit note received.
     */
    @Deprecated(forRemoval = true)
    V_11("11", "Date credit note received"),

    /**
     * Payment time reference is date of present document received.
     */
    @Deprecated(forRemoval = true)
    V_12("12", "Date present document received"),

    /**
     * Payment time reference is date of resale by the buyer.
     */
    @Deprecated(forRemoval = true)
    V_13("13", "Date of resale by buyer"),

    /**
     * Payment time reference is date of resale proceeds are collected by buyer. "buyer" is
     * a retailer. Retailer will pay his supplier when having sold goods and money received.
     */
    @Deprecated(forRemoval = true)
    V_14("14", "Date proceeds of resale collected by buyer"),

    /**
     * Payment time reference is date when goods are received by buyer.
     */
    @Deprecated(forRemoval = true)
    V_21("21", "Date goods received by buyer"),

    /**
     * Payment time reference is date when goods are received by buyer's agent.
     */
    @Deprecated(forRemoval = true)
    V_22("22", "Date goods received by buyer's agent"),

    /**
     * Payment time reference is date when goods are received by carrier.
     */
    @Deprecated(forRemoval = true)
    V_23("23", "Date goods received by carrier"),

    /**
     * Payment time reference is date when goods are leaving the sellers factory.
     */
    @Deprecated(forRemoval = true)
    V_24("24", "Date ex-works"),

    /**
     * Payment time reference is the date when goods are handed over for shipment by the seller
     * or his agent.
     */
    @Deprecated(forRemoval = true)
    V_25("25", "Date goods handed over for shipment by seller or agent"),

    /**
     * Date the transport arrived at the agreed destination.
     */
    @Deprecated(forRemoval = true)
    V_26("26", "Date of arrival of transport"),

    /**
     * Date the goods are crossing the border of the exporters country.
     */
    @Deprecated(forRemoval = true)
    V_27("27", "Date of outward frontier crossing"),

    /**
     * Date the goods are crossing the border of the importers country.
     */
    @Deprecated(forRemoval = true)
    V_28("28", "Date of inward frontier crossing"),

    /**
     * Date the goods are delivered at agreed place of destination.
     */
    V_29("29", "Date of delivery of goods to establishments/domicile/site"),

    /**
     * Date as per agreement when documentary credit is due for payment.
     */
    @Deprecated(forRemoval = true)
    V_31("31", "Stipulated date for payment of documentary credit"),

    /**
     * Date as per agreement when documentary credit is accepted.
     */
    @Deprecated(forRemoval = true)
    V_32("32", "Stipulated date for acceptance of documentary credit"),

    /**
     * Date as per agreement when documentary credit is negotiated.
     */
    @Deprecated(forRemoval = true)
    V_33("33", "Stipulated date for negotiation of documentary credit"),

    /**
     * Date when documents representing goods are received by buyer .
     */
    @Deprecated(forRemoval = true)
    V_41("41", "Date of delivery to buyer of documents representing goods"),

    /**
     * Date when documents representing goods are received by buyer's agent.
     */
    @Deprecated(forRemoval = true)
    V_42("42", "Date of delivery to buyer's agent of documents representing goods"),

    /**
     * Date when documents representing goods are received by carrier.
     */
    @Deprecated(forRemoval = true)
    V_43("43", "Date of delivery to carrier of documents representing goods"),

    /**
     * Date when documents representing goods are received by intermediary bank.
     */
    @Deprecated(forRemoval = true)
    V_44("44", "Date of delivery to intermediary bank of documents representing good"),

    /**
     * The date of issuance of a bill of lading, consignment note or similar transport document.
     */
    @Deprecated(forRemoval = true)
    V_45("45", "Date of bill of lading, consignment note or other transport document"),

    /**
     * Date when goods are expedited to a ship confirmed by mate's received.
     */
    @Deprecated(forRemoval = true)
    V_46("46", "Date of receipt for loading (mate's receipt)"),

    /**
     * Payment time reference is the date of the negotiable instrument.
     */
    @Deprecated(forRemoval = true)
    V_47("47", "Date of negotiable instrument (draft, promissory note, bank)"),

    /**
     * Payment time reference is date of receipt of tool dependent initial samples and unlimited
     * absolute bank guarantee of a third party, who is liable to the full amount of the tooling
     * (which is owned by the customer).
     */
    @Deprecated(forRemoval = true)
    V_48("48", "Date of receipt of tool dependent initial samples plus unlimited absolute bank guarantee plus value added tax"),

    /**
     * Date when the negotiable instrument is due for payment.
     */
    @Deprecated(forRemoval = true)
    V_52("52", "Due date of negotiable instrument"),

    /**
     * Date when the negotiable instrument is presented or will be presented to drawee.
     */
    @Deprecated(forRemoval = true)
    V_53("53", "Date of presentation of negotiable instrument"),

    /**
     * Date when the negotiable instrument is accepted or will be accepted by drawee.
     */
    @Deprecated(forRemoval = true)
    V_54("54", "Date of acceptance of negotiable instrument"),

    /**
     * Payment time reference is date of acceptance of tooling or set of tooling.
     */
    @Deprecated(forRemoval = true)
    V_55("55", "Date of acceptance of tooling"),

    /**
     * Payment time reference is date of receipt of tooling or set of tooling.
     */
    @Deprecated(forRemoval = true)
    V_56("56", "Date of receipt of tooling"),

    /**
     * Payment time reference is date of acceptance of first samples produced under production
     * conditions.
     */
    @Deprecated(forRemoval = true)
    V_57("57", "Date of acceptance of first samples produced under production conditions"),

    /**
     * Payment time reference is the date when work begins.
     */
    @Deprecated(forRemoval = true)
    V_60("60", "Date of start of work"),

    /**
     * Payment time reference is the date when work ends.
     */
    @Deprecated(forRemoval = true)
    V_61("61", "Date of end of work"),

    /**
     * Date of temporary acceptance of work until final reception will take place.
     */
    @Deprecated(forRemoval = true)
    V_62("62", "Date of provisional reception of work"),

    /**
     * Payment time reference is the date of final acceptance of work.
     */
    @Deprecated(forRemoval = true)
    V_63("63", "Date of final acceptance of work"),

    /**
     * Date of certificate of temporary acceptance of work until final reception will take
     * place.
     */
    @Deprecated(forRemoval = true)
    V_64("64", "Date of certificate of preliminary acceptance"),

    /**
     * Payment time reference is the date of the certificate of final acceptance.
     */
    @Deprecated(forRemoval = true)
    V_65("65", "Date of certificate of final acceptance"),

    /**
     * Date specified elsewhere.
     */
    @Deprecated(forRemoval = true)
    V_66("66", "Specified date"),

    /**
     * The date on which delivery is anticipated to take place.
     */
    @Deprecated(forRemoval = true)
    V_67("67", "Anticipated delivery date"),

    /**
     * The date on which an action or event becomes effective.
     */
    @Deprecated(forRemoval = true)
    V_68("68", "Effective date"),

    /**
     * Payment time reference is the date of invoice transmission.
     */
    @Deprecated(forRemoval = true)
    V_69("69", "Invoice transmission date"),

    /**
     * The date on which a transport document(s) is issued.
     */
    @Deprecated(forRemoval = true)
    V_70("70", "Date of issue of transport document(s)"),

    /**
     * Payment time reference is the date when documents are presented.
     */
    @Deprecated(forRemoval = true)
    V_71("71", "Date of presentation of documents"),

    /**
     * Date when a payment was made.
     */
    V_72("72", "Payment date"),

    /**
     * Draft(s) is/are due after a specific number of days after sight.
     */
    @Deprecated(forRemoval = true)
    V_73("73", "Draft(s) at ... days sight"),

    /**
     * Draft(s) is/are due after a specific number of days after date.
     */
    @Deprecated(forRemoval = true)
    V_74("74", "Draft(s) at ... days date"),

    /**
     * Draft(s) is/are due after a specific number of days after date of issuance of transport
     * document(s).
     */
    @Deprecated(forRemoval = true)
    V_75("75", "Draft(s) at ... days after date of issuance of transport document(s)"),

    /**
     * Draft(s) is/are due after a specific number of days after date of presentation of documents.
     */
    @Deprecated(forRemoval = true)
    V_76("76", "Draft(s) at ... days after date of presentation of documents"),

    /**
     * Draft at specified date.
     */
    @Deprecated(forRemoval = true)
    V_77("77", "Specified draft date"),

    /**
     * Date when goods clear Customs in the importing country.
     */
    @Deprecated(forRemoval = true)
    V_78("78", "Customs clearance date (import)"),

    /**
     * Date when goods clear Customs in the exporting country.
     */
    @Deprecated(forRemoval = true)
    V_79("79", "Customs clearance date (export)"),

    /**
     * Date when a salary payment was made.
     */
    @Deprecated(forRemoval = true)
    V_80("80", "Date of salary payment"),

    /**
     * Date of shipment as evidenced by the transport document(s).
     */
    @Deprecated(forRemoval = true)
    V_81("81", "Date of shipment as evidenced by the transport document(s)"),

    /**
     * Date on which a payment is due.
     */
    @Deprecated(forRemoval = true)
    V_82("82", "Payment due date"),

    /**
     * Payment terms apply from the requested date of delivery.
     */
    @Deprecated(forRemoval = true)
    V_83("83", "Requested date of delivery"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    @Deprecated(forRemoval = true)
    ZZZ("ZZZ", "Other reference date agreed upon between the parties"),
    ;

    private final String name;
    private final String code;

    TimeReferenceCodeType(String code, String name) {
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
