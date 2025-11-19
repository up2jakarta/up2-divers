package io.github.up2jakarta.csv.impl.dto;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.Parsable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Valid
@Error("CSV-C02")
@SuppressWarnings("unused")
public class Party extends Parsable {

    @Position(0)
    @NotEmpty
    private String name;

    @Fragment(1)
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
