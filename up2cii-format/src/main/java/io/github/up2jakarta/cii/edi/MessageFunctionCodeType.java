package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.MessageFunctionCodeAdapter;
import io.github.up2jakarta.xml.codelist.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 1225 (MessageFunctionTypeCode) : Message function code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred1225.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("1225")
@Documented(value = "Message Function Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.3", date = "2008-08-23")
@XmlJavaTypeAdapter(MessageFunctionCodeAdapter.class)
public enum MessageFunctionCodeType implements CodeList<MessageFunctionCodeType> {

    /**
     * Message cancelling a previous transmission for a given transaction.
     */
    V_1("1", "Cancellation"),

    /**
     * Message containing items to be added.
     */
    V_2("2", "Addition"),

    /**
     * Message containing items to be deleted.
     */
    V_3("3", "Deletion"),

    /**
     * Message containing items to be changed.
     */
    V_4("4", "Change"),

    /**
     * Message replacing a previous message.
     */
    V_5("5", "Replace"),

    /**
     * Message confirming the details of a previous transmission where such confirmation is
     * required or recommended under the terms of a trading partner agreement.
     */
    V_6("6", "Confirmation"),

    /**
     * The message is a duplicate of a previously generated message.
     */
    V_7("7", "Duplicate"),

    /**
     * Code indicating that the referenced message is a status.
     */
    V_8("8", "Status"),

    /**
     * Initial transmission related to a given transaction.
     */
    V_9("9", "Original"),

    /**
     * Message whose reference number is not filed.
     */
    V_10("10", "Not found"),

    /**
     * Message responding to a previous message or document.
     */
    V_11("11", "Response"),

    /**
     * Message indicating that the referenced message was received but not yet processed.
     */
    V_12("12", "Not processed"),

    /**
     * Code indicating that the referenced message is a request.
     */
    V_13("13", "Request"),

    /**
     * Code indicating that the information contained in the message is an advance notification
     * of information to follow.
     */
    V_14("14", "Advance notification"),

    /**
     * Repeated message transmission for reminding purposes.
     */
    V_15("15", "Reminder"),

    /**
     * Message content is a proposal.
     */
    V_16("16", "Proposal"),

    /**
     * Referenced transaction cancelled, reissued message will follow.
     */
    V_17("17", "Cancel, to be reissued"),

    /**
     * New issue of a previous message (maybe cancelled).
     */
    V_18("18", "Reissue"),

    /**
     * Change information submitted by buyer but initiated by seller.
     */
    V_19("19", "Seller initiated change"),

    /**
     * Message to replace the heading of a previous message.
     */
    V_20("20", "Replace heading section only"),

    /**
     * Message to replace item detail and summary of a previous message.
     */
    V_21("21", "Replace item detail and summary only"),

    /**
     * Final message in a related series of messages together making up a commercial, administrative
     * or transport transaction.
     */
    V_22("22", "Final transmission"),

    /**
     * Message not to be processed until further release information.
     */
    V_23("23", "Transaction on hold"),

    /**
     * Delivery schedule message only used to transmit short-term delivery instructions.
     */
    V_24("24", "Delivery instruction"),

    /**
     * Delivery schedule message only used to transmit long-term schedule information.
     */
    V_25("25", "Forecast"),

    /**
     * Combination of codes '24' and '25'.
     */
    V_26("26", "Delivery instruction and forecast"),

    /**
     * Message to inform that the referenced message is not accepted by the recipient.
     */
    V_27("27", "Not accepted"),

    /**
     * Message accepted but amended in heading section.
     */
    V_28("28", "Accepted, with amendment in heading section"),

    /**
     * Referenced message is entirely accepted.
     */
    V_29("29", "Accepted without amendment"),

    /**
     * Referenced message is accepted but amended in detail section.
     */
    V_30("30", "Accepted, with amendment in detail section"),

    /**
     * Indicates that the message is a copy of an original message that has been sent, e.g.
     * for action or information.
     */
    V_31("31", "Copy"),

    /**
     * A message releasing an existing referenced message for action to the receiver.
     */
    V_32("32", "Approval"),

    /**
     * Message changing the referenced message heading section.
     */
    V_33("33", "Change in heading section"),

    /**
     * The referenced message is accepted but amended.
     */
    V_34("34", "Accepted with amendment"),

    /**
     * Change-free transmission of a message previously sent.
     */
    V_35("35", "Retransmission"),

    /**
     * Message changing referenced detail section.
     */
    V_36("36", "Change in detail section"),

    /**
     * Reversal of a previously posted debit.
     */
    V_37("37", "Reversal of a debit"),

    /**
     * Reversal of a previously posted credit.
     */
    V_38("38", "Reversal of a credit"),

    /**
     * Code indicating that the referenced message is reversing a cancellation of a previous
     * transmission for a given transaction.
     */
    V_39("39", "Reversal for cancellation"),

    /**
     * The message is given to inform the recipient to delete the referenced transaction.
     */
    V_40("40", "Request for deletion"),

    /**
     * Last of series of call-offs.
     */
    V_41("41", "Finishing/closing order"),

    /**
     * Message confirming a transaction previously agreed via other means (e.g. phone).
     */
    V_42("42", "Confirmation via specific means"),

    /**
     * Message already transmitted via another communication channel. This transmission is
     * to provide electronically processable data only.
     */
    V_43("43", "Additional transmission"),

    /**
     * Message accepted without reserves.
     */
    V_44("44", "Accepted without reserves"),

    /**
     * Message accepted with reserves.
     */
    V_45("45", "Accepted with reserves"),

    /**
     * Message content is provisional.
     */
    V_46("46", "Provisional"),

    /**
     * Message content is definitive.
     */
    V_47("47", "Definitive"),

    /**
     * Message to inform that the previous message is received, but it cannot be processed
     * due to regulations, laws, etc.
     */
    V_48("48", "Accepted, contents rejected"),

    /**
     * The reported dispute is settled.
     */
    V_49("49", "Settled dispute"),

    /**
     * Message withdrawing a previously approved message.
     */
    V_50("50", "Withdraw"),

    /**
     * Message authorising a message or transaction(s).
     */
    V_51("51", "Authorisation"),

    /**
     * A code used to indicate an amendment suggested by the sender.
     */
    V_52("52", "Proposed amendment"),

    /**
     * Code indicating the message is to be considered as a test.
     */
    V_53("53", "Test"),

    /**
     * A subset of the original.
     */
    V_54("54", "Extract"),

    /**
     * The receiver may use the notification information for analysis only.
     */
    V_55("55", "Notification only"),

    /**
     * An advice that items have been booked in the ledger.
     */
    V_56("56", "Advice of ledger booked items"),

    /**
     * An advice that items are pending to be booked in the ledger.
     */
    V_57("57", "Advice of items pending to be booked in the ledger"),

    /**
     * A pre-advice that items require further information.
     */
    V_58("58", "Pre-advice of items requiring further information"),

    /**
     * A pre-advice of items.
     */
    V_59("59", "Pre-adviced items"),

    /**
     * Code indicating the fact that no action has taken place since the last message.
     */
    V_60("60", "No action since last message"),

    /**
     * The message function is a complete schedule.
     */
    V_61("61", "Complete schedule"),

    /**
     * The message function is an update to a schedule.
     */
    V_62("62", "Update schedule"),

    /**
     * Not accepted, subject to confirmation.
     */
    V_63("63", "Not accepted, provisional"),

    /**
     * The message is transmitted to verify information.
     */
    V_64("64", "Verification"),

    /**
     * To report an unsettled dispute.
     */
    V_65("65", "Unsettled dispute"),

    /**
     * A message related to a guarantee containing information about the discharge of an operation.
     */
    V_66("66", "Discharge of operation guarantee"),

    /**
     * A message related to a guarantee containing information about the termination of an
     * operation.
     */
    V_67("67", "Termination of operation guarantee"),

    /**
     * A message related to a guarantee containing information about the start of an operation.
     */
    V_68("68", "Start of operation guarantee"),

    /**
     * A message related to a guarantee containing new declaration data.
     */
    V_69("69", "New declaration data"),

    /**
     * A message related to the amendment of declaration data.
     */
    V_70("70", "Amended declaration data"),

    /**
     * A message related to a guarantee containing information about the refusal to start of
     * an operation.
     */
    V_71("71", "Refusal to start operation guarantee"),

    /**
     * A message related to a guarantee containing information about the seals during the start
     * of an operation.
     */
    V_72("72", "Seals information (Start)"),

    /**
     * A message related to a guarantee containing information about the seals during the termination
     * of an operation.
     */
    V_73("73", "Seals information (Terminate)"),
    ;

    private final String name;
    private final String code;

    MessageFunctionCodeType(String code, String name) {
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
