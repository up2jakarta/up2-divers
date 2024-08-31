package io.github.up2jakarta.csv.test.input;

import io.github.up2jakarta.csv.input.InputType;

public enum SegmentType implements InputType<SegmentType> {

    S00("00", "Header"),
    S99("99", "Footer"),
    ;

    private final String name;
    private final String code;

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

}
