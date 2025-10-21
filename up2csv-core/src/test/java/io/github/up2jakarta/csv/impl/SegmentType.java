package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.BeanLinker;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.core.misc.Parsable;
import io.github.up2jakarta.csv.fmt.misc.*;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.csv.impl.GroupType.*;
import static io.github.up2jakarta.csv.impl.SegmentLinker.*;
import static io.github.up2jakarta.xml.api.SeverityType.*;

@SuppressWarnings("unused")
public enum SegmentType implements IFullType<GroupType, SegmentType> {

    // DUMMY
    S00("00", none(), NONE, ERROR),
    // Base (Fine reference)
    S01("01", none(Invoice.class), D001, FATAL),
    S02("02", seller(Invoice.class), D002, ERROR),
    S03("03", buyer(Invoice.class), D003, ERROR),
    S04("04", items(Invoice.class), D004, ERROR),
    S05("05", notes(Invoice.class), D007, WARNING),
    S06("06", amounts(Invoice.class), D006, ERROR),
    S07("07", payer(Invoice.class), D008, ERROR),
    S08("08", payee(Invoice.class), D009, ERROR),
    // Test 2 (Unknown reference)
    S11("11", none(Dummy1Invoice.class), D001, FATAL),
    S12("12", seller(Dummy1Invoice.class), D002, ERROR),
    S13("13", buyer(Dummy1Invoice.class), D003, ERROR),
    S14("14", items(Dummy1Invoice.class), D004, ERROR),
    S15("15", notes(Dummy1Invoice.class), D007, WARNING),
    S16("16", amounts(Dummy1Invoice.class), D006, ERROR),
    S17("17", payer(Dummy1Invoice.class), D008, ERROR),
    S18("18", payee(Dummy1Invoice.class), D009, ERROR),
    // Test 2 (Full truncated)
    S21("21", none(Dummy2Invoice.class), D001, FATAL),
    S22("22", seller(Dummy2Invoice.class), D002, ERROR),
    S23("23", buyer(Dummy2Invoice.class), D003, ERROR),
    S24("24", items(Dummy2Invoice.class), D004, ERROR),
    S25("25", notes(Dummy2Invoice.class), D007, WARNING),
    S26("26", amounts(Dummy2Invoice.class), D006, ERROR),
    S27("27", payer(Dummy2Invoice.class), D008, ERROR),
    S28("28", payee(Dummy2Invoice.class), D009, ERROR),
    // Test 3 (Fast truncated)
    S31("31", none(Dummy3Invoice.class), D001, FATAL),
    S32("32", seller(Dummy3Invoice.class), D002, ERROR),
    S33("33", buyer(Dummy3Invoice.class), D003, ERROR),
    S34("34", items(Dummy3Invoice.class), D004, ERROR),
    S35("35", notes(Dummy3Invoice.class), D007, WARNING),
    S36("36", amounts(Dummy3Invoice.class), D006, ERROR),
    S37("37", payer(Dummy3Invoice.class), D008, ERROR),
    S38("38", payee(Dummy3Invoice.class), D009, ERROR),
    // Test 4 (Null reference)
    S41("41", none(Dummy4Invoice.class), D001, FATAL),
    S42("42", seller(Dummy4Invoice.class), D002, ERROR),
    S43("43", buyer(Dummy4Invoice.class), D003, ERROR),
    S44("44", items(Dummy4Invoice.class), D004, ERROR),
    S45("45", notes(Dummy4Invoice.class), D007, WARNING),
    S46("46", amounts(Dummy4Invoice.class), D006, ERROR),
    S47("47", payer(Dummy4Invoice.class), D008, ERROR),
    S48("48", payee(Dummy4Invoice.class), D009, ERROR),
    // Test 5 (Null reference without validation)
    S51("51", none(Dummy5Invoice.class), D001, FATAL),
    S52("52", seller(Dummy5Invoice.class), D002, ERROR),
    S53("53", buyer(Dummy5Invoice.class), D003, ERROR),
    S54("54", items(Dummy5Invoice.class), D004, ERROR),
    S55("55", notes(Dummy5Invoice.class), D007, WARNING),
    S56("56", amounts(Dummy5Invoice.class), D006, ERROR),
    S57("57", payer(Dummy5Invoice.class), D008, ERROR),
    S58("58", payee(Dummy5Invoice.class), D009, ERROR),
    // Test 6 (Cyclic segments)
    S61("61", none(CyclicInvoice.class), D001, FATAL),
    S62("62", cyclicItems(), D004, FATAL),
    S63("63", cyclicInvoice(), D001, FATAL),
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
