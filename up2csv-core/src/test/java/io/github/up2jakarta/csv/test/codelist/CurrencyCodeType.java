package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(CurrencyConverter.class)
public enum CurrencyCodeType implements CodeList<CurrencyCodeType> {

    EUR("EUR", "Euro"),
    TND("TND", "Tunisian Dinar"),
    ;

    private final String name;
    private final String code;

    CurrencyCodeType(String code, String name) {
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
