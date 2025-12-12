package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;

public class GenericCountryBean extends GenericBean<String> {

    @Position(2)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
