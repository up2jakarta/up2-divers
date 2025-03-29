package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.LineStatusCodeAdapter;
import io.github.up2jakarta.xml.codelist.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 1229 (LineStatusCode) : Action code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred1229.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("1229")
@Documented(value = "Action Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.2", date = "2008-08-23")
@XmlJavaTypeAdapter(LineStatusCodeAdapter.class)
public enum LineStatusCodeType implements CodeList<LineStatusCodeType> {

    /**
     * The information is to be or has been added.
     */
    V_1("1", "Added"),

    /**
     * The information is to be or has been deleted.
     */
    V_2("2", "Deleted"),

    /**
     * The information is to be or has been changed.
     */
    V_3("3", "Changed"),

    /**
     * This line item is not affected by the actual message.
     */
    V_4("4", "No action"),

    /**
     * This line item is entirely accepted by the seller.
     */
    V_5("5", "Accepted without amendment"),

    /**
     * This line item is accepted but amended by the seller.
     */
    V_6("6", "Accepted with amendment"),

    /**
     * This line item is not accepted by the seller.
     */
    V_7("7", "Not accepted"),

    /**
     * Code specifying that the message is a schedule only.
     */
    V_8("8", "Schedule only"),

    /**
     * Code specifying that amendments are requested/notified.
     */
    V_9("9", "Amendments"),

    /**
     * This line item is not found in the referenced message.
     */
    V_10("10", "Not found"),

    /**
     * This line is not amended by the buyer.
     */
    V_11("11", "Not amended"),

    /**
     * Code specifying that the line item numbers have changed.
     */
    V_12("12", "Line item numbers changed"),

    /**
     * Buyer has deducted amount from payment.
     */
    V_13("13", "Buyer has deducted amount"),

    /**
     * Buyer has a claim against an outstanding invoice.
     */
    V_14("14", "Buyer claims against invoice"),

    /**
     * Factor has been requested to charge back the outstanding item.
     */
    V_15("15", "Charge back by seller"),

    /**
     * Seller agrees to issue a credit note.
     */
    V_16("16", "Seller will issue credit note"),

    /**
     * New settlement terms have been agreed.
     */
    V_17("17", "Terms changed for new terms"),

    /**
     * Factor agrees to abide by the outcome of negotiations between seller and buyer.
     */
    V_18("18", "Abide outcome of negotiations"),

    /**
     * Seller does not accept validity of dispute.
     */
    V_19("19", "Seller rejects dispute"),

    /**
     * The reported situation is settled.
     */
    V_20("20", "Settlement"),

    /**
     * Code indicating that no delivery will be required.
     */
    V_21("21", "No delivery"),

    /**
     * A request for delivery of a particular quantity of goods to be delivered on a particular
     * date (or within a particular period).
     */
    V_22("22", "Call-off delivery"),

    /**
     * A code used to indicate an amendment suggested by the sender.
     */
    V_23("23", "Proposed amendment"),

    /**
     * Accepted with changes which require no confirmation.
     */
    V_24("24", "Accepted with amendment, no confirmation required"),

    /**
     * The equipment or component has been provisionally repaired.
     */
    V_25("25", "Equipment provisionally repaired"),

    /**
     * Code indicating that the entity is included.
     */
    V_26("26", "Included"),

    /**
     * Upon receipt and verification of documents we shall cover you when due as per your instructions.
     */
    V_27("27", "Upon receipt and verification of documents we shall cover you when due as per your instructions"),

    /**
     * Upon receipt and verification of documents we shall authorize you to debit our account
     * with you when due.
     */
    V_28("28", "Upon receipt and verification of documents we shall authorize you to debit our account with you when due"),

    /**
     * On receipt of your authenticated advice we shall cover you when due as per your instructions.
     */
    V_29("29", "On receipt of your authenticated advice we shall cover you when due as per your instructions"),

    /**
     * On receipt of your authenticated advice we shall authorize you to debit our account
     * with you when due.
     */
    V_30("30", "On receipt of your authenticated advice we shall authorize you to debit our account with you when due"),

    /**
     * On receipt of your authenticated advice we shall credit your account with us when due.
     */
    V_31("31", "On receipt of your authenticated advice we shall credit your account with us when due"),

    /**
     * A credit advice is requested for the direct debit.
     */
    V_32("32", "Credit advice requested for direct debit"),

    /**
     * A credit advice and acknowledgement are requested for the direct debit.
     */
    V_33("33", "Credit advice and acknowledgement for direct debit"),

    /**
     * Request for information.
     */
    V_34("34", "Inquiry"),

    /**
     * Checked.
     */
    V_35("35", "Checked"),

    /**
     * Not checked.
     */
    V_36("36", "Not checked"),

    /**
     * Discontinued.
     */
    V_37("37", "Cancelled"),

    /**
     * Provide a replacement.
     */
    V_38("38", "Replaced"),

    /**
     * Not existing before.
     */
    V_39("39", "New"),

    /**
     * Consent.
     */
    V_40("40", "Agreed"),

    /**
     * Put forward for consideration.
     */
    V_41("41", "Proposed"),

    /**
     * Delivery has taken place.
     */
    V_42("42", "Already delivered"),

    /**
     * Additional subordinate structures will follow the current hierarchy level.
     */
    V_43("43", "Additional subordinate structures will follow"),

    /**
     * No additional subordinate structures will follow the current hierarchy level.
     */
    V_44("44", "Additional subordinate structures will not follow"),

    /**
     * A notification that the result is opposed.
     */
    V_45("45", "Result opposed"),

    /**
     * A notification that an auction was held.
     */
    V_46("46", "Auction held"),

    /**
     * A notification that legal action has been pursued.
     */
    V_47("47", "Legal action pursued"),

    /**
     * A notification that a meeting was held.
     */
    V_48("48", "Meeting held"),

    /**
     * A notification that the result has been set aside.
     */
    V_49("49", "Result set aside"),

    /**
     * A notification that the result has been disputed.
     */
    V_50("50", "Result disputed"),

    /**
     * A notification that a countersuit has been filed.
     */
    V_51("51", "Countersued"),

    /**
     * A notification that an action is awaiting settlement.
     */
    V_52("52", "Pending"),

    /**
     * A notification that a court action will no longer be heard.
     */
    V_53("53", "Court action dismissed"),

    /**
     * The item being referred to has been accepted.
     */
    V_54("54", "Referred item, accepted"),

    /**
     * The item being referred to has been rejected.
     */
    V_55("55", "Referred item, rejected"),

    /**
     * Notification that the statement line is a debit advice.
     */
    V_56("56", "Debit advice statement line"),

    /**
     * Notification that the statement line is a credit advice.
     */
    V_57("57", "Credit advice statement line"),

    /**
     * Notification that the credit advices are grouped.
     */
    V_58("58", "Grouped credit advices"),

    /**
     * Notification that the debit advices are grouped.
     */
    V_59("59", "Grouped debit advices"),

    /**
     * The name is registered.
     */
    V_60("60", "Registered"),

    /**
     * The payment has been denied.
     */
    V_61("61", "Payment denied"),

    /**
     * Approved with modifications.
     */
    V_62("62", "Approved as amended"),

    /**
     * The request has been approved as submitted.
     */
    V_63("63", "Approved as submitted"),

    /**
     * Cancelled due to the lack of activity.
     */
    V_64("64", "Cancelled, no activity"),

    /**
     * Investigation is being done.
     */
    V_65("65", "Under investigation"),

    /**
     * Notification that the initial claim was received.
     */
    V_66("66", "Initial claim received"),

    /**
     * Not in process.
     */
    V_67("67", "Not in process"),

    /**
     * Rejected because it is a duplicate.
     */
    V_68("68", "Rejected, duplicate"),

    /**
     * Rejected but may be resubmitted when corrected.
     */
    V_69("69", "Rejected, resubmit with corrections"),

    /**
     * Pending because of incomplete information.
     */
    V_70("70", "Pending, incomplete"),

    /**
     * Investigation by the field is being done.
     */
    V_71("71", "Under field office investigation"),

    /**
     * Pending awaiting receipt of additional material.
     */
    V_72("72", "Pending, awaiting additional material"),

    /**
     * Pending while awaiting review.
     */
    V_73("73", "Pending, awaiting review"),

    /**
     * Opened again.
     */
    V_74("74", "Reopened"),

    /**
     * This request has been processed by the primary payer and sent to additional payer(s).
     */
    V_75("75", "Processed by primary, forwarded to additional payer(s)"),

    /**
     * This request has been processed by the secondary payer and sent to additional payer(s).
     */
    V_76("76", "Processed by secondary, forwarded to additional payer(s)"),

    /**
     * This request has been processed by the tertiary payer and sent to additional payer(s).
     */
    V_77("77", "Processed by tertiary, forwarded to additional payer(s)"),

    /**
     * A previous payment decision has been reversed.
     */
    V_78("78", "Previous payment decision reversed"),

    /**
     * A request does not belong to this payer but has been forwarded to another payer(s).
     */
    V_79("79", "Not our claim, forwarded to another payer(s)"),

    /**
     * The request has been transferred to the correct insurance carrier for processing.
     */
    V_80("80", "Transferred to correct insurance carrier"),

    /**
     * Payment has not been made and the enclosed response is predetermination pricing only.
     */
    V_81("81", "Not paid, predetermination pricing only"),

    /**
     * The claim is for documentation purposes only, no payment required.
     */
    V_82("82", "Documentation claim"),

    /**
     * Assessed.
     */
    V_83("83", "Reviewed"),

    /**
     * This price was changed.
     */
    V_84("84", "Repriced"),

    /**
     * An official examination has occurred.
     */
    V_85("85", "Audited"),

    /**
     * Payment has been conditionally made.
     */
    V_86("86", "Conditionally paid"),

    /**
     * Reconsideration of the decision has been applied for.
     */
    V_87("87", "On appeal"),

    /**
     * Shut.
     */
    V_88("88", "Closed"),

    /**
     * A subsequent official examination has occurred.
     */
    V_89("89", "Reaudited"),

    /**
     * Issued again.
     */
    V_90("90", "Reissued"),

    /**
     * Reopened and then closed.
     */
    V_91("91", "Closed after reopening"),

    /**
     * Determined again or differently.
     */
    V_92("92", "Redetermined"),

    /**
     * Processed as the first.
     */
    V_93("93", "Processed as primary"),

    /**
     * Processed as the second.
     */
    V_94("94", "Processed as secondary"),

    /**
     * Processed as the third.
     */
    V_95("95", "Processed as tertiary"),

    /**
     * A correction to information previously communicated which contained an error.
     */
    V_96("96", "Correction of error"),

    /**
     * Notification that the credit item is a single credit item of a group of credit items.
     */
    V_97("97", "Single credit item of a group"),

    /**
     * Notification that the debit item is a single debit item of a group of debit items.
     */
    V_98("98", "Single debit item of a group"),

    /**
     * The response is an interim one.
     */
    V_99("99", "Interim response"),

    /**
     * The response is an final one.
     */
    V_100("100", "Final response"),

    /**
     * A debit advice is requested for the transaction.
     */
    V_101("101", "Debit advice requested"),

    /**
     * Advice that the transaction is not impacted.
     */
    V_102("102", "Transaction not impacted"),

    /**
     * The action to take is to notify the patient.
     */
    V_103("103", "Patient to be notified"),

    /**
     * The action to take is to notify the healthcare provider.
     */
    V_104("104", "Healthcare provider to be notified"),

    /**
     * The action to take is to notify the usual general practitioner.
     */
    V_105("105", "Usual general practitioner to be notified"),

    /**
     * An advice without details is requested or notified.
     */
    V_106("106", "Advice without details"),

    /**
     * An advice with details is requested or notified.
     */
    V_107("107", "Advice with details"),

    /**
     * An amendment is requested.
     */
    V_108("108", "Amendment requested"),

    /**
     * Included for information only.
     */
    V_109("109", "For information"),

    /**
     * A code indicating discontinuance or retraction.
     */
    V_110("110", "Withdraw"),

    /**
     * The action / notiification is a change of the delivery date.
     */
    V_111("111", "Delivery date change"),

    /**
     * The action / notification is a change of quantity.
     */
    V_112("112", "Quantity change"),

    /**
     * The identified items have been sold by the distributor to the end customer, and compensation
     * for the loss of inventory value is claimed.
     */
    V_113("113", "Resale and claim"),

    /**
     * The identified items have been sold by the distributor to the end customer.
     */
    V_114("114", "Resale"),

    /**
     * This existing line item becomes available at an earlier date.
     */
    V_115("115", "Prior addition"),

    /**
     * This line has expired.
     */
    V_116("116", "Expired"),

    /**
     * This line is on Hold.
     */
    V_117("117", "Hold"),

    /**
     * This line is open.
     */
    V_118("118", "Open"),

    /**
     * The object or item is to be or has been observed.
     */
    V_119("119", "Observe"),
    ;

    private final String name;
    private final String code;

    LineStatusCodeType(String code, String name) {
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
