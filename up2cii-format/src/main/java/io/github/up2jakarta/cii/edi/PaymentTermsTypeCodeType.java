package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.PaymentTermsTypeCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 4279 : Payment terms type code qualifier.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred4279.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Payment Terms Type Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(PaymentTermsTypeCodeAdapter.class)
public enum PaymentTermsTypeCodeType implements CodeList<PaymentTermsTypeCodeType> {

    /**
     * Payment conditions normally applied.
     */
    V_1("1", "Basic"),

    /**
     * Payments are due at end of month.
     */
    V_2("2", "End of month"),

    /**
     * Payments are due on the fixed date specified.
     */
    V_3("3", "Fixed date"),

    /**
     * Payments are deferred beyond the normal due date.
     */
    V_4("4", "Deferred"),

    /**
     * Payment terms on which discounts are not applicable.
     */
    V_5("5", "Discount not applicable"),

    /**
     * Different payment terms negotiated under a documentary credit.
     */
    V_6("6", "Mixed"),

    /**
     * Payment is extended beyond the normal due date.
     */
    V_7("7", "Extended"),

    /**
     * Payment terms reflect the basic offered discount.
     */
    V_8("8", "Basic discount offered"),

    /**
     * Occurring in the next month after present.
     */
    V_9("9", "Proximo"),

    /**
     * Payment is due on receipt of invoice.
     */
    V_10("10", "Instant"),

    /**
     * Payment terms to be chosen by buyer (from options separately advised).
     */
    V_11("11", "Elective"),

    /**
     * Payment due ten days after end of a calendar month.
     */
    V_12("12", "10 days after end of month"),

    /**
     * Seller will advise buyer of payment terms by separate transaction.
     */
    V_13("13", "Seller to advise buyer"),

    /**
     * Payments are to be made against statement.
     */
    V_14("14", "Paid against statement"),

    /**
     * Payment terms have no charge.
     */
    V_15("15", "No charge"),

    /**
     * Payment terms are not yet defined.
     */
    V_16("16", "Not yet defined"),

    /**
     * Payment is due the end of the current or specified month.
     */
    V_17("17", "Ultimo"),

    /**
     * Payment terms have been previously agreed upon.
     */
    V_18("18", "Previously agreed upon"),

    /**
     * The payment terms require the use of United States funds.
     */
    V_19("19", "United States funds"),

    /**
     * Payment terms on which penalties apply.
     */
    V_20("20", "Penalty terms"),

    /**
     * Payment terms are based on instalment payments.
     */
    V_21("21", "Payment by instalment"),

    /**
     * Payment terms on which discounts are applicable.
     */
    V_22("22", "Discount"),

    /**
     * Payment made at sight.
     */
    V_23("23", "Available by sight payment"),

    /**
     * Payment made at deferred date.
     */
    V_24("24", "Available by deferred payment"),

    /**
     * Payment on acceptance.
     */
    V_25("25", "Available by acceptance"),

    /**
     * Payment made by negotiation with any bank.
     */
    V_26("26", "Available by negotiation with any bank"),

    /**
     * Payment made by negotiation with any bank in a specified location.
     */
    V_27("27", "Available by negotiation with any bank in ..."),

    /**
     * Payment made by negotiation with a specified financial institution.
     */
    V_28("28", "Available by negotiation by named bank"),

    /**
     * Payment made by negotiation.
     */
    V_29("29", "Available by negotiation"),

    /**
     * Payment adjusted for outstanding credits or debits.
     */
    V_30("30", "Adjustment payment"),

    /**
     * Payment after due date.
     */
    V_31("31", "Late payment"),

    /**
     * Payment in advance of due date.
     */
    V_32("32", "Advanced payment"),

    /**
     * Payment by instalments according to progress (as agreed).
     */
    V_33("33", "Payment by instalments according to progress (as agreed)"),

    /**
     * Payment by instalments according to progress (to be agreed).
     */
    V_34("34", "Payment by instalments according to progress (to be agreed)"),

    /**
     * Terms of payment differ from the normal terms.
     */
    V_35("35", "Nonstandard"),

    /**
     * Payment to be made according to bilaterally agreed conditions between buyer and seller.
     */
    V_36("36", "Tenor payment terms"),

    /**
     * Payment must be made for complete value and may not be paid in instalments.
     */
    V_37("37", "Complete payment"),

    /**
     * Payment terms are specified in a consolidated invoice.
     */
    V_38("38", "Payment terms defined in consolidated invoice"),

    /**
     * The payment terms require payment upon completion.
     */
    V_39("39", "Payment upon completion"),

    /**
     * The payment terms require a partial payment in advance of completion.
     */
    V_40("40", "Partial advance"),

    /**
     * The payment terms are that the goods will be paid for when they are sold or consumed.
     */
    V_41("41", "Consignment"),

    /**
     * The payment terms involve the use of an inter-company account.
     */
    V_42("42", "Inter-company account"),

    /**
     * The payment terms involve a debtor who promises to pay a definite sum of money on demand
     * or at a definite time in the future.
     */
    V_43("43", "Sell by note"),

    /**
     * The payment terms involve payment for merchandise owned by a third party.
     */
    V_44("44", "Supplier floor plan"),

    /**
     * The payment terms are based on a contract with a vendor.
     */
    V_45("45", "Contract basis"),

    /**
     * The payment terms involve the monitoring of credit by the grantor.
     */
    V_46("46", "Credit controlled"),

    /**
     * The payment terms in which suppliers may extend seasonal dating.
     */
    V_47("47", "Dating given"),

    /**
     * A trade acceptance is a written acknowledgement of the sale of goods and promise to
     * pay at a definite date and place.
     */
    V_48("48", "Trade acceptance"),

    /**
     * The payment terms permit reimbursement of costs plus other authorised changes.
     */
    V_49("49", "Cost plus"),

    /**
     * The payment terms require the use of a letter of credit.
     */
    V_50("50", "Letter of credit"),

    /**
     * The payment terms are included in the lease agreement.
     */
    V_51("51", "Lease agreement"),

    /**
     * The payment terms are cash is due on delivery of merchandise.
     */
    V_52("52", "Cash On Delivery (COD)"),

    /**
     * The payment terms are dictated by state law requiring payment of cash.
     */
    V_53("53", "Cash by state law"),

    /**
     * The payment terms require the use of bank transfer.
     */
    V_54("54", "Bank transfer"),

    /**
     * The payment terms require payment by cash on arrival of the goods or services.
     */
    V_55("55", "Cash on arrival"),

    /**
     * The payment terms are that payments are made in cash.
     */
    V_56("56", "Cash"),

    /**
     * The payment terms are that a discount is applicable if the payment is made in cash.
     */
    V_57("57", "Cash discount terms apply"),

    /**
     * The payment terms require the payment in cash with placement of the order.
     */
    V_58("58", "Cash with order"),

    /**
     * The payment terms involve a vendor request for payment by cash.
     */
    V_59("59", "Cash per vendor request"),

    /**
     * The payment terms require the use of an irrevocable letter of credit.
     */
    V_60("60", "Irrevocable letter of credit"),

    /**
     * The payment terms require acceptance of liability before document transfer.
     */
    V_61("61", "Documents against acceptance"),

    /**
     * The payment terms permit the use of a charge card to effect payment.
     */
    V_62("62", "Charge card"),

    /**
     * The payment terms require payment before document transfer.
     */
    V_63("63", "Documents against payment"),

    /**
     * The payment terms are based on the time allowed by commercial usage for the payment
     * of foreign bills of exchange.
     */
    V_64("64", "Usance bill"),

    /**
     * The payment terms require the presentation of a letter of credit.
     */
    V_65("65", "Letter of credit at sight"),

    /**
     * The payment terms call for the use of a secured account.
     */
    V_66("66", "Secured account"),

    /**
     * The payment terms call for the use of basic commission terms.
     */
    V_67("67", "Basic commission terms"),

    /**
     * The payment terms require a deposit be provided.
     */
    V_68("68", "Deposit required"),

    /**
     * The payment terms include a discount when payment is made within a time frame designated
     * as prompt pay.
     */
    V_69("69", "Discount with prompt pay"),

    /**
     * The payment terms include a discount when payment is made in advance.
     */
    V_70("70", "Discount with advance payment"),

    /**
     * The payment terms require the use of a certified cheque.
     */
    V_71("71", "Certified cheque"),

    /**
     * The payment terms require cash payment before document transfer.
     */
    V_72("72", "Cash against documents"),

    /**
     * The payment terms require the use of bill of exchange.
     */
    V_73("73", "Bill of exchange"),

    /**
     * The payment terms include a progressive discount based on the amount and speed with
     * which payments are made.
     */
    V_74("74", "Progressive discount"),

    /**
     * The payment term requires a lump sum payment.
     */
    V_75("75", "Lump sum"),

    /**
     * The payment term requires a fixed fee payment.
     */
    V_76("76", "Fixed fee"),

    /**
     * The payment term requires the use of a promissory note as a means of payment.
     */
    V_77("77", "Promissory note"),

    /**
     * Payment term requires payment being made by the factoring company according to the
     * agreement between buyer, bank and factoring company.
     */
    V_78("78", "Factoring"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    PaymentTermsTypeCodeType(String code, String name) {
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
