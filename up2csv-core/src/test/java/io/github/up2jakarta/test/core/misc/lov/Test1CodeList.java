package io.github.up2jakarta.test.core.misc.lov;

import io.github.up2jakarta.lov.CodeList;

public enum Test1CodeList implements CodeList<CurrencyCodeType> {
    ;

    public String getCode() {
        return "TEST";
    }

    public String getName() {
        return "TEST";
    }
}
