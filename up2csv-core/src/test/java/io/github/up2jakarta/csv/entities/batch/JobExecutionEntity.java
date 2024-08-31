package io.github.up2jakarta.csv.entities.batch;

import io.github.up2jakarta.csv.entities.input.InputFileEntity;
import io.github.up2jakarta.csv.entities.input.InputLoadingEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Immutable
@Table(name = "TB_JOB_EXECUTION")
public class JobExecutionEntity implements Serializable {

    @OneToMany(mappedBy = "execution")
    private final List<InputFileEntity> inputFiles = new LinkedList<>();
    @OneToMany(mappedBy = "execution")
    private final List<InputLoadingEntity> loadings = new LinkedList<>();
    @OneToMany(mappedBy = "key.execution")
    private final List<JobErrorEntity> errors = new LinkedList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_JOB_EXECUTION_SEQ")
    @SequenceGenerator(name = "TB_JOB_EXECUTION_SEQ", sequenceName = "TB_JOB_EXECUTION_SEQ", allocationSize = 1)
    @Column(name = "JOB_EXECUTION_ID")
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "JOB_INSTANCE_ID", referencedColumnName = "JOB_INSTANCE_ID",
            foreignKey = @ForeignKey(name = "FK_JOB_EXECUTION_INSTANCE")
    )
    private JobInstanceEntity instance;
    @Column(name = "VERSION", nullable = false)
    private Long version;
    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;
    @Column(name = "START_TIME")
    private LocalDateTime startTime;
    @Column(name = "END_TIME")
    private LocalDateTime endTime;
    @Column(name = "STATUS", length = 15)
    private String status;
    @Column(name = "EXIT_CODE", length = 63)
    private String exitCode;
    @Column(name = "EXIT_MESSAGE", length = 2047)
    private String exitMessage;
    @Column(name = "LAST_UPDATED")
    private LocalDateTime lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobInstanceEntity getInstance() {
        return instance;
    }

    public void setInstance(JobInstanceEntity instance) {
        this.instance = instance;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public String getExitMessage() {
        return exitMessage;
    }

    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<InputFileEntity> getInputFiles() {
        return inputFiles;
    }

    public List<InputLoadingEntity> getLoadings() {
        return loadings;
    }

    public List<JobErrorEntity> getErrors() {
        return errors;
    }

}
