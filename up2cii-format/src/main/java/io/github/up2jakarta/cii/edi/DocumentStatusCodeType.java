package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.DocumentStatusCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 1373 : Document status code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred1373.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Document Status Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.9", date = "2008-08-23")
@XmlJavaTypeAdapter(DocumentStatusCodeAdapter.class)
public enum DocumentStatusCodeType implements CodeList<DocumentStatusCodeType> {

    /**
     * The specified document is accepted.
     */
    V_1("1", "Accepted"),

    /**
     * Notice that a specific document will be accompanying the goods.
     */
    V_2("2", "Accompanying goods"),

    /**
     * The specified document is conditionally accepted.
     */
    V_3("3", "Conditionally accepted"),

    /**
     * Notice that a specific document/message will be transmitted via a separate EDI message.
     */
    V_4("4", "To arrive by separate EDI message"),

    /**
     * Notice that the specific document or message is for information only.
     */
    V_5("5", "Information only"),

    /**
     * Notice that a specific document or message will not be sent via EDI.
     */
    V_6("6", "To arrive by manual means"),

    /**
     * Request for a specific message to be formatted and transmitted or a request for a specific
     * document to be raised and sent.
     */
    V_7("7", "To be raised and sent"),

    /**
     * The specified document is rejected.
     */
    V_8("8", "Rejected"),

    /**
     * The document or message is to be printed.
     */
    V_9("9", "To be printed"),

    /**
     * Specific document is currently valid.
     */
    V_10("10", "Document currently valid"),

    /**
     * Specified document is not available.
     */
    V_11("11", "Document not available"),

    /**
     * Customs declaration to which the document is related completed or exhaust the allowance
     * stated on the document. The document is attached to the Customs declaration.
     */
    V_12("12", "Document exhausted by declaration and attached"),

    /**
     * Customs declaration to which the document is related does not complete or exhaust the
     * allowance stated on the document . The document is not attached to the declaration
     * but has already been lodged in the Customs station.
     */
    V_13("13", "Document not exhausted by declaration and attached"),

    /**
     * Customs declaration to which the document is related completed or exhaust the allowance
     * stated on the document. The usage of the document is complete. The document is not
     * attached to the declaration but has already been lodged in the Customs station.
     */
    V_14("14", "Document exhausted by declaration and previously lodged"),

    /**
     * Customs declaration to which the document is related does not complete or exhaust the
     * allowance stated on the document. The document can continue to be used for future declarations
     * until the allowance is exhausted. The document is not attached to the declaration but
     * has already been lodged in the Customs station.
     */
    V_15("15", "Document not exhausted by declaration and previously lodged"),

    /**
     * Specified document is not or cannot be attached.
     */
    V_16("16", "Document not attached"),

    /**
     * Document not attached to the Customs declaration but is attached to the goods.
     */
    V_17("17", "Document with the goods"),

    /**
     * Specified document is attached to the Customs declaration and will be required to be
     * returned to the declarant after Customs endorsement.
     */
    V_18("18", "Document attached, to be returned after endorsement"),

    /**
     * Application has been submitted for that document.
     */
    V_19("19", "Document applied for"),

    /**
     * Indicates that the document has legal validity from the date of receival of the cargo.
     */
    V_20("20", "Received for shipment"),

    /**
     * Indicates that the document has legal validity from the date that cargo is loaded on
     * board a vessel.
     */
    V_21("21", "Shipped on board"),

    /**
     * Message is at status 0.
     */
    V_22("22", "Status 0"),

    /**
     * Message is at status 1.
     */
    V_23("23", "Status 1"),

    /**
     * Message is at status 2.
     */
    V_24("24", "Status 2"),

    /**
     * Message is under development.
     */
    V_25("25", "Message under development"),

    /**
     * Document not to include freight figures.
     */
    V_26("26", "Document not freighted"),

    /**
     * Document to include freight figures.
     */
    V_27("27", "Document freighted"),

    /**
     * The document or message has been archived.
     */
    V_28("28", "Archived"),

    /**
     * The document or message has no official status.
     */
    V_29("29", "Provisional"),

    /**
     * The documents are enclosed in the first transmission.
     */
    V_30("30", "Documents enclosed in the first transmission"),

    /**
     * The documents are enclosed in the second transmission.
     */
    V_31("31", "Documents enclosed in the second transmission"),

    /**
     * The document is not required, waiver of requirement has been issued.
     */
    V_32("32", "Document not required, waiver issued"),

    /**
     * The document is already on file with the party receiving the message.
     */
    V_33("33", "Already on file with receiver of this message"),

    /**
     * The document is in the possession of the sender or sender's agent or representative.
     */
    V_34("34", "Retained by sender of this message, or by sender's agent or representative"),

    /**
     * The document is incomplete.
     */
    V_35("35", "Document incomplete"),

    /**
     * The document has already been submitted.
     */
    V_36("36", "Document previously submitted"),

    /**
     * The document is complete.
     */
    V_37("37", "Document complete"),

    /**
     * The document has been finalised.
     */
    V_38("38", "Final"),

    /**
     * The document or message will not be processed until further release information.
     */
    V_39("39", "On hold"),

    /**
     * The validity of the document is or has been suspended.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_40("40", "Validity suspended"),

    /**
     * The validity of the document is or has been revoked.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_41("41", "Validity revoked"),

    /**
     * The specified document is in error.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_42("42", "In error"),

    /**
     * The document is received.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_43("43", "Received"),

    /**
     * The document is accepted, but has generated warnings.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_44("44", "Accepted with warnings"),

    /**
     * Indicates that the document is being processed.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_45("45", "In process"),

    /**
     * Indicates that the document has been halted pending response to a query.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_46("46", "Under query"),

    /**
     * Indicates that the document has been paid.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_47("47", "Paid"),

    /**
     * Indicates that the document is acknowledged as understood and submitted for further
     * processing.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_48("48", "Acknowledged"),

    /**
     * Indicates that the document is accepted under conditions stated and proceeded accordingly
     * unless disputed.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_49("49", "Conditionally accepted"),

    /**
     * Indicates that the document has been rejected, and a clarification or reason is required.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_50("50", "Rejected, no further processing"),

    /**
     * Notice that a specific document or message will be sent by electronic means.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_51("51", "To arrive by electronic means"),
    ;

    private final String name;
    private final String code;

    DocumentStatusCodeType(String code, String name) {
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
