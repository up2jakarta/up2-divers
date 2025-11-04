package io.github.up2jakarta.csv.impl;

public class InputRecord extends io.github.up2jakarta.csv.fmt.InputRecord<SegmentType> {

    public InputRecord(String reference, SegmentType type, String invoiceKey, String... data) {
        super(reference, type, invoiceKey, data);
    }

}
