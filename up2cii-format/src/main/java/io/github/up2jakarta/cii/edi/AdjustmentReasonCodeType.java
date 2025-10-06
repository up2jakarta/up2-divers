package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.AdjustmentReasonCodeAdapter;
import io.github.up2jakarta.xml.clv.Agency;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.Documented;
import io.github.up2jakarta.xml.clv.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 4465 : Adjustment reason description code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred4465.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Adjustment Reason Description Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.4", date = "2008-08-23")
@XmlJavaTypeAdapter(AdjustmentReasonCodeAdapter.class)
public enum AdjustmentReasonCodeType implements CodeList<AdjustmentReasonCodeType> {

    /**
     * An adjustment made based on an agreement between partners.
     */
    V_1("1", "Agreed settlement"),

    /**
     * Goods of inferior quality.
     */
    V_2("2", "Below specification goods"),

    /**
     * An adjustment due to the damage of goods.
     */
    V_3("3", "Damaged goods"),

    /**
     * An adjustment made because the delivered quantity was less than expected.
     */
    V_4("4", "Short delivery"),

    /**
     * An adjustment due to a price query.
     */
    V_5("5", "Price query"),

    /**
     * The buyer requires that proof of delivery be made before payment.
     */
    V_6("6", "Proof of delivery required"),

    /**
     * Buyer is to make payment later.
     */
    V_7("7", "Payment on account"),

    /**
     * Adjustment made to deduct the returnable container charge.
     */
    V_8("8", "Returnable container charge included"),

    /**
     * Invoice not in accordance with the order.
     */
    V_9("9", "Invoice error"),

    /**
     * Cost of draft has been deducted from payment.
     */
    V_10("10", "Costs for draft"),

    /**
     * Bank charges have been deducted from payment.
     */
    V_11("11", "Bank charges"),

    /**
     * Agent commission has been deducted from payment.
     */
    V_12("12", "Agent commission"),

    /**
     * Buyer claims an existing (financial) obligation from seller which (partly) offsets the
     * outstanding invoice(s).
     */
    V_13("13", "Counter claim"),

    /**
     * Delivery not according to specifications.
     */
    V_14("14", "Wrong delivery"),

    /**
     * Goods returned to agent.
     */
    V_15("15", "Goods returned to agent"),

    /**
     * Goods partly returned.
     */
    V_16("16", "Goods partly returned"),

    /**
     * Goods damaged in transit.
     */
    V_17("17", "Transport damage"),

    /**
     * Buyer does not accept invoice(s) charge as it relates to goods where the ownership remains
     * with the seller until sold.
     */
    V_18("18", "Goods on consignment"),

    /**
     * Trade discount deducted from payment.
     */
    V_19("19", "Trade discount"),

    /**
     * Penalty amount deducted for later delivery.
     */
    V_20("20", "Deduction for late delivery"),

    /**
     * Advertising costs deducted from payment.
     */
    V_21("21", "Advertising costs"),

    /**
     * Customs duties deducted from payment.
     */
    V_22("22", "Customs duties"),

    /**
     * Telephone and postal costs deducted from payment.
     */
    V_23("23", "Telephone and postal costs"),

    /**
     * Repair costs deducted from payment.
     */
    V_24("24", "Repair costs"),

    /**
     * Attorney fees deducted from payment.
     */
    V_25("25", "Attorney fees"),

    /**
     * Taxes deducted from payment.
     */
    V_26("26", "Taxes"),

    /**
     * Buyer reclaims an unspecified deduction from the invoice(s) (to be) paid.
     */
    V_27("27", "Reclaimed deduction"),

    /**
     * Buyer or seller refers to separate correspondence about a related shipment(s) and/or
     * invoice(s) and/or a payment(s).
     */
    V_28("28", "See separate advice"),

    /**
     * Buyer refused to take delivery.
     */
    V_29("29", "Buyer refused to take delivery"),

    /**
     * Buyer states to have paid to seller.
     */
    V_30("30", "Direct payment to seller"),

    /**
     * Buyer disagrees with due date.
     */
    V_31("31", "Buyer disagrees with due date"),

    /**
     * Buyer has not received the goods.
     */
    V_32("32", "Goods not delivered"),

    /**
     * Goods delivered too late.
     */
    V_33("33", "Late delivery"),

    /**
     * Factor informs the seller that a certain invoice(s) was paid by the buyer directly to
     * the seller.
     */
    V_34("34", "Quoted as paid to you"),

    /**
     * Buyer returned the goods to seller.
     */
    V_35("35", "Goods returned"),

    /**
     * Buyer claims he did not receive the invoice for which payment is requested.
     */
    V_36("36", "Invoice not received"),

    /**
     * Factor informs the seller that he did not receive copy of a credit note sent to the
     * buyer.
     */
    V_37("37", "Credit note to debtor/not to us"),

    /**
     * Buyer has/will deduct a bonus he is entitled to from the payment.
     */
    V_38("38", "Deducted bonus"),

    /**
     * Buyer has/will deduct the discount he is entitled to from the payment.
     */
    V_39("39", "Deducted discount"),

    /**
     * Buyer has/will deduct freight costs from the payment.
     */
    V_40("40", "Deducted freight costs"),

    /**
     * Deduction against invoices already settled.
     */
    V_41("41", "Deduction against other invoices"),

    /**
     * Buyer makes use of existing credit balance(s) to offset (partly) the outstanding invoice(s).
     */
    V_42("42", "Credit balance(s)"),

    /**
     * Factor informs the seller that the reason of a commercial dispute raised by the buyer
     * is unknown.
     */
    V_43("43", "Reason unknown"),

    /**
     * Buyer or factor are waiting for a (reply) message from the seller before a commercial
     * dispute can be settled.
     */
    V_44("44", "Awaiting message from seller"),

    /**
     * Buyer issued debit note to seller.
     */
    V_45("45", "Debit note to seller"),

    /**
     * Buyer has taken a discount larger than the discount terms agreed with the seller.
     */
    V_46("46", "Discount beyond terms"),

    /**
     * See correspondence from buyer.
     */
    V_47("47", "See buyer's letter"),

    /**
     * Error made by seller in the amount of allowance/charge.
     */
    V_48("48", "Allowance/charge error"),

    /**
     * Product delivered not fully according to specification.
     */
    V_49("49", "Substitute product"),

    /**
     * Terms of sale not according to purchase order.
     */
    V_50("50", "Terms of sale error"),

    /**
     * A message sent by buyer to seller or by seller to buyer did not contain data required
     * to take action/decision.
     */
    V_51("51", "Required data missing"),

    /**
     * Invoice issued to wrong party.
     */
    V_52("52", "Wrong invoice"),

    /**
     * Invoice sent twice.
     */
    V_53("53", "Duplicate invoice"),

    /**
     * Weight not in accordance with the order.
     */
    V_54("54", "Weight error"),

    /**
     * Additional charge not authorised.
     */
    V_55("55", "Additional charge not authorized"),

    /**
     * Buyer states that calculated discount on the invoice(s) is wrongly calculated.
     */
    V_56("56", "Incorrect discount"),

    /**
     * Price has been changed.
     */
    V_57("57", "Price change"),

    /**
     * The adjustment is a variation from an agreed value.
     */
    V_58("58", "Variation"),

    /**
     * Balance of one or more items charged back to seller.
     */
    V_59("59", "Chargeback"),

    /**
     * Allocation of one or more debit items to one or more credit items or vice-versa.
     */
    V_60("60", "Offset"),

    /**
     * Payment in settlement of an invoice has been made to a party other than the designated
     * creditor.
     */
    V_61("61", "Indirect payment"),

    /**
     * Previously assigned invoice/credit note is being reassigned.
     */
    V_62("62", "Financial reassignment"),

    /**
     * Reversal or cancellation of a chargeback and/or offset relating to an incorrect balance.
     */
    V_63("63", "Reinstatement of chargeback/offset"),

    /**
     * Buyer expects that seller revises the terms of payment of an invoice.
     */
    V_64("64", "Expecting new terms"),

    /**
     * Invoice has been/to be paid to seller's agent.
     */
    V_65("65", "Settlement to agent"),

    /**
     * An adjustment has been made due to the application of a cash discount.
     */
    V_66("66", "Cash discount"),

    /**
     * Costs deducted from a total amount to pay for the services of central payment.
     */
    V_67("67", "Delcredere costs"),

    /**
     * Adjustment results from the application of an early payment allowance.
     */
    V_68("68", "Early payment allowance adjustment"),

    /**
     * Adjustment has been made because an incorrect due date was referred to with regard to
     * the monetary amount.
     */
    V_69("69", "Incorrect due date for monetary amount"),

    /**
     * Adjustment has been made because of a wrong monetary amount resulting from an incorrect
     * free goods quantity.
     */
    V_70("70", "Wrong monetary amount resulting from incorrect free goods quantity"),

    /**
     * Adjustment due to the replenishment of the racks or shelves by a supplier.
     */
    V_71("71", "Rack or shelf replenishment service by a supplier"),

    /**
     * Adjustment due to a temporary special promotion.
     */
    V_72("72", "Temporary special promotion"),

    /**
     * Adjustment due to a difference in tax rate.
     */
    V_73("73", "Difference in tax rate"),

    /**
     * Adjustment due to a quantity discount.
     */
    V_74("74", "Quantity discount"),

    /**
     * Adjustment due to a promotion discount.
     */
    V_75("75", "Promotion discount"),

    /**
     * The cancellation has occurred after the deadline.
     */
    V_76("76", "Cancellation deadline passed"),

    /**
     * An adjustment has been made due to the application of a pricing discount.
     */
    V_77("77", "Pricing discount"),

    /**
     * Discount for reaching or exceeding an agreed accumulated volume.
     */
    V_78("78", "Volume discount"),

    /**
     * Adjustment has been made due to the application of a sundry discount.
     */
    V_79("79", "Sundry discount"),

    /**
     * The adjustment was made due to the card holder not signing the filing document.
     */
    V_80("80", "Card holder signature missing"),

    /**
     * The adjustment was made due to the card acceptor not specifying the expiry date within
     * the filing document.
     */
    V_81("81", "Card expiry date missing"),

    /**
     * The adjustment was made due to the card acceptor specifying an erroneous card number
     * within the filing document.
     */
    V_82("82", "Card number error"),

    /**
     * The adjustment was made due to the card acceptor specifying an expired expiry date within
     * the filing document or electronic data.
     */
    V_83("83", "Card expired"),

    /**
     * The adjustment was made due to a test card transaction, used for installing, maintaining
     * or debugging purposes.
     */
    V_84("84", "Test card transaction"),

    /**
     * The adjustment was made due to the permission limit defined by card issuer or card company
     * was exceeded without prior authorisation. Synonym: Floor limit.
     */
    V_85("85", "Permission limit exceeded"),

    /**
     * The adjustment was made due to the authorisation code provided did not fit to the specified
     * transaction.
     */
    V_86("86", "Wrong authorisation code"),

    /**
     * The adjustment was made due to the specified amount not meeting the authorised amount
     * for the transaction.
     */
    V_87("87", "Wrong authorised amount"),

    /**
     * The adjustment was made due to the authorisation needed had failed.
     */
    V_88("88", "Authorisation failed"),

    /**
     * The adjustment was made due to the data regarding the card acceptor is erroneous.
     */
    V_89("89", "Card acceptor data error"),

    /**
     * Charge for the service of treasury management.
     */
    V_90("90", "Treasury management service charge"),

    /**
     * The reason for the adjustment is that a mutually agreed discount has been applied.
     */
    V_91("91", "Agreed discount"),

    /**
     * The reason for the adjustment is that a fee for expediting has been applied.
     */
    V_92("92", "Expediting fee"),

    /**
     * The reason for the adjustment is that a fee for invoicing has been applied.
     */
    V_93("93", "Invoicing fee"),

    /**
     * The reason for the adjustment is that freight charges has been applied.
     */
    V_94("94", "Freight charge"),

    /**
     * The reason for the adjustment is that a fee for processing of a small order (an order
     * below a defined threshold) has been applied.
     */
    V_95("95", "Small order processing service charge"),

    /**
     * An adjustment made due to a change in a currency exchange rate.
     */
    V_96("96", "Currency exchange differences"),

    /**
     * An adjustment made due to the partner's inability to pay open debts.
     */
    V_97("97", "Insolvency"),

    /**
     * Adjustment has taken place because of incorrect references.
     */
    V_98("98", "Incorrect references"),

    /**
     * Adjustment has taken place because of incorrect identification of the buyer.
     */
    V_99("99", "Incorrect identification of the buyer"),

    /**
     * Adjustment has taken place because of incorrect product identification.
     */
    V_100("100", "Incorrect product identification"),

    /**
     * The reason for the adjustment is a new employee.
     */
    V_101("101", "New employee"),

    /**
     * The reason for the adjustment is the retirement of an employee.
     */
    V_102("102", "Employee retirement"),

    /**
     * The reason for the adjustment is a salary change.
     */
    V_103("103", "Salary change"),

    /**
     * The reason for the adjustment is a parental leave.
     */
    V_104("104", "Parental leave"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    AdjustmentReasonCodeType(String code, String name) {
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
