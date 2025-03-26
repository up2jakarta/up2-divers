package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.xml.codelist.CodeList;

public enum Test1CodeList implements CodeList<CurrencyCodeType> {
    ;

    public String getCode() {
        return "TEST";
    }

    public String getName() {
        return "TEST";
    }
}
