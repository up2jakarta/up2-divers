package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanLinker;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.csv.io.impl.GroupType.*;
import static io.github.up2jakarta.csv.io.impl.SegmentLinker.*;
import static io.github.up2jakarta.xml.api.SeverityType.*;

@SuppressWarnings("unused")
public enum SegmentType implements IType<GroupType, SegmentType> {

    S01("01", invoice(), D001, FATAL),
    S02("02", seller(), D002, ERROR),
    S03("03", buyer(), D003, ERROR),
    S04("04", items(), D004, ERROR),
    S05("05", notes(), D007, WARNING),
    S06("06", amounts(), D006, ERROR),
    S07("07", payer(), D008, ERROR),
    S08("08", payee(), D009, ERROR),
    S09("09", attributes(), D005, WARNING),
    ;

    private final SegmentLinker<?, ?> linker;
    private final GroupType groupType;
    private final SeverityType level;
    private final String code;

    <P extends Segment, T extends Segment> SegmentType(String code, SegmentLinker<P, T> linker, GroupType type, SeverityType level) {
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
    public BeanLinker<?, ?> getJoinLinker() {
        return linker;
    }

    @Override
    public String toString() {
        return "Segment#[" + code + ']';
    }

}
