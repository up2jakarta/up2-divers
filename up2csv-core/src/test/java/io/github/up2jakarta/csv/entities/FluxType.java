package io.github.up2jakarta.csv.entities;

import io.github.up2jakarta.csv.extension.CodeList;

public enum FluxType implements CodeList<FluxType> {

    E_INVOICING("e-invoicing", "Flux e-invoicing"),
    E_REPORTING("e-reporting", "Flux e-reporting");

    private final String code;
    private final String name;

    FluxType(final String code, final String name) {
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
