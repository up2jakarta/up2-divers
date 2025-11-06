package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.fmt.FullRecord;

public class InputRecord extends FullRecord<SegmentType> {

    public InputRecord(String reference, SegmentType type, String invoiceKey, String... data) {
        super(reference, type, invoiceKey, data);
    }

}
