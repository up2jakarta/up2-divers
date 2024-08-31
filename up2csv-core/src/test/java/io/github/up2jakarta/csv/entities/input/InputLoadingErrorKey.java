package io.github.up2jakarta.csv.entities.input;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class InputLoadingErrorKey implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "ERR_LOAD_ID", referencedColumnName = "LOAD_ID",
            foreignKey = @ForeignKey(name = "FK_INPUT_ERROR_LOADING")
    )
    private InputLoadingEntity loading;

    @Column(name = "ERR_ORDER", nullable = false)
    private Integer order;

    public InputLoadingEntity getLoading() {
        return loading;
    }

    public void setLoading(InputLoadingEntity loading) {
        this.loading = loading;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
