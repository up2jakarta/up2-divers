package io.github.up2jakarta.csv.entities.batch;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class JobErrorKey implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "ERR_EXEC_ID", referencedColumnName = "JOB_EXECUTION_ID",
            foreignKey = @ForeignKey(name = "FK_JOB_ERROR_EXECUTION")
    )
    private JobExecutionEntity execution;

    @Column(name = "ERR_ORDER", nullable = false)
    private Integer order;

    public JobExecutionEntity getExecution() {
        return execution;
    }

    public void setExecution(JobExecutionEntity execution) {
        this.execution = execution;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
