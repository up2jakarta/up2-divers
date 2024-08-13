package io.github.up2jakarta.csv.test.bean.mapper.oneshot;

import io.github.up2jakarta.csv.annotation.Position;

@SuppressWarnings("unused")
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

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }
}
