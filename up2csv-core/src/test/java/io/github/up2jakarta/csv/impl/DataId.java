package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.extension.DataType;

@SuppressWarnings("unused")
public enum DataId implements DataType<DataId> {

    NONE("?", "Unknown", 0, N),

    D001("0001", "Invoice", 1, 1),
    D002("0002", "Seller", 1, 1),
    D003("0003", "Buyer", 0, 1),
    D004("0004", "Items", 1, N),
    D005("0005", "Product attributes", 0, N),
    ;

    private final String name;
    private final String code;

    private final int min;
    private final int max;

    DataId(String code, String name, int min, int max) {
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
