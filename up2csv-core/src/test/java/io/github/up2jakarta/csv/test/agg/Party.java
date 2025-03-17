package io.github.up2jakarta.csv.test.agg;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.impl.Parsable;
import jakarta.validation.Valid;

@Valid
@SuppressWarnings("unused")
public class Party extends Parsable {

    @Position(0)
    private String name;

    @Fragment(1)
    @Valid
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
