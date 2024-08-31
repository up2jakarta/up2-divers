package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.test.input.ParsedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_TESTS")
@SuppressWarnings("unused")
public class ValidEntity extends ParsedEntity<Integer> {

    @Column(name = "TU_ID")
    private Integer key;

    @Column(name = "TU_NAME", length = 8)
    private String name;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }
}
