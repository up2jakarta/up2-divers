package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.api.IType;

@SuppressWarnings("unused")
public enum SegmentType implements IType<SegmentType> {

    S01("01", "Invoice"),
    S02("02", "Seller"),
    S03("03", "Buyer"),
    S04("04", "Items"),
    S05("05", "Notes"),
    S06("06", "Amounts"),
    S07("07", "Payer"),
    S08("08", "Payee"),
    S09("09", "Attributes"),
    ;

    private final String code;
    private final String name;

    SegmentType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

}
