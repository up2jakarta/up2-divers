package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.CountryIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TradeAddressType", propOrder = {
        "postcodeCode",
        "lineOne",
        "lineTwo",
        "lineThree",
        "cityName",
        "countryId",
        "countrySubDivisionName"
})
public class TradeAddressType {

    // BT-38, BT-53, EXT-FR-FE-18, EXT-FR-FE-36, EXT-FR-FE-59, EXT-FR-FE-81, EXT-FR-FE-105 and (4) specifications too.
    private String postcodeCode;

    // BT-35, BT-50, EXT-FR-FE-15, EXT-FR-FE-32, EXT-FR-FE-55, EXT-FR-FE-78, EXT-FR-FE-101 and (4) specifications too.
    private String lineOne;

    // BT-36, BT-51, EXT-FR-FE-16, EXT-FR-FE-33, EXT-FR-FE-56, EXT-FR-FE-79, EXT-FR-FE-102 and (4) specifications too.
    private String lineTwo;

    // BT-162, BT-163, EXT-FR-FE-17, EXT-FR-FE-34, EXT-FR-FE-57, EXT-FR-FE-80, EXT-FR-FE-103 and (4) specifications too.
    private String lineThree;

    // BT-37, BT-52, EXT-FR-FE-19, EXT-FR-FE-35, EXT-FR-FE-58, EXT-FR-FE-82, EXT-FR-FE-104 and (4) specifications too.
    private String cityName;

    // BT-40, BT-55, EXT-FR-FE-21, EXT-FR-FE-38, EXT-FR-FE-61, EXT-FR-FE-84, EXT-FR-FE-107 and (4) specifications too.
    private CountryIDType countryId;

    // BT-39, BT-54, EXT-FR-FE-20, EXT-FR-FE-37, EXT-FR-FE-60, EXT-FR-FE-83, EXT-FR-FE-106 and (4) specifications too.
    private String countrySubDivisionName;

    @XmlElement(name = "PostcodeCode")
    public String getPostcodeCode() {
        return this.postcodeCode;
    }

    public void setPostcodeCode(String postcodeCode) {
        this.postcodeCode = postcodeCode;
    }

    @XmlElement(name = "LineOne")
    public String getLineOne() {
        return this.lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    @XmlElement(name = "LineTwo")
    public String getLineTwo() {
        return this.lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    @XmlElement(name = "LineThree")
    public String getLineThree() {
        return this.lineThree;
    }

    public void setLineThree(String lineThree) {
        this.lineThree = lineThree;
    }

    @XmlElement(name = "CityName")
    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @XmlElement(name = "CountryID")
    public CountryIDType getCountryId() {
        return this.countryId;
    }

    public void setCountryId(CountryIDType countryId) {
        this.countryId = countryId;
    }

    @XmlElement(name = "CountrySubDivisionName")
    public String getCountrySubDivisionName() {
        return this.countrySubDivisionName;
    }

    public void setCountrySubDivisionName(String countrySubDivisionName) {
        this.countrySubDivisionName = countrySubDivisionName;
    }

}
