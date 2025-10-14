package io.github.up2jakarta.csv.core.misc.map.oneshot;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public abstract class AddressSegment<K extends Comparable<K>> implements Segment {

    @Position(0)
    private String country;

    @Position(1)
    private K city;

    @Position(2)
    private String postCode;

    @Position(3)
    private String addressLine;

    public K getCity() {
        return city;
    }

    public void setCity(K city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
}
