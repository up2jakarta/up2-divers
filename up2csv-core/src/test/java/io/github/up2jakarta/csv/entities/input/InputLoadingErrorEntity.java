package io.github.up2jakarta.csv.entities.input;

import io.github.up2jakarta.csv.entities.ErrorEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_INPUT_LOADING_ERRORS")
public class InputLoadingErrorEntity extends ErrorEntity {

    @EmbeddedId
    private InputLoadingErrorKey key;

    public InputLoadingErrorKey getKey() {
        return key;
    }

    public void setKey(InputLoadingErrorKey key) {
        this.key = key;
    }

}
