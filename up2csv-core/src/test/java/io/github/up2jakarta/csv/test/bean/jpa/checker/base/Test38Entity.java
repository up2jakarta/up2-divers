package io.github.up2jakarta.csv.test.bean.jpa.checker.base;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.core.ext.Prefix;
import io.github.up2jakarta.csv.test.ParsedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Up2EnableJPA
@Prefix("TU_")
@Table(name = "TB_TESTS")
@SuppressWarnings("unused")
public class Test38Entity extends ParsedEntity<String> {

    @Position(0)
    @Column(name = "TU_KEY", length = -1)
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
