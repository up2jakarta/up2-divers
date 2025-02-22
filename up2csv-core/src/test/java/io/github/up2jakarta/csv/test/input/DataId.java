package io.github.up2jakarta.csv.test.input;

import io.github.up2jakarta.csv.extension.DataType;

@SuppressWarnings("unused")
public enum DataId implements DataType<DataId> {

    NONE("?", "Unknown"),

    D001("0001", "Data N°1"),
    D002("0002", "Data N°2"),
    D003("0003", "Data N°3"),
    D004("0004", "Data N°4"),
    D005("0005", "Data N°5"),
    ;

    private final String name;
    private final String code;

    DataId(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }
}
