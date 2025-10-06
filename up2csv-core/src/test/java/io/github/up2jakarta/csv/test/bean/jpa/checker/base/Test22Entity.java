package io.github.up2jakarta.csv.test.bean.jpa.checker.base;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.core.ext.Prefix;
import io.github.up2jakarta.csv.test.ParsedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Up2EnableJPA
@Prefix("TU_")
@Table(name = "TB_TESTS")
@SuppressWarnings("unused")
public class Test22Entity extends ParsedEntity<Integer> {

    @Position(0)
    @Size(max = 10)
    @Column(name = "TU_KEY", precision = 10)
    private Integer key;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
