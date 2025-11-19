package io.github.up2jakarta.lov.cl;

import io.github.up2jakarta.lov.CodeList;

public enum AccessMode2 implements CodeList<AccessMode2> {

    RO("RO", "Read only"),
    WO("WO", "Write only");

    private final String name;
    private final String code;

    AccessMode2(String name, String code) {
        this.name = name;
        this.code = code;
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
