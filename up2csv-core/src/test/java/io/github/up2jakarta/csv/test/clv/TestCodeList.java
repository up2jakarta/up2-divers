package io.github.up2jakarta.csv.test.clv;

import io.github.up2jakarta.xml.clv.CodeList;

import java.io.Serializable;

@SuppressWarnings("ALL")
public enum TestCodeList implements CodeList<TestCodeList>, Serializable {

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
