package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(CountryConverter.class)
public enum CountryCodeType implements CodeList<CountryCodeType> {

    TN("TN", "Tunisia"),
    FR("FR", "France");

    private final String name;
    private final String code;

    CountryCodeType(String code, String name) {
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
