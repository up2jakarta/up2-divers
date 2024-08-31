package io.github.up2jakarta.csv.entities.batch;

import io.github.up2jakarta.csv.entities.ErrorEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_JOB_ERRORS")
public class JobErrorEntity extends ErrorEntity {

    @EmbeddedId
    private JobErrorKey key;

    public JobErrorKey getKey() {
        return key;
    }

    public void setKey(JobErrorKey key) {
        this.key = key;
    }

}
