package io.github.up2jakarta.test.lov;

import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Deprecated;

public enum AccessMode2 implements CodeList<AccessMode2> {

    @Deprecated
    @SuppressWarnings("unused")
    NA("NA", "No access"),
    @Deprecated(exclude = false)
    RW("RW", "Read & Write"),
    RO("RO", "Read only"),
    WO("WO", "Write only");

    private final String code;
    private final String name;

    AccessMode2(String code, String name) {
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
