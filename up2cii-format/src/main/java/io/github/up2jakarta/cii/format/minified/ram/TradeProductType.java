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
@XmlAccessorType(XmlAccessType.FIELD)
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
    @XmlElement(name = "GlobalID")
    private PartyIDType globalId;

    // BT-155
    @XmlElement(name = "SellerAssignedID")
    private String sellerAssignedId;

    // BT-156
    @XmlElement(name = "BuyerAssignedID")
    private String buyerAssignedId;

    // BT-153
    @XmlElement(name = "Name")
    private String name;

    // BT-154
    @XmlElement(name = "Description")
    private String description;

    // BG-32
    @XmlElement(name = "ApplicableProductCharacteristic")
    private List<ProductCharacteristicType> applicableProductCharacteristic;

    @XmlElement(name = "DesignatedProductClassification")
    private ProductClassificationType designatedProductClassification;

    @XmlElement(name = "OriginTradeCountry")
    private TradeCountryType originTradeCountry;

    public PartyIDType getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(PartyIDType globalId) {
        this.globalId = globalId;
    }

    public String getSellerAssignedId() {
        return this.sellerAssignedId;
    }

    public void setSellerAssignedId(String sellerAssignedId) {
        this.sellerAssignedId = sellerAssignedId;
    }

    public String getBuyerAssignedId() {
        return this.buyerAssignedId;
    }

    public void setBuyerAssignedId(String buyerAssignedId) {
        this.buyerAssignedId = buyerAssignedId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductCharacteristicType> getApplicableProductCharacteristic() {
        return this.applicableProductCharacteristic;
    }

    public void setApplicableProductCharacteristic(List<ProductCharacteristicType> applicableProductCharacteristic) {
        this.applicableProductCharacteristic = applicableProductCharacteristic;
    }

    public ProductClassificationType getDesignatedProductClassification() {
        return this.designatedProductClassification;
    }

    public void setDesignatedProductClassification(ProductClassificationType designatedProductClassification) {
        this.designatedProductClassification = designatedProductClassification;
    }

    public TradeCountryType getOriginTradeCountry() {
        return this.originTradeCountry;
    }

    public void setOriginTradeCountry(TradeCountryType originTradeCountry) {
        this.originTradeCountry = originTradeCountry;
    }

}
