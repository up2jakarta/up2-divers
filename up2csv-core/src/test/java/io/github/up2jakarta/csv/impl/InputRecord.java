package io.github.up2jakarta.csv.impl;

import java.nio.file.Path;

public class InputRecord extends io.github.up2jakarta.csv.fmt.hdl.InputRecord<SegmentType, Path> {

    public InputRecord(String reference, SegmentType type, String invoiceKey, String... data) {
        super(null, reference, type, invoiceKey, data);
    }

}
