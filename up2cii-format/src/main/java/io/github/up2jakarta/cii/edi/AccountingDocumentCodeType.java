package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.AccountingDocumentCodeAdapter;
import io.github.up2jakarta.xml.codelist.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 1001 (Accounting) : Document name code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred1001.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList(value = "1001", type = DocumentCodeType.class)
@Documented(value = "Document Name Code_Accounting", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.6", date = "2008-08-23")
@XmlJavaTypeAdapter(AccountingDocumentCodeAdapter.class)
public enum AccountingDocumentCodeType implements CodeList<AccountingDocumentCodeType> {

    /**
     * Document/message issued within an enterprise to initiate the purchase of articles,
     * materials or services required for the production or manufacture of goods to be offered
     * for sale or otherwise supplied to customers.
     */
    V_105(DocumentCodeType.V_105),

    /**
     * Document/message by means of which a buyer initiates a transaction with a seller involving
     * the supply of goods or services as specified, according to conditions set out in an
     * offer, or otherwise known to the buyer.
     */
    V_220(DocumentCodeType.V_220),

    /**
     * Document/message for goods in leasing contracts.
     */
    V_223(DocumentCodeType.V_223),

    /**
     * Document/message for urgent ordering.
     */
    V_224(DocumentCodeType.V_224),

    /**
     * Document/message issued by a buyer releasing the despatch of goods after receipt of
     * the Ready for despatch advice from the seller.
     */
    V_245(DocumentCodeType.V_245),

    /**
     * (1296) Document/message evidencing an agreement between the seller and the buyer for
     * the supply of goods or services; its effects are equivalent to those of an order followed
     * by an acknowledgement of order.
     */
    V_315(DocumentCodeType.V_315),

    /**
     * Document/message acknowledging an undertaking to fulfil an order and confirming conditions
     * or acceptance of conditions.
     */
    V_320(DocumentCodeType.V_320),

    /**
     * Document/message serving as a preliminary invoice, containing - on the whole - the
     * same information as the final invoice, but not actually claiming payment.
     */
    V_325(DocumentCodeType.V_325),

    /**
     * Document/message specifying details of an incomplete invoice.
     */
    V_326(DocumentCodeType.V_326),

    /**
     * (1334) Document/message claiming payment for goods or services supplied under conditions
     * agreed between seller and buyer.
     */
    V_380(DocumentCodeType.V_380),

    /**
     * An invoice the invoicee is producing instead of the seller.
     */
    V_389(DocumentCodeType.V_389),

    /**
     * Invoice assigned to a third party for collection.
     */
    V_393(DocumentCodeType.V_393),

    /**
     * Usage of INVOIC-message for goods in leasing contracts.
     */
    V_394(DocumentCodeType.V_394),

    /**
     * Commercial invoice that covers a transaction other than one involving a sale.
     */
    V_395(DocumentCodeType.V_395),

    /**
     * Document by means of which the supplier or consignor informs the buyer, consignee or
     * the distribution centre about the despatch of goods for cross docking.
     */
    V_398(DocumentCodeType.V_398),

    /**
     * Document by means of which the supplier or consignor informs the buyer, consignee or
     * the distribution centre about the despatch of goods for transshipment.
     */
    V_399(DocumentCodeType.V_399),

    /**
     * Document/message sent by an account servicing institution to one of its account owners,
     * to inform the account owner of an entry that has been or will be credited to its account
     * for a specified amount on the date indicated. It provides extended commercial information
     * concerning the relevant remittance advice.
     */
    V_455(DocumentCodeType.V_455),

    /**
     * Document/message advising of the remittance of payment.
     */
    V_481(DocumentCodeType.V_481),

    /**
     * To indicate that the document/message justifying an accounting entry is original.
     */
    V_533(DocumentCodeType.V_533),

    /**
     * To indicate that the document/message justifying an accounting entry is a copy.
     */
    V_534(DocumentCodeType.V_534),

    /**
     * Document/message issued by a party entitled to authorize the release of goods specified
     * therein to a named consignee, to be retained by the custodian of the goods.
     */
    V_640(DocumentCodeType.V_640),

    /**
     * Document/message providing agreed textual information.
     */
    V_719(DocumentCodeType.V_719),

    /**
     * A message enabling the transmission of commercial data concerning payments made and
     * outstanding items on an account over a period of time.
     */
    V_731(DocumentCodeType.V_731),

    /**
     * A message sent by a party (usually an employer or its representative) to a service
     * providing organisation, to detail payroll deductions paid on behalf of its employees
     * to the service providing organisation.
     */
    V_747(DocumentCodeType.V_747),
    ;

    private final String name;
    private final String code;

    AccountingDocumentCodeType(DocumentCodeType cl) {
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
