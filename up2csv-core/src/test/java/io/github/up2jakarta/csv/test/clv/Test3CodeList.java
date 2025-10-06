package io.github.up2jakarta.csv.test.clv;

import io.github.up2jakarta.xml.clv.CodeList;

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
