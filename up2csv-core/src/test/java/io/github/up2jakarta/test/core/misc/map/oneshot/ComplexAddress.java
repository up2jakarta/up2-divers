package io.github.up2jakarta.test.core.misc.map.oneshot;

import io.github.up2jakarta.csv.cfg.Position;

public final class ComplexAddress extends AddressSegment<String> {

    @Position(3)
    private String addressLine1;

    @Position(4)
    private String addressLine2;

    @Position(5)
    private String addressLine3;

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

}
