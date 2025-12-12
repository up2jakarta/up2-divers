package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.test.core.misc.ParsedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_TESTS")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
