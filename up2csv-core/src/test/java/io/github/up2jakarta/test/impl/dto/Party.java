package io.github.up2jakarta.test.impl.dto;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import static io.github.up2jakarta.test.impl.TermType.P001;

@Valid
public class Party extends Parsable {

    @Position(0)
    @NotEmpty
    @BusinessType(P001)
    private final String name;

    @Fragment(1)
    @NotNull
    private final Address address;

    public Party(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

}
