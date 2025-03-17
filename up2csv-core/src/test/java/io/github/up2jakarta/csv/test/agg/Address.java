package io.github.up2jakarta.csv.test.agg;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Converter;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.CountryCodeType;
import io.github.up2jakarta.csv.test.codelist.CountryConverter;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class Address implements Segment {

    @Position(0)
    @NotNull
    @Up2Converter(CountryConverter.class)
    private CountryCodeType country;

    @Position(1)
    private String city;

    @Position(2)
    @NotNull
    private String zipCode;

    @Position(3)
    private String addressLine1;

    @Position(4)
    private String addressLine2;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryCodeType getCountry() {
        return country;
    }

    public void setCountry(CountryCodeType country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
}
