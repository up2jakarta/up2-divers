package io.github.up2jakarta.job.ctx;

import io.github.up2jakarta.xml.codelist.CodeList;

public enum FlowSource implements CodeList<FlowSource> {

    JOB("Job"),
    FLOW("Flow"),
    STEP("Step");

    private final String code;
    private final String name;

    FlowSource(final String name) {
        this.code = name.substring(0, 1);
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
