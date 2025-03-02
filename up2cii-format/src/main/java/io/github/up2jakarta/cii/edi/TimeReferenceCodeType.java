package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.TimeReferenceCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 2475 : Event time reference code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred2475.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Event Time Reference Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.2", date = "2008-08-23")
@XmlJavaTypeAdapter(TimeReferenceCodeAdapter.class)
public enum TimeReferenceCodeType implements CodeList<TimeReferenceCodeType> {

    /**
     * Payment time reference is date of order.
     */
    V_1("1", "Date of order"),

    /**
     * Payment time reference is date of confirmation.
     */
    V_2("2", "Date of confirmation"),

    /**
     * Payment time reference is date of contract.
     */
    V_3("3", "Date of contract"),

    /**
     * Payment time reference is date of signature of contract.
     */
    V_4("4", "Date of signature of contract"),

    /**
     * Payment time reference is date of invoice.
     */
    V_5("5", "Date of invoice"),

    /**
     * Payment time reference is date of credit note.
     */
    V_6("6", "Date of credit note"),

    /**
     * Payment time reference is date of present document.
     */
    V_7("7", "Date of present document"),

    /**
     * Payment time reference is date of confirmation received.
     */
    V_8("8", "Date of confirmation of order received"),

    /**
     * Payment time reference is date of invoice received.
     */
    V_9("9", "Date invoice received"),

    /**
     * The date when, at the latest, the bids have to be submitted. This date is required
     * for each call for tender, either in public or private sector.
     */
    V_10("10", "Latest date/time for bid reception"),

    /**
     * Payment time reference is date of credit note received.
     */
    V_11("11", "Date credit note received"),

    /**
     * Payment time reference is date of present document received.
     */
    V_12("12", "Date present document received"),

    /**
     * Payment time reference is date of resale by the buyer.
     */
    V_13("13", "Date of resale by buyer"),

    /**
     * Payment time reference is date of resale proceeds are collected by buyer. "buyer" is
     * a retailer. Retailer will pay his supplier when having sold goods and money received.
     */
    V_14("14", "Date proceeds of resale collected by buyer"),

    /**
     * Payment time reference is date when goods are received by buyer.
     */
    V_21("21", "Date goods received by buyer"),

    /**
     * Payment time reference is date when goods are received by buyer's agent.
     */
    V_22("22", "Date goods received by buyer's agent"),

    /**
     * Payment time reference is date when goods are received by carrier.
     */
    V_23("23", "Date goods received by carrier"),

    /**
     * Payment time reference is date when goods are leaving the sellers factory.
     */
    V_24("24", "Date ex-works"),

    /**
     * Payment time reference is the date when goods are handed over for shipment by the seller
     * or his agent.
     */
    V_25("25", "Date goods handed over for shipment by seller or agent"),

    /**
     * Date the transport arrived at the agreed destination.
     */
    V_26("26", "Date of arrival of transport"),

    /**
     * Date the goods are crossing the border of the exporters country.
     */
    V_27("27", "Date of outward frontier crossing"),

    /**
     * Date the goods are crossing the border of the importers country.
     */
    V_28("28", "Date of inward frontier crossing"),

    /**
     * Date the goods are delivered at agreed place of destination.
     */
    V_29("29", "Date of delivery of goods to establishments/domicile/site"),

    /**
     * Date as per agreement when documentary credit is due for payment.
     */
    V_31("31", "Stipulated date for payment of documentary credit"),

    /**
     * Date as per agreement when documentary credit is accepted.
     */
    V_32("32", "Stipulated date for acceptance of documentary credit"),

    /**
     * Date as per agreement when documentary credit is negotiated.
     */
    V_33("33", "Stipulated date for negotiation of documentary credit"),

    /**
     * Date when documents representing goods are received by buyer .
     */
    V_41("41", "Date of delivery to buyer of documents representing goods"),

    /**
     * Date when documents representing goods are received by buyer's agent.
     */
    V_42("42", "Date of delivery to buyer's agent of documents representing goods"),

    /**
     * Date when documents representing goods are received by carrier.
     */
    V_43("43", "Date of delivery to carrier of documents representing goods"),

    /**
     * Date when documents representing goods are received by intermediary bank.
     */
    V_44("44", "Date of delivery to intermediary bank of documents representing good"),

    /**
     * The date of issuance of a bill of lading, consignment note or similar transport document.
     */
    V_45("45", "Date of bill of lading, consignment note or other transport document"),

    /**
     * Date when goods are expedited to a ship confirmed by mate's received.
     */
    V_46("46", "Date of receipt for loading (mate's receipt)"),

    /**
     * Payment time reference is the date of the negotiable instrument.
     */
    V_47("47", "Date of negotiable instrument (draft, promissory note, bank)"),

    /**
     * Payment time reference is date of receipt of tool dependent initial samples and unlimited
     * absolute bank guarantee of a third party, who is liable to the full amount of the tooling
     * (which is owned by the customer).
     */
    V_48("48", "Date of receipt of tool dependent initial samples plus unlimited absolute bank guarantee plus value added tax"),

    /**
     * Date when the negotiable instrument is due for payment.
     */
    V_52("52", "Due date of negotiable instrument"),

    /**
     * Date when the negotiable instrument is presented or will be presented to drawee.
     */
    V_53("53", "Date of presentation of negotiable instrument"),

    /**
     * Date when the negotiable instrument is accepted or will be accepted by drawee.
     */
    V_54("54", "Date of acceptance of negotiable instrument"),

    /**
     * Payment time reference is date of acceptance of tooling or set of tooling.
     */
    V_55("55", "Date of acceptance of tooling"),

    /**
     * Payment time reference is date of receipt of tooling or set of tooling.
     */
    V_56("56", "Date of receipt of tooling"),

    /**
     * Payment time reference is date of acceptance of first samples produced under production
     * conditions.
     */
    V_57("57", "Date of acceptance of first samples produced under production conditions"),

    /**
     * Payment time reference is the date when work begins.
     */
    V_60("60", "Date of start of work"),

    /**
     * Payment time reference is the date when work ends.
     */
    V_61("61", "Date of end of work"),

    /**
     * Date of temporary acceptance of work until final reception will take place.
     */
    V_62("62", "Date of provisional reception of work"),

    /**
     * Payment time reference is the date of final acceptance of work.
     */
    V_63("63", "Date of final acceptance of work"),

    /**
     * Date of certificate of temporary acceptance of work until final reception will take
     * place.
     */
    V_64("64", "Date of certificate of preliminary acceptance"),

    /**
     * Payment time reference is the date of the certificate of final acceptance.
     */
    V_65("65", "Date of certificate of final acceptance"),

    /**
     * Date specified elsewhere.
     */
    V_66("66", "Specified date"),

    /**
     * The date on which delivery is anticipated to take place.
     */
    V_67("67", "Anticipated delivery date"),

    /**
     * The date on which an action or event becomes effective.
     */
    V_68("68", "Effective date"),

    /**
     * Payment time reference is the date of invoice transmission.
     */
    V_69("69", "Invoice transmission date"),

    /**
     * The date on which a transport document(s) is issued.
     */
    V_70("70", "Date of issue of transport document(s)"),

    /**
     * Payment time reference is the date when documents are presented.
     */
    V_71("71", "Date of presentation of documents"),

    /**
     * Date when a payment was made.
     */
    V_72("72", "Payment date"),

    /**
     * Draft(s) is/are due after a specific number of days after sight.
     */
    V_73("73", "Draft(s) at ... days sight"),

    /**
     * Draft(s) is/are due after a specific number of days after date.
     */
    V_74("74", "Draft(s) at ... days date"),

    /**
     * Draft(s) is/are due after a specific number of days after date of issuance of transport
     * document(s).
     */
    V_75("75", "Draft(s) at ... days after date of issuance of transport document(s)"),

    /**
     * Draft(s) is/are due after a specific number of days after date of presentation of documents.
     */
    V_76("76", "Draft(s) at ... days after date of presentation of documents"),

    /**
     * Draft at specified date.
     */
    V_77("77", "Specified draft date"),

    /**
     * Date when goods clear Customs in the importing country.
     */
    V_78("78", "Customs clearance date (import)"),

    /**
     * Date when goods clear Customs in the exporting country.
     */
    V_79("79", "Customs clearance date (export)"),

    /**
     * Date when a salary payment was made.
     */
    V_80("80", "Date of salary payment"),

    /**
     * Date of shipment as evidenced by the transport document(s).
     */
    V_81("81", "Date of shipment as evidenced by the transport document(s)"),

    /**
     * Date on which a payment is due.
     */
    V_82("82", "Payment due date"),

    /**
     * Payment terms apply from the requested date of delivery.
     */
    V_83("83", "Requested date of delivery"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
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
