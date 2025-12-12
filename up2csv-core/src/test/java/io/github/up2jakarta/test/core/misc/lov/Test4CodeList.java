package io.github.up2jakarta.test.core.misc.lov;

import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Deprecated;

public enum Test4CodeList implements CodeList<Test4CodeList> {

    ANY("*", "Any"),

    @Deprecated(exclude = false)// should be excluded
    ALL("*", "Duplicated code");

    private final String name;
    private final String code;

    Test4CodeList(String code, String name) {
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
