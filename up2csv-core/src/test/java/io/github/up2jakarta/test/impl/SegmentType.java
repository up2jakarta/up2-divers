package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.lov.CodeList;

/**
 * Base {@link IType} implementation.
 */
@SuppressWarnings("unused")
public enum SegmentType implements IType<SegmentType> {

    S01("01", "Invoice"),
    S02("02", "Seller"),
    S03("03", "Buyer"),
    S04("04", "Items"),
    S05("05", "Notes"),
    S06("06", "Amounts"),
    S07("07", "Payer"),
    S08("08", "Payee"),
    S09("09", "Attributes"),

    // DUMMY TYPES used for test coverage
    S00("00", "NaN"),
    // Test 2 (Unknown reference)
    S11("11", "Dummy1Invoice"),
    // Test 2 (Full truncated)
    S21("21", "Dummy2Invoice"),
    // Test 3 (Mess truncated)
    S31("31", "Dummy3Invoice"),
    // Test 4 (Null reference)
    S41("41", "Dummy4Invoice"),
    // Test 5 (Null reference without validation)
    S51("51", "Dummy5Invoice"),
    // Test 6 (Cyclic segments)
    S61("61", "CyclicInvoice"),
    S62("62", "CyclicInvoice[items]"),
    S63("63", "CyclicItem[invoice]"),
    // Test 7 (Dummy / Virtual reference)
    S71("71", "DV-Reference"),
    S72("72", "DV-Attribute"),
    S73("73", "DV-Item"),
    // Test 8 (Multiple @ReferenceId)
    S80("80", "Tree80"),
    S81("81", "Node81"),
    S82("82", "Node82"),
    S83("83", "Node83"),
    S84("84", "Node84"),
    // Test 9 (Virtual @ReferenceId)
    S90("90", "Tree90"),
    S91("91", "Node91"),
    S92("92", "Node92"),
    S93("93", "Node93"),
    S94("94", "Node94"),
    ;

    private final String code;
    private final String name;

    SegmentType(String code, String name) {
        this.code = code;
        this.name = name;
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
    public String toString() {
        return CodeList.toString(code, name);
    }
}
