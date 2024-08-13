package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.core.Segment;

@SuppressWarnings("unused")
public final class SimpleSegment implements Segment {

    @Position(0)
    private String code;
    @Position(1)
    private String name;
    @Position(2)
    private String role;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
