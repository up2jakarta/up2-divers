package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.PartyIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TradeProductType", propOrder = {
        "globalId",
        "sellerAssignedId",
        "buyerAssignedId",
        "name",
        "description",
        "applicableProductCharacteristic",
        "designatedProductClassification",
        "originTradeCountry"
})
public class TradeProductType {

    // BT-157
    private PartyIDType globalId;

    // BT-155
    private String sellerAssignedId;

    // BT-156
    private String buyerAssignedId;

    // BT-153
    private String name;

    // BT-154
    private String description;

    // BG-32
    private List<ProductCharacteristicType> applicableProductCharacteristic;

    private ProductClassificationType designatedProductClassification;

    private TradeCountryType originTradeCountry;

    @XmlElement(name = "GlobalID")
    public PartyIDType getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(PartyIDType globalId) {
        this.globalId = globalId;
    }

    @XmlElement(name = "SellerAssignedID")
    public String getSellerAssignedId() {
        return this.sellerAssignedId;
    }

    public void setSellerAssignedId(String sellerAssignedId) {
        this.sellerAssignedId = sellerAssignedId;
    }

    @XmlElement(name = "BuyerAssignedID")
    public String getBuyerAssignedId() {
        return this.buyerAssignedId;
    }

    public void setBuyerAssignedId(String buyerAssignedId) {
        this.buyerAssignedId = buyerAssignedId;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "ApplicableProductCharacteristic")
    public List<ProductCharacteristicType> getApplicableProductCharacteristic() {
        return this.applicableProductCharacteristic;
    }

    public void setApplicableProductCharacteristic(List<ProductCharacteristicType> applicableProductCharacteristic) {
        this.applicableProductCharacteristic = applicableProductCharacteristic;
    }

    @XmlElement(name = "DesignatedProductClassification")
    public ProductClassificationType getDesignatedProductClassification() {
        return this.designatedProductClassification;
    }

    public void setDesignatedProductClassification(ProductClassificationType designatedProductClassification) {
        this.designatedProductClassification = designatedProductClassification;
    }

    @XmlElement(name = "OriginTradeCountry")
    public TradeCountryType getOriginTradeCountry() {
        return this.originTradeCountry;
    }

    public void setOriginTradeCountry(TradeCountryType originTradeCountry) {
        this.originTradeCountry = originTradeCountry;
    }

}
