package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;

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
