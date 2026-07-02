package io.github.up2jakarta.test.dto;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@SuppressWarnings("unused")
public class Address implements Segment {

    @Position(value = 0, defaultValue = "TN")
    @NotEmpty
    @Size(min = 2, max = 2)
    @BusinessType(P002)
    private final String country;

    @Position(1)
    @BusinessType(P003)
    private final String city;

    @Position(2)
    @NotEmpty
    @BusinessType(P004)
    private final String zipCode;

    @Position(3)
    @NotEmpty
    @BusinessType(P005)
    private final String addressLine1;

    @Position(4)
    @BusinessType(P006)
    private final String addressLine2;

    public Address(String country, String city, String zipCode, String addressLine1, String addressLine2) {
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }
}
