package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.input.InputLinker;
import io.github.up2jakarta.csv.input.Linkable;
import io.github.up2jakarta.csv.test.agg.Invoice;

import static io.github.up2jakarta.csv.impl.DataId.*;
import static io.github.up2jakarta.csv.impl.SegmentLinker.*;

@SuppressWarnings("unused")
public enum SegmentType implements Linkable<DataId, SegmentType> {

    S00("00", "Header", none(), NONE),
    S99("99", "Footer", none(), NONE),

    S01("01", "Invoice", none(Invoice.class), D001),
    S02("02", "Seller", seller(), D002),
    S03("03", "Buyer", buyer(), D003),
    S04("04", "Items", items(), D004),
    S05("05", "Product Attributes", attributes(), D005),
    ;

    private final SegmentLinker<?, ?> linker;
    private final DataId groupType;
    private final String name;
    private final String code;

    <P extends Parsable, T extends Parsable>
    SegmentType(String code, String name, SegmentLinker<P, T> linker, DataId groupType) {
        this.code = code;
        this.name = name;
        this.linker = linker;
        this.groupType = groupType;
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
    public DataId getBusinessType() {
        return groupType;
    }

    @Override
    public String getErrorCode() {
        return "CSV-" + name();
    }

    @Override
    @SuppressWarnings("unchecked")
    public InputLinker<SegmentType, ?, ?> linker() {
        return linker;
    }

    @Override
    public String toString() {
        return "Segment#[" + code + ']';
    }

}
