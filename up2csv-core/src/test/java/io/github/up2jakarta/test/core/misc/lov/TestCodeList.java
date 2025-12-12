package io.github.up2jakarta.test.core.misc.lov;

import io.github.up2jakarta.lov.CodeList;

public enum TestCodeList implements CodeList<TestCodeList> {

    ANY("*", "Any");

    private final String name;
    private final String code;

    TestCodeList(String code, String name) {
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
