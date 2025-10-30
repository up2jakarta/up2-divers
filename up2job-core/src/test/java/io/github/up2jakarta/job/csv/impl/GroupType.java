package io.github.up2jakarta.job.csv.impl;

import io.github.up2jakarta.csv.data.DataType;

@SuppressWarnings("unused")
public enum GroupType implements DataType<GroupType> {

    D01("D01", "Invoice", 1, 1),
    D02("D02", "Seller", 1, 1),
    D03("D03", "Buyer", 1, 1),
    D04("D04", "Items", 1, N),
    D05("D05", "Notes", 0, N),
    D06("D06", "Charges or Allowance amounts", 0, N),
    D07("D07", "Payer", 0, 1),
    D08("D08", "Payee", 0, 1),
    D09("D09", "Item attributes", 0, N),
    ;

    private final String name;
    private final String code;

    private final int min;
    private final int max;

    GroupType(String code, String name, int min, int max) {
        this.code = code;
        this.name = name;
        this.min = min;
        this.max = max;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

}
