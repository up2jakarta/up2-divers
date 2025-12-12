package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.data.DataType;

@SuppressWarnings("unused")
public enum GroupType implements DataType<GroupType> {

    NONE("0000", "Dummy", 0, N),

    D001("0001", "Invoice", 1, 1),
    D002("0002", "Seller", 1, 1),
    D003("0003", "Buyer", 1, 1),
    D004("0004", "Items", 1, N),
    D005("0005", "Item attributes", 0, N),
    D006("0006", "Charges or Allowance amounts", 0, N),
    D007("0007", "Notes", 0, N),
    D008("0008", "Payer", 0, 1),
    D009("0009", "Payee", 0, 1),
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
