package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.edi.adapters.PaymentMeansCodeAdapter;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 4461 : Payment means code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred4461.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Payment Means Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.4", date = "2008-08-23")
@XmlJavaTypeAdapter(PaymentMeansCodeAdapter.class)
public enum PaymentMeansCodeType implements CodeList<PaymentMeansCodeType> {

    /**
     * Not defined legally enforceable agreement between two or more parties (expressing a
     * contractual right or a right to the payment of money).
     */
    V_1("1", "Instrument not defined"),

    /**
     * A credit transaction made through the automated clearing house system.
     */
    V_2("2", "Automated clearing house credit"),

    /**
     * A debit transaction made through the automated clearing house system.
     */
    V_3("3", "Automated clearing house debit"),

    /**
     * A request to reverse an ACH debit transaction to a demand deposit account.
     */
    V_4("4", "ACH demand debit reversal"),

    /**
     * A request to reverse a credit transaction to a demand deposit account.
     */
    V_5("5", "ACH demand credit reversal"),

    /**
     * A credit transaction made through the ACH system to a demand deposit account.
     */
    V_6("6", "ACH demand credit"),

    /**
     * A debit transaction made through the ACH system to a demand deposit account.
     */
    V_7("7", "ACH demand debit"),

    /**
     * Indicates that the bank should hold the payment for collection by the beneficiary or
     * other instructions.
     */
    V_8("8", "Hold"),

    /**
     * Indicates that the payment should be made using the national or regional clearing.
     */
    V_9("9", "National or regional clearing"),

    /**
     * Payment by currency (including bills and coins) in circulation, including checking account
     * deposits.
     */
    V_10("10", "In cash"),

    /**
     * A request to reverse an ACH credit transaction to a savings account.
     */
    V_11("11", "ACH savings credit reversal"),

    /**
     * A request to reverse an ACH debit transaction to a savings account.
     */
    V_12("12", "ACH savings debit reversal"),

    /**
     * A credit transaction made through the ACH system to a savings account.
     */
    V_13("13", "ACH savings credit"),

    /**
     * A debit transaction made through the ACH system to a savings account.
     */
    V_14("14", "ACH savings debit"),

    /**
     * A credit entry between two accounts at the same bank branch. Synonym: house credit.
     */
    V_15("15", "Bookentry credit"),

    /**
     * A debit entry between two accounts at the same bank branch. Synonym: house debit.
     */
    V_16("16", "Bookentry debit"),

    /**
     * A credit transaction made through the ACH system to a demand deposit account using the
     * CCD payment format.
     */
    V_17("17", "ACH demand cash concentration/disbursement (CCD) credit"),

    /**
     * A debit transaction made through the ACH system to a demand deposit account using the
     * CCD payment format.
     */
    V_18("18", "ACH demand cash concentration/disbursement (CCD) debit"),

    /**
     * A credit transaction made through the ACH system to a demand deposit account using the
     * CTP payment format.
     */
    V_19("19", "ACH demand corporate trade payment (CTP) credit"),

    /**
     * Payment by a pre-printed form on which instructions are given to an account holder (a
     * bank or building society) to pay a stated sum to a named recipient.
     */
    V_20("20", "Cheque"),

    /**
     * Issue of a banker's draft in payment of the funds.
     */
    V_21("21", "Banker's draft"),

    /**
     * Cheque drawn by a bank on itself or its agent. A person who owes money to another buys
     * the draft from a bank for cash and hands it to the creditor who need have no fear that
     * it might be dishonoured.
     */
    V_22("22", "Certified banker's draft"),

    /**
     * Payment by a pre-printed form, which has been completed by a financial institution,
     * on which instructions are given to an account holder (a bank or building society) to
     * pay a stated sum to a named recipient.
     */
    V_23("23", "Bank cheque (issued by a banking or similar establishment)"),

    /**
     * Bill drawn by the creditor on the debtor but not yet accepted by the debtor.
     */
    V_24("24", "Bill of exchange awaiting acceptance"),

    /**
     * Payment by a pre-printed form stamped with the paying bank's certification on which
     * instructions are given to an account holder (a bank or building society) to pay a stated
     * sum to a named recipient .
     */
    V_25("25", "Certified cheque"),

    /**
     * Indicates that the cheque is given local to the recipient.
     */
    V_26("26", "Local cheque"),

    /**
     * A debit transaction made through the ACH system to a demand deposit account using the
     * CTP payment format.
     */
    V_27("27", "ACH demand corporate trade payment (CTP) debit"),

    /**
     * A credit transaction made through the ACH system to a demand deposit account using the
     * CTX payment format.
     */
    V_28("28", "ACH demand corporate trade exchange (CTX) credit"),

    /**
     * A debit transaction made through the ACH system to a demand account using the CTX payment
     * format.
     */
    V_29("29", "ACH demand corporate trade exchange (CTX) debit"),

    /**
     * Payment by credit movement of funds from one account to another.
     */
    V_30("30", "Credit transfer"),

    /**
     * Payment by debit movement of funds from one account to another.
     */
    V_31("31", "Debit transfer"),

    /**
     * A credit transaction made through the ACH system to a demand deposit account using the
     * CCD+ payment format.
     */
    V_32("32", "ACH demand cash concentration/disbursement plus (CCD+) credit"),

    /**
     * A debit transaction made through the ACH system to a demand deposit account using the
     * CCD+ payment format.
     */
    V_33("33", "ACH demand cash concentration/disbursement plus (CCD+) debit"),

    /**
     * A consumer credit transaction made through the ACH system to a demand deposit or savings
     * account.
     */
    V_34("34", "ACH prearranged payment and deposit (PPD)"),

    /**
     * A credit transaction made through the ACH system to a demand deposit or savings account.
     */
    V_35("35", "ACH savings cash concentration/disbursement (CCD) credit"),

    /**
     * A debit transaction made through the ACH system to a savings account using the CCD payment
     * format.
     */
    V_36("36", "ACH savings cash concentration/disbursement (CCD) debit"),

    /**
     * A credit transaction made through the ACH system to a savings account using the CTP
     * payment format.
     */
    V_37("37", "ACH savings corporate trade payment (CTP) credit"),

    /**
     * A debit transaction made through the ACH system to a savings account using the CTP payment
     * format.
     */
    V_38("38", "ACH savings corporate trade payment (CTP) debit"),

    /**
     * A credit transaction made through the ACH system to a savings account using the CTX
     * payment format.
     */
    V_39("39", "ACH savings corporate trade exchange (CTX) credit"),

    /**
     * A debit transaction made through the ACH system to a savings account using the CTX payment
     * format.
     */
    V_40("40", "ACH savings corporate trade exchange (CTX) debit"),

    /**
     * A credit transaction made through the ACH system to a savings account using the CCD+
     * payment format.
     */
    V_41("41", "ACH savings cash concentration/disbursement plus (CCD+) credit"),

    /**
     * Payment by an arrangement for settling debts that is operated by the Post Office.
     */
    V_42("42", "Payment to bank account"),

    /**
     * A debit transaction made through the ACH system to a savings account using the CCD+
     * payment format.
     */
    V_43("43", "ACH savings cash concentration/disbursement plus (CCD+) debit"),

    /**
     * Bill drawn by the creditor on the debtor and accepted by the debtor.
     */
    V_44("44", "Accepted bill of exchange"),

    /**
     * A referenced credit transfer initiated through home-banking.
     */
    V_45("45", "Referenced home-banking credit transfer"),

    /**
     * A debit transfer via interbank means.
     */
    V_46("46", "Interbank debit transfer"),

    /**
     * A debit transfer initiated through home-banking.
     */
    V_47("47", "Home-banking debit transfer"),

    /**
     * Payment by means of a card issued by a bank or other financial institution.
     */
    V_48("48", "Bank card"),

    /**
     * The amount is to be, or has been, directly debited to the customer's bank account.
     */
    V_49("49", "Direct debit"),

    /**
     * A method for the transmission of funds through the postal system rather than through
     * the banking system.
     */
    V_50("50", "Payment by postgiro"),

    /**
     * A French standard procedure that allows a debtor to pay an amount due to a creditor.
     * The creditor will forward it to its bank, which will collect the money on the bank account
     * of the debtor.
     */
    V_51("51", "FR, norme 6 97-Telereglement CFONB (French Organisation for Banking Standards)  - Option A"),

    /**
     * Payment order which requires guaranteed processing by the most appropriate means to
     * ensure it occurs on the requested execution date, provided that it is issued to the
     * ordered bank before the agreed cut-off time.
     */
    V_52("52", "Urgent commercial payment"),

    /**
     * Payment order or transfer which must be executed, by the most appropriate means, as
     * urgently as possible and before urgent commercial payments.
     */
    V_53("53", "Urgent Treasury Payment"),

    /**
     * Payment made by means of credit card.
     */
    V_54("54", "Credit card"),

    /**
     * Payment made by means of debit card.
     */
    V_55("55", "Debit card"),

    /**
     * Payment will be, or has been, made by bankgiro.
     */
    V_56("56", "Bankgiro"),

    /**
     * The payment means have been previously agreed between seller and buyer and thus are
     * not stated again.
     */
    V_57("57", "Standing agreement"),

    /**
     * Credit transfer inside the Single Euro Payment Area (SEPA) system.
     */
    V_58("58", "SEPA credit transfer"),

    /**
     * Direct debit inside the Single Euro Payment Area (SEPA) system.
     */
    V_59("59", "SEPA direct debit"),

    /**
     * Payment by an unconditional promise in writing made by one person to another, signed
     * by the maker, engaging to pay on demand or at a fixed or determinable future time a
     * sum certain in money, to order or to bearer.
     */
    V_60("60", "Promissory note"),

    /**
     * Payment by an unconditional promise in writing made by the debtor to another person,
     * signed by the debtor, engaging to pay on demand or at a fixed or determinable future
     * time a sum certain in money, to order or to bearer.
     */
    V_61("61", "Promissory note signed by the debtor"),

    /**
     * Payment by an unconditional promise in writing made by the debtor to another person,
     * signed by the debtor and endorsed by a bank, engaging to pay on demand or at a fixed
     * or determinable future time a sum certain in money, to order or to bearer.
     */
    V_62("62", "Promissory note signed by the debtor and endorsed by a bank"),

    /**
     * Payment by an unconditional promise in writing made by the debtor to another person,
     * signed by the debtor and endorsed by a third party, engaging to pay on demand or at
     * a fixed or determinable future time a sum certain in money, to order or to bearer.
     */
    V_63("63", "Promissory note signed by the debtor and endorsed by a third party"),

    /**
     * Payment by an unconditional promise in writing made by the bank to another person, signed
     * by the bank, engaging to pay on demand or at a fixed or determinable future time a sum
     * certain in money, to order or to bearer.
     */
    V_64("64", "Promissory note signed by a bank"),

    /**
     * Payment by an unconditional promise in writing made by the bank to another person, signed
     * by the bank and endorsed by another bank, engaging to pay on demand or at a fixed or
     * determinable future time a sum certain in money, to order or to bearer.
     */
    V_65("65", "Promissory note signed by a bank and endorsed by another bank"),

    /**
     * Payment by an unconditional promise in writing made by a third party to another person,
     * signed by the third party, engaging to pay on demand or at a fixed or determinable future
     * time a sum certain in money, to order or to bearer.
     */
    V_66("66", "Promissory note signed by a third party"),

    /**
     * Payment by an unconditional promise in writing made by a third party to another person,
     * signed by the third party and endorsed by a bank, engaging to pay on demand or at a
     * fixed or determinable future time a sum certain in money, to order or to bearer.
     */
    V_67("67", "Promissory note signed by a third party and endorsed by a bank"),

    /**
     * Payment will be made or has been made by an online payment service.
     */
    V_68("68", "Online payment service"),

    /**
     * Transfer of an amount of money in the books of the account servicer. An advice should
     * be sent back to the account owner.
     */
    V_69("69", "Transfer Advice"),

    /**
     * Bill drawn by the creditor on the debtor.
     */
    V_70("70", "Bill drawn by the creditor on the debtor"),

    /**
     * Bill drawn by the creditor on a bank.
     */
    V_74("74", "Bill drawn by the creditor on a bank"),

    /**
     * Bill drawn by the creditor, endorsed by another bank.
     */
    V_75("75", "Bill drawn by the creditor, endorsed by another bank"),

    /**
     * Bill drawn by the creditor on a bank and endorsed by a third party.
     */
    V_76("76", "Bill drawn by the creditor on a bank and endorsed by a third party"),

    /**
     * Bill drawn by the creditor on a third party.
     */
    V_77("77", "Bill drawn by the creditor on a third party"),

    /**
     * Bill drawn by creditor on third party, accepted and endorsed by bank.
     */
    V_78("78", "Bill drawn by creditor on third party, accepted and endorsed by bank"),

    /**
     * Issue a bankers draft not endorsable.
     */
    V_91("91", "Not transferable banker's draft"),

    /**
     * Issue a cheque not endorsable in payment of the funds.
     */
    V_92("92", "Not transferable local cheque"),

    /**
     * Ordering customer tells the bank to use the payment system 'Reference giro'. Used in
     * the Finnish national banking system.
     */
    V_93("93", "Reference giro"),

    /**
     * Ordering customer tells the bank to use the bank service 'Urgent Giro' when transferring
     * the payment. Used in Finnish national banking system.
     */
    V_94("94", "Urgent giro"),

    /**
     * Ordering customer tells the ordering bank to use the bank service 'Free Format Giro'
     * when transferring the payment. Used in Finnish national banking system.
     */
    V_95("95", "Free format giro"),

    /**
     * If the requested method for payment was or could not be used, this code indicates that.
     */
    V_96("96", "Requested method for payment was not used"),

    /**
     * Amounts which two partners owe to each other to be compensated in order to avoid useless
     * payments.
     */
    V_97("97", "Clearing between partners"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    PaymentMeansCodeType(String code, String name) {
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
