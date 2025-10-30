package io.github.up2jakarta.job.csv.impl;

import io.github.up2jakarta.csv.api.BeanLinker;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.job.csv.impl.GroupType.*;
import static io.github.up2jakarta.job.csv.impl.SegmentLinker.*;
import static io.github.up2jakarta.xml.api.SeverityType.*;

@SuppressWarnings("unused")
public enum SegmentType implements IFullType<GroupType, SegmentType> {

    S01("01", invoice(), D01, FATAL),
    S02("02", seller(), D02, ERROR),
    S03("03", buyer(), D03, ERROR),
    S04("04", items(), D04, ERROR),
    S05("05", notes(), D05, WARNING),
    S06("06", amounts(), D06, ERROR),
    S07("07", payer(), D07, ERROR),
    S08("08", payee(), D08, ERROR),
    S09("09", attributes(), D09, WARNING),
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
        return "CSV-" + this.name();
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
        return "#S[" + code + ']';
    }

}
