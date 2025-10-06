package io.github.up2jakarta.csv.ops.impl;

import io.github.up2jakarta.csv.api.BeanLinker;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice1;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice2;
import io.github.up2jakarta.csv.ops.impl.dto.Invoice3;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.csv.ops.impl.GroupType.*;
import static io.github.up2jakarta.csv.ops.impl.SegmentLinker.*;
import static io.github.up2jakarta.xml.api.SeverityType.*;

@SuppressWarnings("unused")
public enum SegmentType implements IFullType<GroupType, SegmentType> {

    // DUMMY
    S00("00", none(), NONE, ERROR),
    // Test 1
    S01("01", none(Invoice1.class), D001, FATAL),
    S02("02", seller(Invoice1.class), D002, ERROR),
    S03("03", buyer(Invoice1.class), D003, ERROR),
    S04("04", items(Invoice1.class), D004, ERROR),
    S05("05", notes(Invoice1.class), D007, WARNING),
    S06("06", amounts(Invoice1.class), D006, ERROR),
    // Test 2
    S11("11", none(Invoice2.class), D001, FATAL),
    S12("12", seller(Invoice2.class), D002, ERROR),
    S13("13", buyer(Invoice2.class), D003, ERROR),
    S14("14", items(Invoice2.class), D004, ERROR),
    S15("15", notes(Invoice2.class), D007, WARNING),
    S16("16", amounts(Invoice2.class), D006, ERROR),
    // Test 3
    S21("21", none(Invoice3.class), D001, FATAL),
    S22("22", seller(Invoice3.class), D002, ERROR),
    S23("23", buyer(Invoice3.class), D003, ERROR),
    S24("24", items(Invoice3.class), D004, ERROR),
    S25("25", notes(Invoice3.class), D007, WARNING),
    S26("26", amounts(Invoice3.class), D006, ERROR),
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
