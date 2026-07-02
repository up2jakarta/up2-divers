package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.PaymentMeansCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeSettlementPaymentMeansType", propOrder = {
        "typeCode",
        "information",
        "applicableTradeSettlementFinancialCard",
        "payerPartyDebtorFinancialAccount",
        "payeePartyCreditorFinancialAccount",
        "payeeSpecifiedCreditorFinancialInstitution"
})
public class TradeSettlementPaymentMeansType {

    // BT-81
    @XmlElement(name = "TypeCode")
    private PaymentMeansCodeType typeCode;

    // BT-82
    @XmlElement(name = "Information")
    private String information;

    // BG-18
    @XmlElement(name = "ApplicableTradeSettlementFinancialCard")
    private TradeSettlementFinancialCardType applicableTradeSettlementFinancialCard;

    @XmlElement(name = "PayerPartyDebtorFinancialAccount")
    private DebtorFinancialAccountType payerPartyDebtorFinancialAccount;

    // BG-17
    @XmlElement(name = "PayeePartyCreditorFinancialAccount")
    private CreditorFinancialAccountType payeePartyCreditorFinancialAccount;

    @XmlElement(name = "PayeeSpecifiedCreditorFinancialInstitution")
    private CreditorFinancialInstitutionType payeeSpecifiedCreditorFinancialInstitution;

    public PaymentMeansCodeType getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(PaymentMeansCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public String getInformation() {
        return this.information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public TradeSettlementFinancialCardType getApplicableTradeSettlementFinancialCard() {
        return this.applicableTradeSettlementFinancialCard;
    }

    public void setApplicableTradeSettlementFinancialCard(TradeSettlementFinancialCardType applicableTradeSettlementFinancialCard) {
        this.applicableTradeSettlementFinancialCard = applicableTradeSettlementFinancialCard;
    }

    public DebtorFinancialAccountType getPayerPartyDebtorFinancialAccount() {
        return this.payerPartyDebtorFinancialAccount;
    }

    public void setPayerPartyDebtorFinancialAccount(DebtorFinancialAccountType payerPartyDebtorFinancialAccount) {
        this.payerPartyDebtorFinancialAccount = payerPartyDebtorFinancialAccount;
    }

    public CreditorFinancialAccountType getPayeePartyCreditorFinancialAccount() {
        return this.payeePartyCreditorFinancialAccount;
    }

    public void setPayeePartyCreditorFinancialAccount(CreditorFinancialAccountType payeePartyCreditorFinancialAccount) {
        this.payeePartyCreditorFinancialAccount = payeePartyCreditorFinancialAccount;
    }

    public CreditorFinancialInstitutionType getPayeeSpecifiedCreditorFinancialInstitution() {
        return this.payeeSpecifiedCreditorFinancialInstitution;
    }

    public void setPayeeSpecifiedCreditorFinancialInstitution(CreditorFinancialInstitutionType payeeSpecifiedCreditorFinancialInstitution) {
        this.payeeSpecifiedCreditorFinancialInstitution = payeeSpecifiedCreditorFinancialInstitution;
    }

}
