package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;

import java.util.Collection;

import static io.github.up2jakarta.lov.SeverityType.*;
import static io.github.up2jakarta.test.impl.GroupType.*;
import static io.github.up2jakarta.test.impl.SegmentLinker.*;

@SuppressWarnings("unused")
public enum SegmentType implements IType<GroupType, SegmentType> {

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

    private final SegmentLinker<Segment, Segment> linker;
    private final GroupType groupType;
    private final SeverityType level;
    private final String code;

    @SuppressWarnings("unchecked")
    SegmentType(String code, SegmentLinker<?, ?> linker, GroupType type, SeverityType level) {
        this.code = code;
        this.level = level;
        this.linker = (SegmentLinker<Segment, Segment>) linker;
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
    public GroupType getDataType() {
        return groupType;
    }

    @Override
    public String getEventCode() {
        return "CSV-" + groupType.name();
    }

    @Override
    public SeverityType getEventLevel() {
        return level;
    }

    @Override
    public Class<Segment> getClassType() {
        return linker.classType;
    }

    @Override
    public Class<Segment> getParentType() {
        return linker.parentType;
    }

    @Override
    public Collection<Segment> from(Segment parent) {
        return linker.from(parent);
    }

    @Override
    public void link(Segment parent, Segment child) {
        linker.link(parent, child);
    }

}
