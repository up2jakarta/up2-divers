package io.github.up2jakarta.test.core.misc.map.oneshot;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

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

    public String getCountry() {
        return country;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

}
