package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.fmt.hdl.PathRecord;

public class InputRecord extends PathRecord<SegmentType> {

    public InputRecord(String reference, SegmentType type, String invoiceKey, String... data) {
        super(null, reference, type, invoiceKey, data);
    }

}
