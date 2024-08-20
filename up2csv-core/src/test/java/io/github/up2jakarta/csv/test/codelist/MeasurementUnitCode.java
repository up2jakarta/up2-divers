package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(MeasurementUnitConverter.class)
public enum MeasurementUnitCode implements CodeList<MeasurementUnitCode> {


    C62("C62", "One"),
    KGM("KGM", "Kilogram"),
    ;

    private final String name;
    private final String code;

    MeasurementUnitCode(String code, String name) {
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
