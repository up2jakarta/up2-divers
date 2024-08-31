package io.github.up2jakarta.csv.test.bean.jpa;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableJPA;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.TestCodeList;
import io.github.up2jakarta.csv.test.codelist.TestCodeListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Up2EnableJPA
@SuppressWarnings("unused")
public class Test1Bean implements Segment {

    public static final String JPA_XXX = "JPA-XXX";

    @Position(0)
    @Enumerated(EnumType.STRING)
    private XML1Enum enum1;

    @Position(1)
    @Enumerated(EnumType.STRING)
    private XML2Enum enum2;

    @Position(2)
    @Enumerated(EnumType.ORDINAL)
    private XML1Enum enum3;

    @Position(3)
    @Enumerated(EnumType.ORDINAL)
    private XML2Enum enum4;

    @Position(4)
    @Convert(converter = TestCodeListConverter.class)
    private TestCodeList adapter;

    public XML1Enum getEnum1() {
        return enum1;
    }

    public void setEnum1(XML1Enum enum1) {
        this.enum1 = enum1;
    }

    public XML2Enum getEnum2() {
        return enum2;
    }

    public void setEnum2(XML2Enum enum2) {
        this.enum2 = enum2;
    }

    public XML1Enum getEnum3() {
        return enum3;
    }

    public void setEnum3(XML1Enum enum3) {
        this.enum3 = enum3;
    }

    public XML2Enum getEnum4() {
        return enum4;
    }

    public void setEnum4(XML2Enum enum4) {
        this.enum4 = enum4;
    }

    public TestCodeList getAdapter() {
        return adapter;
    }

    public void setAdapter(TestCodeList adapter) {
        this.adapter = adapter;
    }
}
