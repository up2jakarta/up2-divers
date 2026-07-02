package io.github.up2jakarta.test.core.misc.jpa.checker.base;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.core.ext.Up2Prefix;
import io.github.up2jakarta.test.core.misc.ParsedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Up2EnableJPA
@Up2Prefix("TU_")
@Table(name = "TB_TESTS")
public class Test18Entity extends ParsedEntity<String> {

    @Position(value = 0, defaultValue = "ZZZ")
    @Size(max = 8)
    @Column(name = "TU_KEY", length = 8, nullable = false)
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
