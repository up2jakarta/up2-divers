package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.csv.extension.CodeList;

import java.io.Serializable;

@SuppressWarnings("ALL")
public class Test3CodeList implements CodeList, Serializable {
    ;

    public String getCode() {
        return "TEST";
    }

    public String getName() {
        return "TEST";
    }
}
