package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.AllowanceChargeIdentificationCodeAdapter;
import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import io.github.up2jakarta.xml.codelist.SubList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 5189 (AllowanceChargeID) : Allowance or charge identification code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred5189.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("5189")
@Documented(value = "Allowance Charge Identification Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.5", date = "2008-08-23")
@XmlJavaTypeAdapter(AllowanceChargeIdentificationCodeAdapter.class)
public enum AllowanceChargeIdentificationCodeType implements ChargeReasonCodeType<AllowanceChargeIdentificationCodeType> {

    /**
     * Fee for the processing of documentary credit, collection and payment which are charged
     * to the customer.
     */
    V_1("1", "Handling commission"),

    /**
     * Fee for amendments in documentary credit and collection business (not extensions and
     * increases of documentary credits).
     */
    V_2("2", "Amendment commission"),

    /**
     * Fee for the acceptance of draft in documentary credit and collection business which
     * are drawn on us (also to be seen as a kind of 'guarantee commission').
     */
    V_3("3", "Acceptance commission"),

    /**
     * Fee for obtaining an acceptance under collections on the basis of 'documents against
     * acceptance'.
     */
    V_4("4", "Commission for obtaining acceptance"),

    /**
     * Fee for delivery of documents without corresponding payment.
     */
    V_5("5", "Commission on delivery"),

    /**
     * Fee for advising documentary credits (charged also in case of confirmed credits).
     */
    V_6("6", "Advising commission"),

    /**
     * Fee for confirmation of credit.
     */
    V_7("7", "Confirmation commission"),

    /**
     * Fee for the deferred payment period under documentary credits confirmed by bank. This
     * fee are charges for the period from presentation of the document until due date of
     * payment.
     */
    V_8("8", "Deferred payment commission"),

    /**
     * Fee charged to the foreign bank for the processing of documentary credit.
     */
    V_9("9", "Commission for taking up documents"),

    /**
     * Fee for opening revocable documentary credit.
     */
    V_10("10", "Opening commission"),

    /**
     * Fee charged to the customer for discrepancies in credit documents in the case of which
     * the bank have to stipulate payment under reserve.
     */
    V_11("11", "Fee for payment under reserve"),

    /**
     * Fee charged to the foreign bank for discrepancies in credit documents.
     */
    V_12("12", "Discrepancy fee"),

    /**
     * Fee for the domicilation of bills with the bank.
     */
    V_13("13", "Domicilation commission"),

    /**
     * Commission for the release of goods sent to the bank.
     */
    V_14("14", "Commission for release of goods"),

    /**
     * Fee for settling collections on the basis of 'documents against payments'.
     */
    V_15("15", "Collection commission"),

    /**
     * Fee for the purchase of documents under sight credit for the first ten days.
     */
    V_16("16", "Negotiation commission"),

    /**
     * Fee for cheques, bills and collections returned unpaid and/or recalled.
     */
    V_17("17", "Return commission"),

    /**
     * Fee for the splitting of bills of lading.
     */
    V_18("18", "B/L splitting charges"),

    /**
     * Fee for the handling on a fiduciary basis of imported goods that have been warehoused.
     */
    V_19("19", "Trust commission"),

    /**
     * Fee for the transfer of transferable documentary credits.
     */
    V_20("20", "Transfer commission"),

    /**
     * Fee for opening irrevocable documentary credits. This fee is a kind of 'Guarantee commission'
     * as compensation for the commitment into which the bank have entered on the customers
     * behalf; similar to confirmation commission, acceptance commission.
     */
    V_21("21", "Commission for opening irrevocable documentary credits"),

    /**
     * Fee for the pre-advice of a documentary credit.
     */
    V_22("22", "Pre-advice commission"),

    /**
     * Fee for the supervising unconfirmed documentary credits with a deferred payment period.
     */
    V_23("23", "Supervisory commission"),

    /**
     * Fee for decoding telex messages.
     */
    V_24("24", "Model charges"),

    /**
     * Commission in addition to the confirmation commission for documentary credits from
     * sensitive countries.
     */
    V_25("25", "Risk commission"),

    /**
     * Commission for drawing up guaranties.
     */
    V_26("26", "Guarantee commission"),

    /**
     * Fee for reimbursement of, for example, documentary credits.
     */
    V_27("27", "Reimbursement commission"),

    /**
     * Tax payable on bills in accordance with national bill of exchange legislation.
     */
    V_28("28", "Stamp duty"),

    /**
     * Brokers commission arising, in trade with foreign currencies.
     */
    V_29("29", "Brokerage"),

    /**
     * Charges deducted/claimed by other banks involved in the transaction.
     */
    V_30("30", "Bank charges"),

    /**
     * Charges not included in the total charge amount i.e. the charges are for information
     * only.
     */
    V_31("31", "Bank charges information"),

    /**
     * Fee for use of courier service.
     */
    V_32("32", "Courier fee"),

    /**
     * Fee for use of phone.
     */
    V_33("33", "Phone fee"),

    /**
     * Fee for postage.
     */
    V_34("34", "Postage fee"),

    /**
     * Fee for use of S.W.I.F.T.
     */
    V_35("35", "S.W.I.F.T. fee"),

    /**
     * Fee for telex.
     */
    V_36("36", "Telex fee"),

    /**
     * Penalty imposed when documents are delivered late.
     */
    V_37("37", "Penalty for late delivery of documents"),

    /**
     * Penalty imposed when valuation of works is delivered late.
     */
    V_38("38", "Penalty for late delivery of valuation of works"),

    /**
     * Penalty imposed when the execution of works is behind schedule.
     */
    V_39("39", "Penalty for execution of works behind schedule"),

    /**
     * Penalty imposed for other reasons.
     */
    V_40("40", "Other penalties"),

    /**
     * Bonus for completing work ahead of schedule.
     */
    V_41("41", "Bonus for works ahead of schedule"),

    /**
     * Bonus earned for other reasons.
     */
    V_42("42", "Other bonus"),

    /**
     * Cost for project management.
     */
    V_44("44", "Project management cost"),

    /**
     * Proportional retention charge.
     */
    V_45("45", "Pro rata retention"),

    /**
     * Contractual retention charge.
     */
    V_46("46", "Contractual retention"),

    /**
     * Retention charge not otherwise specified.
     */
    V_47("47", "Other retentions"),

    /**
     * Interest for late payment.
     */
    V_48("48", "Interest on arrears"),

    /**
     * Cost of using money.
     */
    V_49("49", "Interest"),

    /**
     * Unit charge per credit cover established.
     */
    V_50("50", "Charge per credit cover"),

    /**
     * Unit charge per unused credit cover.
     */
    V_51("51", "Charge per unused credit cover"),

    /**
     * Minimum commission charge.
     */
    V_52("52", "Minimum commission"),

    /**
     * Commission charged for factoring services.
     */
    V_53("53", "Factoring commission"),

    /**
     * Identifies the charges from the chamber of commerce.
     */
    V_54("54", "Chamber of commerce charge"),

    /**
     * Charges for transfer.
     */
    V_55("55", "Transfer charges"),

    /**
     * Charges for repatriation.
     */
    V_56("56", "Repatriation charges"),

    /**
     * Not specifically defined charges.
     */
    V_57("57", "Miscellaneous charges"),

    /**
     * Charges for foreign exchange.
     */
    V_58("58", "Foreign exchange charges"),

    /**
     * Charge for agreed debit interest
     */
    V_59("59", "Agreed debit interest charge"),

    /**
     * A discount given by the manufacturer which should be passed on to the consumer.
     */
    V_60("60", "Manufacturer's consumer discount"),

    /**
     * Charge for fax advice.
     */
    V_61("61", "Fax advice charge"),

    /**
     * Allowance granted because of the military status.
     */
    V_62("62", "Due to military status"),

    /**
     * Allowance granted to a victim of a work accident.
     */
    V_63("63", "Due to work accident"),

    /**
     * An allowance or charge as specified in a special agreement.
     */
    V_64("64", "Special agreement"),

    /**
     * A discount given for the purchase of a product with a production error.
     */
    V_65("65", "Production error discount"),

    /**
     * A discount given at the occasion of the opening of a new outlet.
     */
    V_66("66", "New outlet discount"),

    /**
     * A discount given for the purchase of a sample of a product.
     */
    V_67("67", "Sample discount"),

    /**
     * A discount given for the purchase of an end-of-range product.
     */
    V_68("68", "End-of-range discount"),

    /**
     * A charge for the addition of a customer specific finish to a product.
     */
    V_69("69", "Charge for a customer specific finish"),

    /**
     * A discount given for a specified Incoterm.
     */
    V_70("70", "Incoterm discount"),

    /**
     * Allowance for reaching or exceeding an agreed sales threshold at the point of sales.
     */
    V_71("71", "Point of sales threshold allowance"),

    /**
     * Costs for technical modifications to a product.
     */
    V_72("72", "Technical modification costs"),

    /**
     * Costs of job-order production.
     */
    V_73("73", "Job-order production costs"),

    /**
     * Expenses for non-local activities.
     */
    V_74("74", "Off-premises costs"),

    /**
     * Costs of additional processing.
     */
    V_75("75", "Additional processing costs"),

    /**
     * Costs of official attestation.
     */
    V_76("76", "Attesting charge"),

    /**
     * Charge for increased delivery speed.
     */
    V_77("77", "Rush delivery surcharge"),

    /**
     * Charge for costs incurred as result of special constructions.
     */
    V_78("78", "Special construction costs"),

    /**
     * Amount to be paid for moving goods, by whatever means, from one place to another.
     */
    V_79("79", "Freight charges"),

    /**
     * Charge for packing.
     */
    V_80("80", "Packing charge"),

    /**
     * Charge for repair.
     */
    V_81("81", "Repair charge"),

    /**
     * Charge for loading.
     */
    V_82("82", "Loading charge"),

    /**
     * Charge for setup.
     */
    V_83("83", "Setup charge"),

    /**
     * Charge for testing.
     */
    V_84("84", "Testing charge"),

    /**
     * Charge for storage and handling.
     */
    V_85("85", "Warehousing charge"),

    /**
     * Difference between current price and basic value contained in product price in relation
     * to gold content.
     */
    V_86("86", "Gold surcharge"),

    /**
     * Difference between current price and basic value contained in product price in relation
     * to copper content.
     */
    V_87("87", "Copper surcharge"),

    /**
     * Surcharge/deduction, calculated for higher/ lower material's consumption.
     */
    V_88("88", "Material surcharge/deduction"),

    /**
     * Difference between current price and basic value contained in product price in relation
     * to lead content.
     */
    V_89("89", "Lead surcharge"),

    /**
     * Higher/lower price, resulting from change in costs between the times of making offer
     * and delivery.
     */
    V_90("90", "Price index surcharge"),

    /**
     * Difference between current price and basic value contained in product price in relation
     * to platinum content.
     */
    V_91("91", "Platinum surcharge"),

    /**
     * Difference between current price and basic value contained in product price in relation
     * to silver content.
     */
    V_92("92", "Silver surcharge"),

    /**
     * Difference between current price and basic value contained in product price in relation
     * to wolfram content.
     */
    V_93("93", "Wolfram surcharge"),

    /**
     * Difference between current price and basic value contained in product price in relation
     * to aluminum content.
     */
    V_94("94", "Aluminum surcharge"),

    /**
     * A reduction from a usual or list price.
     */
    V_95("95", "Discount"),

    /**
     * Charge for insurance.
     */
    V_96("96", "Insurance"),

    /**
     * Charge for minimum order or minimum billing.
     */
    V_97("97", "Minimum order / minimum billing charge"),

    /**
     * Surcharge for (special) materials.
     */
    V_98("98", "Material surcharge (special materials)"),

    /**
     * An additional amount added to the usual charge.
     */
    V_99("99", "Surcharge"),

    /**
     * A return of part of an amount paid for goods or services, serving as a reduction or
     * discount.
     */
    V_100("100", "Special rebate"),

    /**
     * A monetary amount charged for carbon footprint related to a regulatory requirement.
     */
    V_101("101", "Carbon footprint charge"),

    /**
     * A fixed long term allowance or charge.
     */
    V_102("102", "Fixed long term"),

    /**
     * A temporary allowance or charge.
     */
    V_103("103", "Temporary"),

    /**
     * The standard available allowance or charge.
     */
    V_104("104", "Standard"),

    /**
     * An allowance or charge based on yearly turnover.
     */
    V_105("105", "Yearly turnover"),

    /**
     * The amount of taxes and contributions for social security, that is subtracted from
     * the payable amount as it is to be paid separately.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    V_106("106", "Withheld taxes and social security contributions"),
    ;

    private final String name;
    private final String code;

    AllowanceChargeIdentificationCodeType(String code, String name) {
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
