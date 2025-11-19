package io.github.up2jakarta.lov.cl;

import io.github.up2jakarta.lov.CodeList;

public sealed class AccessMode30 implements CodeList<AccessMode30> permits AccessMode31, AccessMode32 {

    private final String name;
    private final String code;

    AccessMode30(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public final String getCode() {
        return code;
    }

    @Override
    public final String getName() {
        return name;
    }
}
