package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.data.FullRecord;

public class InputRecord extends FullRecord<SegmentType> {

    public InputRecord(String reference, SegmentType type, String invoiceNumber, String... data) {
        super(reference, type, invoiceNumber, data);
    }

}
