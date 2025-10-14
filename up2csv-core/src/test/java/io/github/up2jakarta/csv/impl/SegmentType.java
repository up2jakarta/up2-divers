package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.BeanLinker;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.ops.misc.Dummy1;
import io.github.up2jakarta.csv.ops.misc.Dummy2;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.csv.impl.GroupType.*;
import static io.github.up2jakarta.csv.impl.SegmentLinker.*;
import static io.github.up2jakarta.xml.api.SeverityType.*;

@SuppressWarnings("unused")
public enum SegmentType implements IFullType<GroupType, SegmentType> {

    // DUMMY
    S00("00", none(), NONE, ERROR),
    // Test 1
    S01("01", none(Invoice.class), D001, FATAL),
    S02("02", seller(Invoice.class), D002, ERROR),
    S03("03", buyer(Invoice.class), D003, ERROR),
    S04("04", items(Invoice.class), D004, ERROR),
    S05("05", notes(Invoice.class), D007, WARNING),
    S06("06", amounts(Invoice.class), D006, ERROR),
    S07("07", payer(Invoice.class), D008, ERROR),
    S08("08", payee(Invoice.class), D009, ERROR),
    // Test 2
    S11("11", none(Dummy1.class), D001, FATAL),
    S12("12", seller(Dummy1.class), D002, ERROR),
    S13("13", buyer(Dummy1.class), D003, ERROR),
    S14("14", items(Dummy1.class), D004, ERROR),
    S15("15", notes(Dummy1.class), D007, WARNING),
    S16("16", amounts(Dummy1.class), D006, ERROR),
    S17("17", payer(Dummy1.class), D008, ERROR),
    S18("18", payee(Dummy1.class), D009, ERROR),
    // Test 3
    S31("31", none(Dummy2.class), D001, FATAL),
    S32("32", cyclicItems(), D004, FATAL),
    S33("33", cyclicInvoice(), D001, FATAL),
    // COMMON
    S90("90", attributes(), D005, WARNING),
    ;

    private final SegmentLinker<?, ?> linker;
    private final GroupType groupType;
    private final SeverityType level;
    private final String code;

    <P extends Parsable, T extends Parsable> SegmentType(String code, SegmentLinker<P, T> linker, GroupType type, SeverityType level) {
        this.code = code;
        this.level = level;
        this.linker = linker;
        this.groupType = type;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return groupType.getName();
    }

    @Override
    public GroupType getBusinessType() {
        return groupType;
    }

    @Override
    public String getErrorCode() {
        return "CSV-" + groupType.name();
    }

    @Override
    public SeverityType getErrorLevel() {
        return level;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BeanLinker<?, ?> linker() {
        return linker;
    }

    @Override
    public String toString() {
        return "Segment#[" + code + ']';
    }

}
