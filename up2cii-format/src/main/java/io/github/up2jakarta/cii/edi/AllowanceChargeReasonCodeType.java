package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.api.SubList;
import io.github.up2jakarta.cii.edi.adapters.AllowanceChargeReasonCodeAdapter;
import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 4465 (AllowanceChargeReasonCode) : Adjustment reason description code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred4465.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList(value = "4465", type = AdjustmentReasonCodeType.class)
@Documented(value = "Allowance Charge Reason Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.4", date = "2008-08-23")
@XmlJavaTypeAdapter(AllowanceChargeReasonCodeAdapter.class)
public enum AllowanceChargeReasonCodeType implements ChargeReasonCodeType<AllowanceChargeReasonCodeType> {

    /**
     * An adjustment made based on an agreement between partners.
     */
    V_1(AdjustmentReasonCodeType.V_1),

    /**
     * Goods of inferior quality.
     */
    V_2(AdjustmentReasonCodeType.V_2),

    /**
     * An adjustment due to the damage of goods.
     */
    V_3(AdjustmentReasonCodeType.V_3),

    /**
     * An adjustment made because the delivered quantity was less than expected.
     */
    V_4(AdjustmentReasonCodeType.V_4),

    /**
     * An adjustment due to a price query.
     */
    V_5(AdjustmentReasonCodeType.V_5),

    /**
     * The buyer requires that proof of delivery be made before payment.
     */
    V_6(AdjustmentReasonCodeType.V_6),

    /**
     * Buyer is to make payment later.
     */
    V_7(AdjustmentReasonCodeType.V_7),

    /**
     * Adjustment made to deduct the returnable container charge.
     */
    V_8(AdjustmentReasonCodeType.V_8),

    /**
     * Invoice not in accordance with the order.
     */
    V_9(AdjustmentReasonCodeType.V_9),

    /**
     * Cost of draft has been deducted from payment.
     */
    V_10(AdjustmentReasonCodeType.V_10),

    /**
     * Bank charges have been deducted from payment.
     */
    V_11(AdjustmentReasonCodeType.V_11),

    /**
     * Agent commission has been deducted from payment.
     */
    V_12(AdjustmentReasonCodeType.V_12),

    /**
     * Buyer claims an existing (financial) obligation from seller which (partly) offsets
     * the outstanding invoice(s).
     */
    V_13(AdjustmentReasonCodeType.V_13),

    /**
     * Delivery not according to specifications.
     */
    V_14(AdjustmentReasonCodeType.V_14),

    /**
     * Goods returned to agent.
     */
    V_15(AdjustmentReasonCodeType.V_15),

    /**
     * Goods partly returned.
     */
    V_16(AdjustmentReasonCodeType.V_16),

    /**
     * Goods damaged in transit.
     */
    V_17(AdjustmentReasonCodeType.V_17),

    /**
     * Buyer does not accept invoice(s) charge as it relates to goods where the ownership
     * remains with the seller until sold.
     */
    V_18(AdjustmentReasonCodeType.V_18),

    /**
     * Trade discount deducted from payment.
     */
    V_19(AdjustmentReasonCodeType.V_19),

    /**
     * Penalty amount deducted for later delivery.
     */
    V_20(AdjustmentReasonCodeType.V_20),

    /**
     * Advertising costs deducted from payment.
     */
    V_21(AdjustmentReasonCodeType.V_21),

    /**
     * Customs duties deducted from payment.
     */
    V_22(AdjustmentReasonCodeType.V_22),

    /**
     * Telephone and postal costs deducted from payment.
     */
    V_23(AdjustmentReasonCodeType.V_23),

    /**
     * Repair costs deducted from payment.
     */
    V_24(AdjustmentReasonCodeType.V_24),

    /**
     * Attorney fees deducted from payment.
     */
    V_25(AdjustmentReasonCodeType.V_25),

    /**
     * Taxes deducted from payment.
     */
    V_26(AdjustmentReasonCodeType.V_26),

    /**
     * Buyer reclaims an unspecified deduction from the invoice(s) (to be) paid.
     */
    V_27(AdjustmentReasonCodeType.V_27),

    /**
     * Buyer or seller refers to separate correspondence about a related shipment(s) and/or
     * invoice(s) and/or a payment(s).
     */
    V_28(AdjustmentReasonCodeType.V_28),

    /**
     * Buyer refused to take delivery.
     */
    V_29(AdjustmentReasonCodeType.V_29),

    /**
     * Buyer states to have paid to seller.
     */
    V_30(AdjustmentReasonCodeType.V_30),

    /**
     * Buyer disagrees with due date.
     */
    V_31(AdjustmentReasonCodeType.V_31),

    /**
     * Buyer has not received the goods.
     */
    V_32(AdjustmentReasonCodeType.V_32),

    /**
     * Goods delivered too late.
     */
    V_33(AdjustmentReasonCodeType.V_33),

    /**
     * Factor informs the seller that a certain invoice(s) was paid by the buyer directly
     * to the seller.
     */
    V_34(AdjustmentReasonCodeType.V_34),

    /**
     * Buyer returned the goods to seller.
     */
    V_35(AdjustmentReasonCodeType.V_35),

    /**
     * Buyer claims he did not receive the invoice for which payment is requested.
     */
    V_36(AdjustmentReasonCodeType.V_36),

    /**
     * Factor informs the seller that he did not receive copy of a credit note sent to the
     * buyer.
     */
    V_37(AdjustmentReasonCodeType.V_37),

    /**
     * Buyer has/will deduct a bonus he is entitled to from the payment.
     */
    V_38(AdjustmentReasonCodeType.V_38),

    /**
     * Buyer has/will deduct the discount he is entitled to from the payment.
     */
    V_39(AdjustmentReasonCodeType.V_39),

    /**
     * Buyer has/will deduct freight costs from the payment.
     */
    V_40(AdjustmentReasonCodeType.V_40),

    /**
     * Deduction against invoices already settled.
     */
    V_41(AdjustmentReasonCodeType.V_41),

    /**
     * Buyer makes use of existing credit balance(s) to offset (partly) the outstanding invoice(s).
     */
    V_42(AdjustmentReasonCodeType.V_42),

    /**
     * Factor informs the seller that the reason of a commercial dispute raised by the buyer
     * is unknown.
     */
    V_43(AdjustmentReasonCodeType.V_43),

    /**
     * Buyer or factor are waiting for a (reply) message from the seller before a commercial
     * dispute can be settled.
     */
    V_44(AdjustmentReasonCodeType.V_44),

    /**
     * Buyer issued debit note to seller.
     */
    V_45(AdjustmentReasonCodeType.V_45),

    /**
     * Buyer has taken a discount larger than the discount terms agreed with the seller.
     */
    V_46(AdjustmentReasonCodeType.V_46),

    /**
     * See correspondence from buyer.
     */
    V_47(AdjustmentReasonCodeType.V_47),

    /**
     * Error made by seller in the amount of allowance/charge.
     */
    V_48(AdjustmentReasonCodeType.V_48),

    /**
     * Product delivered not fully according to specification.
     */
    V_49(AdjustmentReasonCodeType.V_49),

    /**
     * Terms of sale not according to purchase order.
     */
    V_50(AdjustmentReasonCodeType.V_50),

    /**
     * A message sent by buyer to seller or by seller to buyer did not contain data required
     * to take action/decision.
     */
    V_51(AdjustmentReasonCodeType.V_51),

    /**
     * Invoice issued to wrong party.
     */
    V_52(AdjustmentReasonCodeType.V_52),

    /**
     * Invoice sent twice.
     */
    V_53(AdjustmentReasonCodeType.V_53),

    /**
     * Weight not in accordance with the order.
     */
    V_54(AdjustmentReasonCodeType.V_54),

    /**
     * Additional charge not authorised.
     */
    V_55(AdjustmentReasonCodeType.V_55),

    /**
     * Buyer states that calculated discount on the invoice(s) is wrongly calculated.
     */
    V_56(AdjustmentReasonCodeType.V_56),

    /**
     * Price has been changed.
     */
    V_57(AdjustmentReasonCodeType.V_57),

    /**
     * The adjustment is a variation from an agreed value.
     */
    V_58(AdjustmentReasonCodeType.V_58),

    /**
     * Balance of one or more items charged back to seller.
     */
    V_59(AdjustmentReasonCodeType.V_59),

    /**
     * Allocation of one or more debit items to one or more credit items or vice-versa.
     */
    V_60(AdjustmentReasonCodeType.V_60),

    /**
     * Payment in settlement of an invoice has been made to a party other than the designated
     * creditor.
     */
    V_61(AdjustmentReasonCodeType.V_61),

    /**
     * Previously assigned invoice/credit note is being reassigned.
     */
    V_62(AdjustmentReasonCodeType.V_62),

    /**
     * Reversal or cancellation of a chargeback and/or offset relating to an incorrect balance.
     */
    V_63(AdjustmentReasonCodeType.V_63),

    /**
     * Buyer expects that seller revises the terms of payment of an invoice.
     */
    V_64(AdjustmentReasonCodeType.V_64),

    /**
     * Invoice has been/to be paid to seller's agent.
     */
    V_65(AdjustmentReasonCodeType.V_65),

    /**
     * An adjustment has been made due to the application of a cash discount.
     */
    V_66(AdjustmentReasonCodeType.V_66),

    /**
     * Costs deducted from a total amount to pay for the services of central payment.
     */
    V_67(AdjustmentReasonCodeType.V_67),

    /**
     * Adjustment results from the application of an early payment allowance.
     */
    V_68(AdjustmentReasonCodeType.V_68),

    /**
     * Adjustment has been made because an incorrect due date was referred to with regard
     * to the monetary amount.
     */
    V_69(AdjustmentReasonCodeType.V_69),

    /**
     * Adjustment has been made because of a wrong monetary amount resulting from an incorrect
     * free goods quantity.
     */
    V_70(AdjustmentReasonCodeType.V_70),

    /**
     * Adjustment due to the replenishment of the racks or shelves by a supplier.
     */
    V_71(AdjustmentReasonCodeType.V_71),

    /**
     * Adjustment due to a temporary special promotion.
     */
    V_72(AdjustmentReasonCodeType.V_72),

    /**
     * Adjustment due to a difference in tax rate.
     */
    V_73(AdjustmentReasonCodeType.V_73),

    /**
     * Adjustment due to a quantity discount.
     */
    V_74(AdjustmentReasonCodeType.V_74),

    /**
     * Adjustment due to a promotion discount.
     */
    V_75(AdjustmentReasonCodeType.V_75),

    /**
     * The cancellation has occurred after the deadline.
     */
    V_76(AdjustmentReasonCodeType.V_76),

    /**
     * An adjustment has been made due to the application of a pricing discount.
     */
    V_77(AdjustmentReasonCodeType.V_77),

    /**
     * Discount for reaching or exceeding an agreed accumulated volume.
     */
    V_78(AdjustmentReasonCodeType.V_78),

    /**
     * Adjustment has been made due to the application of a sundry discount.
     */
    V_79(AdjustmentReasonCodeType.V_79),

    /**
     * The adjustment was made due to the card holder not signing the filing document.
     */
    V_80(AdjustmentReasonCodeType.V_80),

    /**
     * The adjustment was made due to the card acceptor not specifying the expiry date within
     * the filing document.
     */
    V_81(AdjustmentReasonCodeType.V_81),

    /**
     * The adjustment was made due to the card acceptor specifying an erroneous card number
     * within the filing document.
     */
    V_82(AdjustmentReasonCodeType.V_82),

    /**
     * The adjustment was made due to the card acceptor specifying an expired expiry date
     * within the filing document or electronic data.
     */
    V_83(AdjustmentReasonCodeType.V_83),

    /**
     * The adjustment was made due to a test card transaction, used for installing, maintaining
     * or debugging purposes.
     */
    V_84(AdjustmentReasonCodeType.V_84),

    /**
     * The adjustment was made due to the permission limit defined by card issuer or card
     * company was exceeded without prior authorisation. Synonym: Floor limit.
     */
    V_85(AdjustmentReasonCodeType.V_85),

    /**
     * The adjustment was made due to the authorisation code provided did not fit to the specified
     * transaction.
     */
    V_86(AdjustmentReasonCodeType.V_86),

    /**
     * The adjustment was made due to the specified amount not meeting the authorised amount
     * for the transaction.
     */
    V_87(AdjustmentReasonCodeType.V_87),

    /**
     * The adjustment was made due to the authorisation needed had failed.
     */
    V_88(AdjustmentReasonCodeType.V_88),

    /**
     * The adjustment was made due to the data regarding the card acceptor is erroneous.
     */
    V_89(AdjustmentReasonCodeType.V_89),

    /**
     * Charge for the service of treasury management.
     */
    V_90(AdjustmentReasonCodeType.V_90),

    /**
     * The reason for the adjustment is that a mutually agreed discount has been applied.
     */
    V_91(AdjustmentReasonCodeType.V_91),

    /**
     * The reason for the adjustment is that a fee for expediting has been applied.
     */
    V_92(AdjustmentReasonCodeType.V_92),

    /**
     * The reason for the adjustment is that a fee for invoicing has been applied.
     */
    V_93(AdjustmentReasonCodeType.V_93),

    /**
     * The reason for the adjustment is that freight charges has been applied.
     */
    V_94(AdjustmentReasonCodeType.V_94),

    /**
     * The reason for the adjustment is that a fee for processing of a small order (an order
     * below a defined threshold) has been applied.
     */
    V_95(AdjustmentReasonCodeType.V_95),

    /**
     * An adjustment made due to a change in a currency exchange rate.
     */
    V_96(AdjustmentReasonCodeType.V_96),

    /**
     * An adjustment made due to the partner's inability to pay open debts.
     */
    V_97(AdjustmentReasonCodeType.V_97),

    /**
     * Adjustment has taken place because of incorrect references.
     */
    V_98(AdjustmentReasonCodeType.V_98),

    /**
     * Adjustment has taken place because of incorrect identification of the buyer.
     */
    V_99(AdjustmentReasonCodeType.V_99),

    /**
     * Adjustment has taken place because of incorrect product identification.
     */
    V_100(AdjustmentReasonCodeType.V_100),

    /**
     * The reason for the adjustment is a new employee.
     */
    V_101(AdjustmentReasonCodeType.V_101),

    /**
     * The reason for the adjustment is the retirement of an employee.
     */
    V_102(AdjustmentReasonCodeType.V_102),

    /**
     * The reason for the adjustment is a salary change.
     */
    V_103(AdjustmentReasonCodeType.V_103),

    /**
     * The reason for the adjustment is a parental leave.
     */
    V_104(AdjustmentReasonCodeType.V_104),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    ZZZ(AdjustmentReasonCodeType.ZZZ),
    ;

    private final String name;
    private final String code;

    AllowanceChargeReasonCodeType(AdjustmentReasonCodeType cl) {
        this.code = cl.getCode();
        this.name = cl.getName();
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
