package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.BeanLinker;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.test.sample.Invoice;

import static io.github.up2jakarta.csv.impl.BusinessType.*;
import static io.github.up2jakarta.csv.impl.SegmentLinker.*;

@SuppressWarnings("unused")
public enum SegmentType implements IFullType<BusinessType, SegmentType> {

    S00("00", "Test", none(), NONE),

    S01("01", none(Invoice.class), D001),
    S02("02", seller(), D002),
    S03("03", buyer(), D003),
    S04("04", items(), D004),
    S05("05", attributes(), D005),
    S06("06", amounts(), D006),
    S07("07", notes(), D007);

    private final SegmentLinker<?, ?> linker;
    private final BusinessType groupType;
    private final String name;
    private final String code;

    <P extends Parsable, T extends Parsable> SegmentType(String code, SegmentLinker<P, T> linker, BusinessType type) {
        this(code, type.getName(), linker, type);
    }

    <P extends Parsable, T extends Parsable> SegmentType(String code, String name, SegmentLinker<P, T> linker, BusinessType type) {
        this.code = code;
        this.name = name;
        this.linker = linker;
        this.groupType = type;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BusinessType getBusinessType() {
        return groupType;
    }

    @Override
    public String getErrorCode() {
        return "CSV-" + name();
    }

    @Override
    @SuppressWarnings("unchecked")
    public BeanLinker<SegmentType, ?, ?> linker() {
        return linker;
    }

    @Override
    public String toString() {
        return "Segment#[" + code + ']';
    }

}
