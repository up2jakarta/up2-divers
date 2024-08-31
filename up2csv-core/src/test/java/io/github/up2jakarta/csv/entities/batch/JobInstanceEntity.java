package io.github.up2jakarta.csv.entities.batch;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Immutable
@Table(
        name = "TB_JOB_INSTANCE",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_JOB_INSTANCE",
                columnNames = {"JOB_NAME", "JOB_KEY"}
        )
)
public class JobInstanceEntity implements Serializable {

    @OneToMany(mappedBy = "instance")
    private final List<JobExecutionEntity> executions = new LinkedList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_JOB_SEQ")
    @SequenceGenerator(name = "TB_JOB_SEQ", sequenceName = "TB_JOB_SEQ", allocationSize = 1)
    @Column(name = "JOB_INSTANCE_ID")
    private Long id;
    @Column(name = "VERSION")
    private Long version;
    @Column(name = "JOB_NAME", length = 127)
    private String name;
    @Column(name = "JOB_KEY", length = 31)
    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<JobExecutionEntity> getExecutions() {
        return executions;
    }

}
