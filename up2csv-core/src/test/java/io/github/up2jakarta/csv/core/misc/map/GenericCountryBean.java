package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;

@SuppressWarnings("unused")
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
